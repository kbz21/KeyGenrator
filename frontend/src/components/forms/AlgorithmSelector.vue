<template>
  <div class="mb-3">
    <label :for="id" class="form-label fw-bold">
      <i class="bi bi-code-square me-2"></i>
      Algorithm
    </label>
    <select
      :id="id"
      class="form-select form-select-lg"
      :value="modelValue"
      @change="$emit('update:modelValue', $event.target.value)"
      :disabled="disabled"
      :class="{ 'is-invalid': error }"
    >
      <option 
        v-for="option in options" 
        :key="option.value"
        :value="option.value"
      >
        {{ option.label }}
      </option>
    </select>
    <div v-if="error" class="invalid-feedback">{{ error }}</div>
  </div>
</template>

<script setup>
const props = defineProps({
  modelValue: {
    type: String,
    required: true
  },
  id: {
    type: String,
    default: 'algorithm'
  },
  options: {
    type: Array,
    default: () => [
      { value: 'AES', label: 'AES (Symmetric)' },
      { value: 'RSA', label: 'RSA (Asymmetric)' }
    ]
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

defineEmits(['update:modelValue'])
</script>
