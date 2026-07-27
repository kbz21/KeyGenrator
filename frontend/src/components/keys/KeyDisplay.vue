<template>
  <div v-if="key" class="key-display">
    <div class="key-header">
      <span class="key-title">
        <i class="bi bi-clipboard-check text-success me-2"></i>
        Generated Key
      </span>
      <span class="badge bg-success">Active</span>
    </div>

    <div class="key-value-wrapper">
      <pre class="key-value">{{ key.generatedKey }}</pre>
      <button
        @click="copyToClipboard(key.generatedKey)"
        class="copy-btn"
        :class="{ copied: isCopied }"
      >
        <i :class="isCopied ? 'bi bi-check-lg' : 'bi bi-clipboard'"></i>
        {{ isCopied ? 'Copied' : 'Copy' }}
      </button>
    </div>

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
        <span class="detail-label">Created</span>
        <span class="detail-value">{{ formatDate(key.createdAt) }}</span>
      </div>
    </div>

    <div class="security-note">
      <i class="bi bi-shield-exclamation me-2"></i>
      Never share this key publicly. Store it securely.
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useClipboard } from '@/composables/useClipboard'

const props = defineProps({
  key: {
    type: Object,
    required: true
  }
})

const { copied: isCopied, copyToClipboard } = useClipboard()

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
  margin-bottom: 1rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid var(--border-color);
}

.key-title {
  font-size: 1.1rem;
  font-weight: 600;
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
}

.copy-btn {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
  padding: 0.4rem 0.75rem;
  border: none;
  border-radius: 6px;
  background: var(--border-color);
  color: var(--text-primary);
  font-size: 0.8rem;
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

.key-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 1rem;
  margin-bottom: 1rem;
}

.detail-item {
  background: var(--bg-secondary);
  padding: 0.75rem;
  border-radius: 8px;
  text-align: center;
}

.detail-label {
  display: block;
  font-size: 0.7rem;
  text-transform: uppercase;
  color: var(--text-secondary);
  letter-spacing: 0.5px;
}

.detail-value {
  display: block;
  font-size: 0.95rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-top: 0.25rem;
}

.security-note {
  background: #fff3cd;
  border-left: 4px solid #ffc107;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  font-size: 0.85rem;
  color: #856404;
}
</style>
