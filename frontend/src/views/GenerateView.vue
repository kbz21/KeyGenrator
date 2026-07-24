<template>
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card shadow">
        <div class="card-body p-4">
          <h1 class="card-title text-center mb-4">
            <i class="bi bi-plus-circle-fill text-primary me-2"></i>
            Generate Key
          </h1>
          <KeyGenerator />
          
          <div v-if="currentKey" class="mt-4 fade-enter-active">
            <hr />
            <KeyDisplay :key="currentKey" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useKeys } from '@/composables/useKeys'
import KeyGenerator from '@/components/keys/KeyGenerator.vue'
import KeyDisplay from '@/components/keys/KeyDisplay.vue'

const { currentKey } = useKeys()
</script>
