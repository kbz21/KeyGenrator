<template>
  <div class="container py-4">
    <div class="row">
      <!-- Left Column - Generate Form -->
      <div class="col-lg-6">
        <div class="generate-card">
          <h2 class="card-title">Generate New Key</h2>
          <p class="card-subtitle">Generate cryptographic keys by selecting an algorithm and key size.</p>

          <form @submit.prevent="handleGenerate" class="generate-form">
            <!-- Algorithm Selection -->
            <div class="form-group">
              <label class="form-label">
                Algorithm
                <span class="required">*</span>
              </label>
              <div class="algorithm-options">
                <button
                  v-for="algo in algorithms"
                  :key="algo.value"
                  type="button"
                  class="algorithm-btn"
                  :class="{ active: formData.algorithm === algo.value }"
                  @click="formData.algorithm = algo.value"
                >
                  {{ algo.label }}
                </button>
              </div>
              <p class="helper-text">
                <i class="bi bi-info-circle"></i>
                Select the algorithm for key generation.
              </p>
            </div>

            <!-- Key Size -->
            <div class="form-group">
              <label class="form-label">
                Key Size
                <span class="required">*</span>
              </label>
              <div class="size-options">
                <button
                  v-for="size in availableSizes"
                  :key="size"
                  type="button"
                  class="size-btn"
                  :class="{ active: formData.keySize === size }"
                  @click="formData.keySize = size"
                >
                  {{ size }}
                </button>
              </div>
              <p class="helper-text">
                <i class="bi bi-info-circle"></i>
                Select the key size in bits.
              </p>
            </div>

            <!-- Key Alias (Optional) -->
            <div class="form-group">
              <label class="form-label">Key Alias (Optional)</label>
              <input
                v-model="formData.alias"
                type="text"
                class="form-control"
                placeholder="Enter a name for this key"
              />
              <p class="helper-text">
                <i class="bi bi-info-circle"></i>
                Optional alias to identify this key.
              </p>
            </div>

            <!-- Key Purpose (Optional) -->
            <div class="form-group">
              <label class="form-label">Key Purpose (Optional)</label>
              <select v-model="formData.purpose" class="form-select">
                <option value="">Select the purpose of this key</option>
                <option value="encryption">Encryption/Decryption</option>
                <option value="signing">Signing/Verification</option>
                <option value="both">Both</option>
              </select>
              <p class="helper-text">
                <i class="bi bi-info-circle"></i>
                Select the purpose of this key.
              </p>
            </div>

            <!-- Info Checkbox -->
            <div class="info-checkbox">
              <i class="bi bi-check-square"></i>
              <span>Key will be generated when you specify an algorithm and key size.</span>
            </div>

            <!-- Generate Button -->
            <button
              type="submit"
              class="generate-btn"
              :disabled="loading"
            >
              <span v-if="!loading">
                <i class="bi bi-key me-2"></i>
                Generate Key
              </span>
              <span v-else>
                <span class="spinner-border spinner-border-sm me-2"></span>
                Generating...
              </span>
            </button>

            <div v-if="error" class="alert alert-danger mt-3">
              <i class="bi bi-exclamation-triangle-fill me-2"></i>
              {{ error }}
            </div>
          </form>
        </div>
      </div>

      <!-- Right Column - Key Display -->
      <div class="col-lg-6">
        <div v-if="currentKey" class="display-card">
          <h3 class="display-title">Generated Key</h3>
          
          <!-- Key Details -->
          <div class="key-details-grid">
            <div class="key-detail-item">
              <span class="detail-label">Algorithm</span>
              <span class="detail-value">{{ currentKey.algorithm }}</span>
            </div>
            <div class="key-detail-item">
              <span class="detail-label">Key Size</span>
              <span class="detail-value">{{ currentKey.keySize }} bits</span>
            </div>
            <div class="key-detail-item">
              <span class="detail-label">Generated At</span>
              <span class="detail-value">{{ formatDate(currentKey.createdAt) }}</span>
            </div>
            <div class="key-detail-item">
              <span class="detail-label">Key Alias</span>
              <span class="detail-value">{{ currentKey.alias || '—' }}</span>
            </div>
            <div class="key-detail-item">
              <span class="detail-label">Purpose</span>
              <span class="detail-value">{{ currentKey.purpose || '—' }}</span>
            </div>
          </div>

          <!-- Action Buttons -->
          <div class="action-buttons">
            <button @click="copyToClipboard(currentKey.generatedKey)" class="action-btn copy-action">
              <i class="bi bi-clipboard me-1"></i> Copy
            </button>
            <button @click="downloadKey" class="action-btn download-action">
              <i class="bi bi-download me-1"></i> Download
            </button>
            <button @click="clearKey" class="action-btn clear-action">
              <i class="bi bi-x-circle me-1"></i> Clear
            </button>
          </div>

          <!-- Key Value -->
          <div class="key-value-section">
            <label class="key-value-label">Key (Base64)</label>
            <div class="key-value-wrapper">
              <pre class="key-value">{{ currentKey.generatedKey }}</pre>
              <button
                @click="copyToClipboard(currentKey.generatedKey)"
                class="copy-btn"
                :class="{ copied: isCopied }"
              >
                <i :class="isCopied ? 'bi bi-check-lg' : 'bi bi-clipboard'"></i>
                {{ isCopied ? 'Copied' : 'Copy' }}
              </button>
            </div>
          </div>

          <!-- Security Note -->
          <div class="security-note">
            <i class="bi bi-shield-check me-2"></i>
            <span>Keys are generated securely using Java Cryptography Architecture (JCA).</span>
          </div>
        </div>

        <!-- Empty State -->
        <div v-else class="display-card empty">
          <div class="empty-state">
            <i class="bi bi-key display-1 text-muted"></i>
            <p class="text-muted mt-3">Generate a key to see it here</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, computed, watch, ref } from 'vue'
