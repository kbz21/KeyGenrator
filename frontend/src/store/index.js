import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'
import { keyApi } from '@/api/keyApi'
import { toast } from 'vue3-toastify'

export const useKeyStore = defineStore('keyStore', () => {
  // State
  const keys = ref([])
  const currentKey = ref(null)
  const loading = ref(false)
  const error = ref(null)
  const selectedAlgorithm = ref('AES')
  const selectedKeySize = ref(256)
  const searchQuery = ref('')
  const filterAlgorithm = ref('ALL')
  const sortBy = ref('newest')

  // Getters
  const keyCount = computed(() => keys.value.length)
  
  const aesKeys = computed(() => 
    keys.value.filter(k => k.algorithm === 'AES')
  )
  
  const rsaKeys = computed(() => 
    keys.value.filter(k => k.algorithm === 'RSA')
  )
  
  const recentKeys = computed(() => 
    keys.value.slice(0, 5)
  )
  
  const filteredKeys = computed(() => {
    let result = keys.value

    if (filterAlgorithm.value !== 'ALL') {
      result = result.filter(k => k.algorithm === filterAlgorithm.value)
    }

    if (searchQuery.value) {
      const query = searchQuery.value.toLowerCase()
      result = result.filter(k => 
        k.generatedKey.toLowerCase().includes(query) ||
        k.algorithm.toLowerCase().includes(query)
      )
    }

    if (sortBy.value === 'newest') {
      result = [...result].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    } else if (sortBy.value === 'oldest') {
      result = [...result].sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt))
    }

    return result
  })

  const stats = computed(() => ({
    total: keyCount.value,
    aes: aesKeys.value.length,
    rsa: rsaKeys.value.length,
    recent: recentKeys.value.length
  }))

  // Watchers
  watch(selectedAlgorithm, (newVal, oldVal) => {
    console.log(`🔄 Algorithm changed: ${oldVal} → ${newVal}`)
    const sizes = getAvailableSizes(newVal)
    if (!sizes.includes(selectedKeySize.value)) {
      selectedKeySize.value = sizes[0]
    }
  })

  // Actions
  async function generateKey(algorithm, keySize, alias = null, purpose = null) {
    loading.value = true
    error.value = null
    
    try {
      const response = await keyApi.generateKey(algorithm, keySize, alias, purpose)
      currentKey.value = response.data
      keys.value.unshift(response.data)
      toast.success('🔑 Key generated successfully!')
      return response.data
    } catch (err) {
      error.value = err.message
      toast.error(err.message || 'Failed to generate key')
      throw err
    } finally {
      loading.value = false
    }
  }

  async function fetchAllKeys() {
    loading.value = true
    error.value = null
    
    try {
      const response = await keyApi.getAllKeys()
      keys.value = response.data
      return response.data
    } catch (err) {
      error.value = err.message
      toast.error('Failed to fetch keys')
      throw err
    } finally {
      loading.value = false
    }
  }

  async function deleteKey(id) {
    loading.value = true
    error.value = null
    
    try {
      await keyApi.deleteKey(id)
      keys.value = keys.value.filter(k => k.id !== id)
      if (currentKey.value?.id === id) {
        currentKey.value = null
      }
      toast.success('🗑️ Key deleted successfully')
      return true
    } catch (err) {
      error.value = err.message
      toast.error('Failed to delete key')
      throw err
    } finally {
      loading.value = false
    }
  }

  function clearError() {
    error.value = null
  }

  function resetFilters() {
    searchQuery.value = ''
    filterAlgorithm.value = 'ALL'
    sortBy.value = 'newest'
  }

  function getAvailableSizes(algorithm) {
    const sizes = {
      AES: [128, 192, 256],
      RSA: [1024, 2048, 4096]
    }
    return sizes[algorithm] || sizes.AES
  }

  return {
    keys,
    currentKey,
    loading,
    error,
    selectedAlgorithm,
    selectedKeySize,
    searchQuery,
    filterAlgorithm,
    sortBy,
    keyCount,
    aesKeys,
    rsaKeys,
    recentKeys,
    filteredKeys,
    stats,
    generateKey,
    fetchAllKeys,
    deleteKey,
    clearError,
    resetFilters,
    getAvailableSizes
  }
})
