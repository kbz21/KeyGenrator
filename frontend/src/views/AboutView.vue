<template>
  <div class="container py-4">
    <div class="row">
      <div class="col-lg-8 mx-auto">
        <div class="about-card">
          <h1 class="about-title">About Key Generator</h1>
          
          <div class="about-section">
            <h3>🔑 What is Key Generator?</h3>
            <p>
              Key Generator is a secure web application that allows you to generate 
              cryptographic keys using industry-standard algorithms like AES and RSA.
            </p>
          </div>

          <div class="about-section">
            <h3>🛠️ Technologies Used</h3>
            <div class="tech-badges">
              <span class="tech-badge">Vue.js 3</span>
              <span class="tech-badge">Spring Boot</span>
              <span class="tech-badge">MariaDB</span>
              <span class="tech-badge">Bootstrap 5</span>
              <span class="tech-badge">JCA</span>
            </div>
          </div>

          <div class="about-section">
            <h3>🔐 Security</h3>
            <p>
              Keys are generated using the Java Cryptography Architecture (JCA) 
              and stored securely in a database. All keys are Base64 encoded for 
              easy storage and transmission.
            </p>
          </div>

          <div class="about-section">
            <h3>📝 Features</h3>
            <ul class="feature-list">
              <li><i class="bi bi-check-circle text-success"></i> Generate AES keys (128, 192, 256 bits)</li>
              <li><i class="bi bi-check-circle text-success"></i> Generate RSA keys (1024, 2048, 4096 bits)</li>
              <li><i class="bi bi-check-circle text-success"></i> Copy keys to clipboard</li>
              <li><i class="bi bi-check-circle text-success"></i> Download keys as text files</li>
              <li><i class="bi bi-check-circle text-success"></i> View key history</li>
              <li><i class="bi bi-check-circle text-success"></i> Multi-language support (English/Amharic)</li>
              <li><i class="bi bi-check-circle text-success"></i> Dark/Light/System theme</li>
            </ul>
          </div>

          <div class="about-footer">
            <p>
              Built using Vue.js &amp; Spring Boot
              <br />
              <small>&copy; {{ currentYear }} - All rights reserved</small>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const currentYear = ref('')

onMounted(() => {
  currentYear.value = new Date().getFullYear()
})
</script>

<style scoped>
.about-card {
  background: var(--bg-card);
  border-radius: 16px;
  padding: 2.5rem;
  border: 1px solid var(--border-color);
  box-shadow: 0 2px 16px var(--shadow-color);
}

.about-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 1.5rem;
  text-align: center;
}

.about-section {
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid var(--border-color);
}

.about-section:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.about-section h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.about-section p {
  color: var(--text-secondary);
  line-height: 1.6;
}

.tech-badges {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.tech-badge {
  background: var(--bg-secondary);
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 500;
  color: var(--text-primary);
  border: 1px solid var(--border-color);
}

.feature-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.feature-list li {
  padding: 0.4rem 0;
  color: var(--text-secondary);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.feature-list li i {
  font-size: 1.1rem;
}

.about-footer {
  text-align: center;
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 2px solid var(--border-color);
}

.about-footer p {
  color: var(--text-secondary);
  margin: 0;
}
</style>
