<template>
  <footer class="bg-dark text-white py-4 mt-auto">
    <div class="container text-center">
      <p class="mb-0">
        <i class="bi bi-shield-lock me-2"></i>
        Secure Key Generator &bull; Built with Vue.js &amp; Spring Boot
      </p>
      <small class="text-muted">
        &copy; {{ currentYear }} - All rights reserved
      </small>
    </div>
  </footer>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const currentYear = ref('')

onMounted(() => {
  currentYear.value = new Date().getFullYear()
})
</script>
