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
          <span class="spinner-border spinner-border-sm me-2" role="status"></span>
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
  },
  initialAlgorithm: {
    type: String,
    default: 'AES'
  },
  initialKeySize: {
    type: Number,
    default: 256
  }
})

const emit = defineEmits(['submit', 'update:algorithm', 'update:keySize'])

const formData = reactive({
  algorithm: props.initialAlgorithm,
  keySize: props.initialKeySize
})

const errors = reactive({
  algorithm: '',
  keySize: ''
})

watch(() => formData.algorithm, (newVal) => {
  emit('update:algorithm', newVal)
})

watch(() => formData.keySize, (newVal) => {
  emit('update:keySize', newVal)
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
