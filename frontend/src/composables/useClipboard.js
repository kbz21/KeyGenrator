import { ref } from 'vue'
import { toast } from 'vue3-toastify'

export function useClipboard(resetDelay = 2000) {
  const copied = ref(false)
  const error = ref(null)

  async function copyToClipboard(text) {
    if (!text) {
      error.value = 'No text to copy'
      toast.error('No text to copy')
      return false
    }

    try {
      await navigator.clipboard.writeText(text)
      copied.value = true
      error.value = null
      toast.success('📋 Copied to clipboard!')
      
      setTimeout(() => {
        copied.value = false
      }, resetDelay)
      
      return true
    } catch (err) {
      error.value = err.message
      toast.error('Failed to copy to clipboard')
      return false
    }
  }

  return {
    copied,
    error,
    copyToClipboard
  }
}
