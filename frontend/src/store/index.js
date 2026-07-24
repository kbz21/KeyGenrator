import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
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

  // Getters
  const keyCount = computed(() => keys.value.length)
  const aesKeys = computed(() => keys.value.filter(k => k.algorithm === 'AES'))
  const rsaKeys = computed(() => keys.value.filter(k => k.algorithm === 'RSA'))
  const recentKeys = computed(() => keys.value.slice(0, 5))

  // Actions
  async function generateKey(algorithm, keySize) {
    loading.value = true
    error.value = null
    
    try {
      const response = await keyApi.generateKey(algorithm, keySize)
      currentKey.value = response.data
      keys.value.unshift(response.data)
      toast.success('Key generated successfully! 🎉')
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

  async function fetchKeysByAlgorithm(algorithm) {
    loading.value = true
    error.value = null
    
    try {
      const response = await keyApi.getKeysByAlgorithm(algorithm)
      keys.value = response.data
      return response.data
    } catch (err) {
      error.value = err.message
      toast.error('Failed to fetch keys by algorithm')
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
      toast.success('Key deleted successfully')
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

  function setSelectedAlgorithm(algorithm) {
    selectedAlgorithm.value = algorithm
  }

  function setSelectedKeySize(size) {
    selectedKeySize.value = size
  }

  return {
    // State
    keys,
    currentKey,
    loading,
    error,
    selectedAlgorithm,
    selectedKeySize,
    
    // Getters
    keyCount,
    aesKeys,
    rsaKeys,
    recentKeys,
    
    // Actions
    generateKey,
    fetchAllKeys,
    fetchKeysByAlgorithm,
    deleteKey,
    clearError,
    setSelectedAlgorithm,
    setSelectedKeySize
  }
})
