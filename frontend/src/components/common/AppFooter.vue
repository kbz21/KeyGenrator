<template>
  <footer class="footer">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-md-6 text-center text-md-start">
          <span class="footer-brand">🔑 Key Generator</span>
          <span class="footer-divider">|</span>
          <span class="footer-text">Secure encryption key management</span>
        </div>
        <div class="col-md-6 text-center text-md-end">
          <span class="footer-text">
            &copy; {{ currentYear }} - Built with Vue.js &amp; Spring Boot
          </span>
        </div>
      </div>
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

<style scoped>
.footer {
  background: #2d3436;
  color: #dfe6e9;
  padding: 1.5rem 0;
  margin-top: auto;
}

.footer-brand {
  font-weight: 700;
  font-size: 1.1rem;
}

.footer-divider {
  margin: 0 1rem;
  color: #636e72;
}

.footer-text {
  color: #b2bec3;
  font-size: 0.95rem;
}

@media (max-width: 768px) {
  .footer-divider {
    display: none;
  }
  .footer-text {
    display: block;
    margin-top: 0.5rem;
  }
}
</style>
