<template>
  <div class="row">
    <!-- Hero Section -->
    <div class="col-12 text-center mb-5">
      <div class="bg-white bg-opacity-25 p-5 rounded-3">
        <i class="bi bi-key display-1 text-white"></i>
        <h1 class="display-3 text-white fw-bold">Key Generator</h1>
        <p class="lead text-white">Generate secure encryption keys with ease</p>
      </div>
    </div>

    <!-- Generator -->
    <div class="col-lg-6 mb-4">
      <KeyGenerator />
    </div>
    
    <!-- Display -->
    <div class="col-lg-6">
      <div v-if="currentKey" class="fade-enter-active">
        <KeyDisplay :key="currentKey" />
      </div>
      <div v-else class="card shadow text-center py-5">
        <div class="card-body">
          <i class="bi bi-shield-lock display-1 text-muted"></i>
          <p class="text-muted mt-3">Generate a key to see it here</p>
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
