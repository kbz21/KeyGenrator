<template>
  <div class="container py-4">
    <div class="row">
      <div class="col-12">
        <h1 class="page-title">Algorithms</h1>
        <p class="page-subtitle">Supported encryption algorithms for key generation</p>
      </div>
    </div>

    <div class="row mt-4">
      <!-- AES Card -->
      <div class="col-lg-6 mb-4">
        <div class="algorithm-card">
          <div class="algorithm-header">
            <span class="algorithm-icon">🔐</span>
            <div>
              <h3 class="algorithm-name">AES</h3>
              <span class="algorithm-type">Symmetric Encryption</span>
            </div>
          </div>
          <div class="algorithm-body">
            <p class="algorithm-description">
              Advanced Encryption Standard (AES) is a symmetric encryption algorithm 
              widely used for secure data encryption.
            </p>
            <div class="algorithm-details">
              <div class="detail-chip">
                <span class="chip-label">Key Sizes</span>
                <span class="chip-value">128, 192, 256 bits</span>
              </div>
              <div class="detail-chip">
                <span class="chip-label">Use Cases</span>
                <span class="chip-value">Data Encryption, Secure Communication</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- RSA Card -->
      <div class="col-lg-6 mb-4">
        <div class="algorithm-card">
          <div class="algorithm-header">
            <span class="algorithm-icon">🔑</span>
            <div>
              <h3 class="algorithm-name">RSA</h3>
              <span class="algorithm-type">Asymmetric Encryption</span>
            </div>
          </div>
          <div class="algorithm-body">
            <p class="algorithm-description">
              Rivest-Shamir-Adleman (RSA) is an asymmetric encryption algorithm 
              used for secure data transmission and digital signatures.
            </p>
            <div class="algorithm-details">
              <div class="detail-chip">
                <span class="chip-label">Key Sizes</span>
                <span class="chip-value">1024, 2048, 4096 bits</span>
              </div>
              <div class="detail-chip">
                <span class="chip-label">Use Cases</span>
                <span class="chip-value">Digital Signatures, Key Exchange</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// Algorithms page
</script>

<style scoped>
.page-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.25rem;
}

.page-subtitle {
  color: var(--text-secondary);
  font-size: 1rem;
}

.algorithm-card {
  background: var(--bg-card);
  border-radius: 16px;
  padding: 1.5rem;
  border: 1px solid var(--border-color);
  transition: all 0.3s ease;
  height: 100%;
}

.algorithm-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 32px var(--shadow-color);
}

.algorithm-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid var(--border-color);
}

.algorithm-icon {
  font-size: 2.5rem;
}

.algorithm-name {
  font-size: 1.3rem;
  font-weight: 700;
  margin: 0;
  color: var(--text-primary);
}

.algorithm-type {
  font-size: 0.8rem;
  color: var(--text-secondary);
  display: block;
}

.algorithm-description {
  color: var(--text-secondary);
  font-size: 0.95rem;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.algorithm-details {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.detail-chip {
  background: var(--bg-secondary);
  padding: 0.5rem 1rem;
  border-radius: 8px;
  flex: 1;
  min-width: 150px;
}

.chip-label {
  display: block;
  font-size: 0.65rem;
  text-transform: uppercase;
  color: var(--text-secondary);
  letter-spacing: 0.5px;
}

.chip-value {
  display: block;
  font-weight: 500;
  color: var(--text-primary);
  font-size: 0.85rem;
  margin-top: 0.1rem;
}
</style>