import { useKeys } from '@/composables/useKeys'
import { useClipboard } from '@/composables/useClipboard'
import { toast } from 'vue3-toastify'

const { currentKey, loading, error, generateKey, clearError, deleteKey } = useKeys()
const { copied: isCopied, copyToClipboard } = useClipboard()

const algorithms = [
  { value: 'AES', label: 'AES (Advanced Encryption Standard)' },
  { value: 'RSA', label: 'RSA (Rivest-Shamir-Adleman)' }
]

const algorithmSizes = {
  AES: [128, 192, 256],
  RSA: [1024, 2048, 4096]
}

const formData = reactive({
  algorithm: 'AES',
  keySize: 256,
  alias: '',
  purpose: ''
})

const availableSizes = computed(() => {
  return algorithmSizes[formData.algorithm] || algorithmSizes.AES
})

watch(() => formData.algorithm, () => {
  const sizes = algorithmSizes[formData.algorithm]
  if (!sizes.includes(formData.keySize)) {
    formData.keySize = sizes[0]
  }
})

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

async function handleGenerate() {
  clearError()
  const result = await generateKey(formData.algorithm, formData.keySize, formData.alias, formData.purpose)
  if (result) {
    toast.success('Key generated successfully! 🎉')
  }
}

function downloadKey() {
  if (!currentKey.value) return
  const blob = new Blob([currentKey.value.generatedKey], { type: 'text/plain' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `key_${currentKey.value.algorithm}_${currentKey.value.keySize}.txt`
  a.click()
  URL.revokeObjectURL(url)
  toast.success('Key downloaded! 📥')
}

function clearKey() {
  if (currentKey.value) {
    deleteKey(currentKey.value.id)
  }
  toast.info('Key cleared')
}
</script>

<style scoped>
.generate-card,
.display-card {
  background: var(--bg-card);
  border-radius: 16px;
  padding: 2rem;
  box-shadow: 0 2px 16px var(--shadow-color);
  border: 1px solid var(--border-color);
  height: 100%;
}

.card-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.card-subtitle {
  color: var(--text-secondary);
  font-size: 0.95rem;
  margin-bottom: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.required {
  color: #dc2626;
  margin-left: 2px;
}

.algorithm-options {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.algorithm-btn {
  padding: 0.6rem 1.25rem;
  border: 2px solid var(--border-color);
  background: var(--bg-secondary);
  color: var(--text-primary);
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  cursor: pointer;
}

.algorithm-btn:hover {
  border-color: #667eea;
}

.algorithm-btn.active {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.size-options {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.size-btn {
  min-width: 60px;
  padding: 0.6rem 1.25rem;
  border: 2px solid var(--border-color);
  background: var(--bg-secondary);
  color: var(--text-primary);
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.3s ease;
  cursor: pointer;
}

.size-btn:hover {
  border-color: #667eea;
}

.size-btn.active {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.helper-text {
  font-size: 0.8rem;
  color: var(--text-secondary);
  margin-top: 0.4rem;
  margin-bottom: 0;
}

.helper-text i {
  color: #667eea;
}

.form-control,
.form-select {
  background: var(--bg-secondary);
  border: 2px solid var(--border-color);
  border-radius: 8px;
  padding: 0.6rem 1rem;
  color: var(--text-primary);
  width: 100%;
  transition: border-color 0.3s ease;
}

.form-control:focus,
.form-select:focus {
  outline: none;
  border-color: #667eea;
}

.info-checkbox {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: #e8f0fe;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  color: #1a56db;
  font-size: 0.9rem;
  margin-bottom: 1.25rem;
}

.info-checkbox i {
  font-size: 1.2rem;
}

.generate-btn {
  width: 100%;
  padding: 0.85rem;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  cursor: pointer;
}

.generate-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(102, 126, 234, 0.4);
}

.generate-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.display-card {
  display: flex;
  flex-direction: column;
}

.display-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 1.25rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid var(--border-color);
}

.key-details-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 0.75rem;
  margin-bottom: 1.25rem;
}

.key-detail-item {
  background: var(--bg-secondary);
  padding: 0.6rem 0.75rem;
  border-radius: 8px;
}

.detail-label {
  display: block;
  font-size: 0.65rem;
  text-transform: uppercase;
  color: var(--text-secondary);
  letter-spacing: 0.5px;
}

.detail-value {
  display: block;
  font-weight: 600;
  color: var(--text-primary);
  margin-top: 0.1rem;
  font-size: 0.9rem;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1.25rem;
}

.action-btn {
  padding: 0.4rem 1rem;
  border: none;
  border-radius: 6px;
  font-size: 0.85rem;
  font-weight: 500;
  transition: all 0.3s ease;
  cursor: pointer;
}

.copy-action {
  background: #e8f0fe;
  color: #1a56db;
}

.copy-action:hover {
  background: #d2e3ff;
}

.download-action {
  background: #e8f5e9;
  color: #2e7d32;
}

.download-action:hover {
  background: #c8e6c9;
}

.clear-action {
  background: #fbe9e7;
  color: #c62828;
}

.clear-action:hover {
  background: #ffcdd2;
}

.key-value-section {
  flex: 1;
}

.key-value-label {
  display: block;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
}

.key-value-wrapper {
  background: var(--bg-secondary);
  border-radius: 8px;
  padding: 0.75rem;
  position: relative;
  border: 1px solid var(--border-color);
}

.key-value {
  font-family: 'Courier New', monospace;
  font-size: 0.7rem;
  word-break: break-all;
  white-space: pre-wrap;
  margin: 0;
  padding-right: 70px;
  color: var(--text-primary);
  max-height: 100px;
  overflow-y: auto;
  line-height: 1.4;
}

.copy-btn {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  padding: 0.25rem 0.6rem;
  border: none;
  border-radius: 6px;
  background: var(--border-color);
  color: var(--text-primary);
  font-size: 0.7rem;
  transition: all 0.3s ease;
  cursor: pointer;
}

.copy-btn:hover {
  background: #667eea;
  color: white;
}

.copy-btn.copied {
  background: #28a745;
  color: white;
}

.security-note {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: #e8f5e9;
  padding: 0.6rem 1rem;
  border-radius: 8px;
  color: #2e7d32;
  font-size: 0.8rem;
  margin-top: 1rem;
}

.empty-state {
  text-align: center;
  padding: 2rem 0;
}

.empty-state i {
  font-size: 4rem;
  opacity: 0.3;
}

@media (max-width: 992px) {
  .generate-card,
  .display-card {
    margin-bottom: 1.5rem;
  }
}

@media (max-width: 576px) {
  .key-details-grid {
    grid-template-columns: 1fr;
  }
  
  .algorithm-options,
  .size-options {
    flex-direction: column;
  }
  
  .algorithm-btn,
  .size-btn {
    width: 100%;
  }
  
  .action-buttons {
    flex-wrap: wrap;
  }
  
  .action-btn {
    flex: 1;
    text-align: center;
  }
}
</style>
