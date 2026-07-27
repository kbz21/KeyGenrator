<template>
  <form @submit.prevent="handleSubmit">
    <AlgorithmSelector
      v-model="formData.algorithm"
      :error="errors.algorithm"
    />
    
    <KeySizeSelector
      v-model="formData.keySize"
      :algorithm="formData.algorithm"
      :error="errors.keySize"
    />
    
    <div class="d-grid">
      <button
        type="submit"
        class="btn btn-primary btn-lg"
        :disabled="loading"
      >
        <span v-if="!loading">
          <i class="bi bi-key me-2"></i>
          Generate Key
        </span>
        <span v-else>
          <span class="spinner-border spinner-border-sm me-2"></span>
          Generating...
        </span>
      </button>
    </div>
    
    <div v-if="error" class="alert alert-danger mt-3">
      <i class="bi bi-exclamation-triangle-fill me-2"></i>
      {{ error }}
    </div>
  </form>
</template>

<script setup>
import { reactive, watch } from 'vue'
import AlgorithmSelector from './AlgorithmSelector.vue'
import KeySizeSelector from './KeySizeSelector.vue'

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['submit'])

const formData = reactive({
  algorithm: 'AES',
  keySize: 256
})

const errors = reactive({
  algorithm: '',
  keySize: ''
})

watch(() => formData.algorithm, () => {
  errors.algorithm = ''
})

watch(() => formData.keySize, () => {
  errors.keySize = ''
})

function validateForm() {
  let isValid = true
  errors.algorithm = ''
  errors.keySize = ''

  if (!formData.algorithm) {
    errors.algorithm = 'Algorithm is required'
    isValid = false
  }

  if (!formData.keySize || formData.keySize < 128) {
    errors.keySize = 'Valid key size is required'
    isValid = false
  }

  return isValid
}

function handleSubmit() {
  if (validateForm()) {
    emit('submit', { ...formData })
  }
}
</script>
