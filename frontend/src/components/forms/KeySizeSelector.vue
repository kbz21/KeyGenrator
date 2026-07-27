<template>
  <div class="mb-3">
    <label :for="id" class="form-label fw-bold">
      <i class="bi bi-rulers me-2"></i>
      Key Size (bits)
    </label>
    <select
      :id="id"
      class="form-select form-select-lg"
      :value="modelValue"
      @change="$emit('update:modelValue', parseInt($event.target.value))"
      :disabled="disabled"
      :class="{ 'is-invalid': error }"
    >
      <option 
        v-for="size in availableSizes" 
        :key="size"
        :value="size"
      >
        {{ size }} bits
      </option>
    </select>
    <div v-if="error" class="invalid-feedback">{{ error }}</div>
    <small class="text-muted">
      <i class="bi bi-info-circle"></i>
      Recommended: <strong>{{ recommendedSize }}</strong> bits for {{ algorithm }}
    </small>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: {
    type: Number,
    required: true
  },
  id: {
    type: String,
    default: 'keySize'
  },
  algorithm: {
    type: String,
    default: 'AES'
  },
  disabled: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

const algorithmSizes = {
  AES: [128, 192, 256],
  RSA: [1024, 2048, 4096]
}

const availableSizes = computed(() => {
  return algorithmSizes[props.algorithm] || algorithmSizes.AES
})

const recommendedSize = computed(() => {
  return props.algorithm === 'AES' ? 256 : 2048
})
</script>
