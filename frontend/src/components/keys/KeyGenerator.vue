<template>
  <div class="card card-hover shadow">
    <div class="card-body">
      <h2 class="card-title text-center mb-4">
        <i class="bi bi-plus-circle-fill text-primary me-2"></i>
        Generate New Key
      </h2>
      <KeyForm
        :loading="loading"
        :error="error"
        @submit="handleGenerate"
      />
    </div>
  </div>
</template>

<script setup>
import KeyForm from '@/components/forms/KeyForm.vue'
import { useKeys } from '@/composables/useKeys'

const { generateKey, loading, error, clearError } = useKeys()

async function handleGenerate(data) {
  clearError()
  await generateKey(data.algorithm, data.keySize)
}
</script>
