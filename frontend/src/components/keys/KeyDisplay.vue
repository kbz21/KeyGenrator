<template>
  <div v-if="key" class="card card-hover shadow">
    <div class="card-body">
      <div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3">
        <h3 class="card-title mb-0">
          <i class="bi bi-clipboard-check text-success me-2"></i>
          Generated Key
        </h3>
        <span class="badge bg-success">✅ Active</span>
      </div>

      <div class="bg-light p-3 rounded border">
        <div class="d-flex justify-content-between align-items-start">
          <pre class="mb-0 text-break font-monospace small">{{ key.generatedKey }}</pre>
          <button
            @click="copyToClipboard"
            class="btn btn-sm btn-outline-secondary ms-2 flex-shrink-0"
            :class="{ 'btn-success': copied }"
          >
            <i :class="copied ? 'bi bi-check-lg' : 'bi bi-clipboard'"></i>
            {{ copied ? 'Copied' : 'Copy' }}
          </button>
        </div>
      </div>

      <div class="row g-3 mt-3">
        <div class="col-6 col-md-3">
          <div class="bg-primary bg-opacity-10 p-3 rounded text-center">
            <small class="text-muted d-block">ID</small>
            <strong class="fs-5">#{{ key.id }}</strong>
          </div>
        </div>
        <div class="col-6 col-md-3">
          <div class="bg-info bg-opacity-10 p-3 rounded text-center">
            <small class="text-muted d-block">Algorithm</small>
            <strong class="fs-5">{{ key.algorithm }}</strong>
          </div>
        </div>
        <div class="col-6 col-md-3">
          <div class="bg-success bg-opacity-10 p-3 rounded text-center">
            <small class="text-muted d-block">Key Size</small>
            <strong class="fs-5">{{ key.keySize }} bits</strong>
          </div>
        </div>
        <div class="col-6 col-md-3">
          <div class="bg-warning bg-opacity-10 p-3 rounded text-center">
            <small class="text-muted d-block">Created</small>
            <strong class="fs-6">{{ formatDate(key.createdAt) }}</strong>
          </div>
        </div>
      </div>

      <div class="alert alert-warning mt-3 mb-0">
        <i class="bi bi-shield-exclamation me-2"></i>
        <strong>Security Note:</strong> Never share this key publicly. Store it securely.
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { toast } from 'vue3-toastify'

const props = defineProps({
  key: {
    type: Object,
    required: true
  }
})

const copied = ref(false)

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const copyToClipboard = async () => {
  try {
    await navigator.clipboard.writeText(props.key.generatedKey)
    copied.value = true
    toast.success('Key copied to clipboard! 📋')
    setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch (err) {
    toast.error('Failed to copy key')
  }
}
</script>
