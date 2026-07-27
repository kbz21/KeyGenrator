<template>
  <div class="container py-4">
    <!-- Hero Section -->
    <div class="row">
      <div class="col-12">
        <div class="hero-section text-center">
          <div class="hero-icon">🔑</div>
          <h1 class="hero-title">Key Generator</h1>
          <p class="hero-subtitle">Generate cryptographic keys by selecting an algorithm and key size.</p>
          <div class="hero-buttons mt-4">
            <router-link to="/generate" class="btn btn-primary btn-lg me-3">
              <i class="bi bi-plus-circle me-2"></i>
              Generate Key
            </router-link>
            <router-link to="/keys" class="btn btn-outline-light btn-lg">
              <i class="bi bi-clock-history me-2"></i>
              View History
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Feature Cards -->
    <div class="row mt-5 g-4">
      <div class="col-md-4">
        <div class="feature-card">
          <div class="feature-icon">
            <i class="bi bi-shield-check"></i>
          </div>
          <h3 class="feature-title">Secure Keys</h3>
          <p class="feature-text">Generate AES and RSA encryption keys using Java Cryptography Architecture (JCA)</p>
        </div>
      </div>
      <div class="col-md-4">
        <div class="feature-card">
          <div class="feature-icon">
            <i class="bi bi-clipboard-check"></i>
          </div>
          <h3 class="feature-title">Copy to Clipboard</h3>
          <p class="feature-text">Copy generated keys with one click and use them in your applications</p>
        </div>
      </div>
      <div class="col-md-4">
        <div class="feature-card">
          <div class="feature-icon">
            <i class="bi bi-database"></i>
          </div>
          <h3 class="feature-title">Secure Storage</h3>
          <p class="feature-text">All keys are stored securely in database with timestamp and metadata</p>
        </div>
      </div>
    </div>

    <!-- Quick Stats -->
    <div class="row mt-5">
      <div class="col-12">
        <div class="stats-section">
          <div class="stat-item">
            <span class="stat-number">{{ keyCount }}</span>
            <span class="stat-label">Total Keys</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-number">{{ aesCount }}</span>
            <span class="stat-label">AES Keys</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-number">{{ rsaCount }}</span>
            <span class="stat-label">RSA Keys</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-number">2</span>
            <span class="stat-label">Algorithms</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useKeyStore } from '@/store'

const store = useKeyStore()

const keyCount = computed(() => store.keyCount)
const aesCount = computed(() => store.aesKeys.length)
const rsaCount = computed(() => store.rsaKeys.length)

onMounted(() => {
  store.fetchAllKeys()
})
</script>

<style scoped>
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
  padding: 3.5rem 2rem;
  color: white;
  position: relative;
  overflow: hidden;
}

.hero-section::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -20%;
  width: 60%;
  height: 200%;
  background: rgba(255, 255, 255, 0.05);
  transform: rotate(30deg);
  border-radius: 50%;
}

.hero-icon {
  font-size: 4rem;
  margin-bottom: 0.5rem;
  animation: bounce 2s infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.hero-title {
  font-size: 2.8rem;
  font-weight: 800;
  margin-bottom: 0.5rem;
  position: relative;
}

.hero-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  position: relative;
}

.hero-buttons .btn-primary {
  background: white;
  color: #667eea;
  border: none;
  font-weight: 600;
  padding: 0.75rem 2rem;
}

.hero-buttons .btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
}

.hero-buttons .btn-outline-light {
  border: 2px solid rgba(255, 255, 255, 0.5);
  font-weight: 500;
  padding: 0.75rem 2rem;
}

.hero-buttons .btn-outline-light:hover {
  background: rgba(255, 255, 255, 0.15);
}

.feature-card {
  background: var(--bg-card);
  padding: 2rem;
  border-radius: 16px;
  text-align: center;
  border: 1px solid var(--border-color);
  transition: all 0.3s ease;
  height: 100%;
}

.feature-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 12px 40px var(--shadow-color);
}

.feature-icon {
  font-size: 2.5rem;
  color: #667eea;
  margin-bottom: 0.75rem;
}

.feature-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: var(--text-primary);
}

.feature-text {
  color: var(--text-secondary);
  margin-bottom: 0;
  font-size: 0.9rem;
  line-height: 1.5;
}

.stats-section {
  background: var(--bg-card);
  border-radius: 16px;
  padding: 2rem;
  border: 1px solid var(--border-color);
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
}

.stat-item {
  text-align: center;
  padding: 0.5rem 1rem;
}

.stat-number {
  display: block;
  font-size: 2rem;
  font-weight: 700;
  color: #667eea;
}

.stat-label {
  display: block;
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin-top: 0.25rem;
}

.stat-divider {
  width: 1px;
  height: 50px;
  background: var(--border-color);
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }
  
  .hero-buttons .btn {
    display: block;
    width: 100%;
    margin: 0.5rem 0 !important;
  }
  
  .stats-section {
    flex-direction: column;
    gap: 1rem;
  }
  
  .stat-divider {
    width: 80%;
    height: 1px;
  }
}
</style>
