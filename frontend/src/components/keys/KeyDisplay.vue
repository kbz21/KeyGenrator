<template>
  <div v-if="key" class="key-display">
    <!-- Header -->
    <div class="key-header">
      <span class="key-title">
        <i class="bi bi-clipboard-check text-success me-2"></i>
        Generated Key
      </span>
      <span class="badge bg-success">Active</span>
    </div>

    <!-- Alias -->
    <div v-if="key.alias" class="key-alias">
      <i class="bi bi-tag me-1"></i>
      <strong>{{ key.alias }}</strong>
    </div>

    <!-- Key Value -->
    <div class="key-value-wrapper">
      <pre class="key-value">{{ key.generatedKey }}</pre>
      <div class="key-actions">
        <button @click="copyKey" class="action-btn copy-btn">
          <i :class="isCopied ? 'bi bi-check-lg' : 'bi bi-clipboard'"></i>
          {{ isCopied ? 'Copied' : 'Copy' }}
        </button>
      </div>
    </div>

    <!-- Export Options -->
    <div class="export-section">
      <label class="export-label">
        <i class="bi bi-download me-1"></i>
        Export Key As:
      </label>
      <div class="export-buttons">
        <button @click="exportKey('txt')" class="export-btn txt-btn">
          <i class="bi bi-file-text"></i> TXT
        </button>
        <button @click="exportKey('json')" class="export-btn json-btn">
          <i class="bi bi-file-code"></i> JSON
        </button>
        <button @click="exportKey('pem')" class="export-btn pem-btn">
          <i class="bi bi-file-lock"></i> PEM
        </button>
      </div>
    </div>

    <!-- Key Details -->
    <div class="key-details">
      <div class="detail-item">
        <span class="detail-label">ID</span>
        <span class="detail-value">#{{ key.id }}</span>
      </div>
      <div class="detail-item">
        <span class="detail-label">Algorithm</span>
        <span class="detail-value">{{ key.algorithm }}</span>
      </div>
      <div class="detail-item">
        <span class="detail-label">Key Size</span>
        <span class="detail-value">{{ key.keySize }} bits</span>
      </div>
      <div class="detail-item">
        <span class="detail-label">Alias</span>
        <span class="detail-value">{{ key.alias || '—' }}</span>
      </div>
      <div class="detail-item">
        <span class="detail-label">Created</span>
        <span class="detail-value">{{ formatDate(key.createdAt) }}</span>
      </div>
    </div>

    <!-- Security Note -->
    <div class="security-note">
      <i class="bi bi-shield-exclamation me-2"></i>
      Never share this key publicly. Store it securely.
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useClipboard } from '@/composables/useClipboard'
import { useExport } from '@/composables/useExport'
import { toast } from 'vue3-toastify'

const props = defineProps({
  key: {
    type: Object,
    required: true
  }
})

const { copied: isCopied, copyToClipboard } = useClipboard()
const { exportAsText, exportAsJSON, exportAsPEM } = useExport()

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const copyKey = async () => {
  await copyToClipboard(props.key.generatedKey)
}

const exportKey = (format) => {
  const key = props.key
  const filename = `${key.algorithm}-${key.keySize}-${key.id}`
  
  console.log('🔑 Exporting key:', key.id, 'Format:', format)
  
  try {
    switch(format) {
      case 'txt':
        exportAsText(key, filename)
        toast.success('📄 Key exported as TXT!')
        break
      case 'json':
        exportAsJSON(key, filename)
        toast.success('📋 Key exported as JSON!')
        break
      case 'pem':
        exportAsPEM(key, filename)
        toast.success('🔐 Key exported as PEM!')
        break
      default:
        toast.error('Unknown export format')
    }
  } catch (error) {
    console.error('Export error:', error)
    toast.error('Failed to export key: ' + error.message)
  }
}
</script>

<style scoped>
.key-display {
  background: var(--bg-card);
  border-radius: 16px;
  padding: 1.5rem;
  border: 1px solid var(--border-color);
}

.key-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid var(--border-color);
}

.key-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--text-primary);
}

.key-alias {
  background: var(--bg-secondary);
  padding: 0.4rem 0.75rem;
  border-radius: 8px;
  margin-bottom: 0.75rem;
  font-size: 0.9rem;
  color: var(--text-primary);
}

.key-value-wrapper {
  background: var(--bg-secondary);
  border-radius: 10px;
  padding: 1rem;
  position: relative;
  margin-bottom: 1rem;
}

.key-value {
  font-family: 'Courier New', monospace;
  font-size: 0.85rem;
  word-break: break-all;
  white-space: pre-wrap;
  margin: 0;
  padding-right: 80px;
  color: var(--text-primary);
  max-height: 150px;
  overflow-y: auto;
}

.key-actions {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
}

.action-btn {
  padding: 0.4rem 0.75rem;
  border: none;
  border-radius: 6px;
  font-size: 0.8rem;
  transition: all 0.3s ease;
  cursor: pointer;
}

.copy-btn {
  background: var(--border-color);
  color: var(--text-primary);
}

.copy-btn:hover {
  background: #667eea;
  color: white;
}

.copy-btn.copied {
  background: #28a745;
  color: white;
}

.export-section {
  background: var(--bg-secondary);
  padding: 0.75rem 1rem;
  border-radius: 10px;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  flex-wrap: wrap;
}

.export-label {
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--text-secondary);
  margin: 0;
}

.export-buttons {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.export-btn {
  padding: 0.3rem 0.75rem;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  font-size: 0.8rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  background: var(--bg-card);
  color: var(--text-primary);
}

.export-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px var(--shadow-color);
}

.txt-btn:hover {
  border-color: #3498db;
  color: #3498db;
}

.json-btn:hover {
  border-color: #f39c12;
  color: #f39c12;
}

.pem-btn:hover {
  border-color: #2ecc71;
  color: #2ecc71;
}

.key-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.detail-item {
  background: var(--bg-secondary);
  padding: 0.6rem;
  border-radius: 8px;
  text-align: center;
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
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-top: 0.1rem;
}

.security-note {
  background: #fff3cd;
  border-left: 4px solid #ffc107;
  padding: 0.6rem 1rem;
  border-radius: 8px;
  font-size: 0.8rem;
  color: #856404;
}
</style>
