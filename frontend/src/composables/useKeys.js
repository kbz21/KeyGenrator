import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useKeyStore } from '@/store'

export function useKeys() {
  const store = useKeyStore()
  const searchQuery = ref('')
  const filterAlgorithm = ref('ALL')
  const sortBy = ref('newest')

  onMounted(() => {
    console.log('🔄 useKeys composable mounted')
    fetchKeys()
  })

  onUnmounted(() => {
    console.log('🔄 useKeys composable unmounted')
  })

  async function fetchKeys() {
    await store.fetchAllKeys()
  }

  const filteredKeys = computed(() => {
    let result = store.keys

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

  return {
    keys: computed(() => store.keys),
    loading: computed(() => store.loading),
    error: computed(() => store.error),
    currentKey: computed(() => store.currentKey),
    keyCount: computed(() => store.keyCount),
    searchQuery,
    filterAlgorithm,
    sortBy,
    filteredKeys,
    generateKey: store.generateKey,
    fetchKeys,
    deleteKey: store.deleteKey,
    clearError: store.clearError
  }
}
