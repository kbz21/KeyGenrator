import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useKeyStore } from '@/store'

export function useKeys() {
  const store = useKeyStore()
  const isInitialized = ref(false)

  onMounted(() => {
    console.log('🔄 useKeys composable mounted')
    fetchKeys()
    isInitialized.value = true
  })

  onUnmounted(() => {
    console.log('🔄 useKeys composable unmounted')
    isInitialized.value = false
  })

  async function fetchKeys() {
    await store.fetchAllKeys()
  }

  async function generateKey(algorithm, keySize, alias = null, purpose = null) {
    return await store.generateKey(algorithm, keySize, alias, purpose)
  }

  async function deleteKey(id) {
    return await store.deleteKey(id)
  }

  function resetFilters() {
    store.resetFilters()
  }

  return {
    keys: computed(() => store.keys),
    loading: computed(() => store.loading),
    error: computed(() => store.error),
    currentKey: computed(() => store.currentKey),
    keyCount: computed(() => store.keyCount),
    stats: computed(() => store.stats),
    searchQuery: computed({
      get: () => store.searchQuery,
      set: (value) => store.searchQuery = value
    }),
    filterAlgorithm: computed({
      get: () => store.filterAlgorithm,
      set: (value) => store.filterAlgorithm = value
    }),
    sortBy: computed({
      get: () => store.sortBy,
      set: (value) => store.sortBy = value
    }),
    filteredKeys: computed(() => store.filteredKeys),
    isInitialized,
    fetchKeys,
    generateKey,
    deleteKey,
    resetFilters,
    clearError: store.clearError
  }
}
