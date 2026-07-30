<template>
  <div class="key-list">
    <div class="list-header">
      <h2 class="list-title">
        <i class="bi bi-list-ul text-primary me-2"></i>
        My Keys
      </h2>
      <div class="list-actions">
        <span class="badge bg-secondary">{{ keys.length }} keys</span>
        <button 
          v-if="keys.length > 0"
          @click="exportAllKeys" 
          class="btn btn-success btn-sm"
        >
          <i class="bi bi-download me-1"></i> Export All
        </button>
      </div>
    </div>

    <!-- Search & Filters -->
    <div class="list-filters">
      <div class="search-box">
        <i class="bi bi-search"></i>
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search by key, alias, or algorithm..."
          class="form-control form-control-sm"
        />
      </div>
      <div class="filter-group">
        <select v-model="filterAlgorithm" class="form-select form-select-sm">
          <option value="ALL">All</option>
          <option value="AES">AES</option>
          <option value="RSA">RSA</option>
        </select>
        <select v-model="sortBy" class="form-select form-select-sm">
          <option value="newest">Newest</option>
          <option value="oldest">Oldest</option>
        </select>
        <button @click="fetchKeys" class="btn btn-outline-secondary btn-sm" title="Refresh">
          <i class="bi bi-arrow-clockwise"></i>
        </button>
      </div>
    </div>

    <!-- Loading -->
    <div v-if="loading" class="text-center py-4">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- Empty State -->
    <div v-else-if="filteredKeys.length === 0" class="empty-state">
      <i class="bi bi-inbox display-1 text-muted"></i>
      <p class="text-muted mt-3">No keys found</p>
      <router-link to="/generate" class="btn btn-primary">
        <i class="bi bi-plus-circle me-2"></i>
        Generate First Key
      </router-link>
    </div>

    <!-- Key Items -->
    <div v-else class="key-items">
      <div
        v-for="key in filteredKeys"
        :key="key.id"
        class="key-item"
      >
        <div class="key-item-info">
          <div class="key-item-badges">
            <span class="badge bg-primary">{{ key.algorithm }}</span>
            <span class="badge bg-secondary">{{ key.keySize }} bits</span>
            <span class="badge bg-light text-dark">#{{ key.id }}</span>
            <span v-if="key.alias" class="badge bg-info text-white">
              <i class="bi bi-tag me-1"></i>{{ key.alias }}
            </span>
          </div>
          <pre class="key-item-preview">{{ key.generatedKey.substring(0, 50) }}...</pre>
          <small class="key-item-date">
            <i class="bi bi-clock me-1"></i>
            {{ formatDate(key.createdAt) }}
          </small>
        </div>
        <div class="key-item-actions">
          <!-- Copy Button -->
          <button
            @click="copyKey(key.generatedKey)"
            class="action-btn copy-btn"
            title="Copy to clipboard"
          >
            <i class="bi bi-clipboard"></i>
          </button>
          
          <!-- Export Dropdown -->
          <div class="dropdown d-inline-block">
            <button
              class="action-btn export-btn dropdown-toggle"
              type="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              title="Export key"
            >
              <i class="bi bi-download"></i>
            </button>
            <ul class="dropdown-menu dropdown-menu-end">
              <li>
                <a class="dropdown-item" href="#" @click.prevent="exportSingleKey(key, 'txt')">
                  <i class="bi bi-file-text me-2"></i> Export as TXT
                </a>
              </li>
              <li>
                <a class="dropdown-item" href="#" @click.prevent="exportSingleKey(key, 'json')">
                  <i class="bi bi-file-code me-2"></i> Export as JSON
                </a>
              </li>
              <li>
                <a class="dropdown-item" href="#" @click.prevent="exportSingleKey(key, 'pem')">
                  <i class="bi bi-file-lock me-2"></i> Export as PEM
                </a>
              </li>
            </ul>
          </div>
          
          <!-- Delete Button -->
          <button
            @click="deleteKeyItem(key.id)"
            class="action-btn delete-btn"
            title="Delete key"
          >
            <i class="bi bi-trash"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useKeys } from '@/composables/useKeys'
import { useClipboard } from '@/composables/useClipboard'
import { useExport } from '@/composables/useExport'
import { toast } from 'vue3-toastify'

const {
  keys,
  loading,
  filteredKeys,
  searchQuery,
  filterAlgorithm,
  sortBy,
  fetchKeys,
  deleteKey
} = useKeys()

const { copyToClipboard } = useClipboard()
const { exportAsText, exportAsJSON, exportAsPEM, exportAllAsZip } = useExport()

onMounted(() => {
  fetchKeys()
})

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

const copyKey = async (key) => {
  await copyToClipboard(key)
  toast.success('📋 Key copied!')
}

const deleteKeyItem = async (id) => {
  if (confirm('Are you sure you want to delete this key?')) {
    await deleteKey(id)
    toast.success('🗑️ Key deleted!')
  }
}

const exportSingleKey = (key, format) => {
  const filename = `${key.algorithm}-${key.keySize}-${key.id}`
  
  console.log('📤 Exporting key:', key.id, 'as', format)
  
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
        toast.error('Unknown format')
    }
  } catch (error) {
    console.error('Export error:', error)
    toast.error('Failed to export: ' + error.message)
  }
}

const exportAllKeys = async () => {
  if (keys.value.length === 0) {
    toast.warning('No keys to export')
    return
  }
  
  try {
    await exportAllAsZip(keys.value, 'keys-backup')
    toast.success('📦 All keys exported as ZIP!')
  } catch (error) {
    console.error('Bulk export error:', error)
    toast.error('Failed to export all keys')
  }
}
</script>

<style scoped>
.key-list {
  background: var(--bg-card);
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 4px 20px var(--shadow-color);
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.list-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0;
  color: var(--text-primary);
}

.list-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.list-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
  margin-bottom: 1.5rem;
}

.search-box {
  flex: 1;
  min-width: 200px;
  position: relative;
}

.search-box i {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-secondary);
  z-index: 1;
}

.search-box input {
  padding-left: 32px;
}

.filter-group {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.filter-group .form-select {
  width: auto;
  min-width: 120px;
}

.empty-state {
  text-align: center;
  padding: 3rem 0;
}

.empty-state i {
  font-size: 3rem;
  color: var(--text-secondary);
}

.empty-state p {
  color: var(--text-secondary);
  margin: 1rem 0;
}

.key-items {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.key-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border: 1px solid var(--border-color);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.key-item:hover {
  border-color: #667eea;
  box-shadow: 0 4px 12px var(--shadow-color);
}

.key-item-info {
  flex: 1;
  min-width: 0;
}

.key-item-badges {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
  flex-wrap: wrap;
}

.key-item-preview {
  font-family: 'Courier New', monospace;
  font-size: 0.8rem;
  color: var(--text-secondary);
  margin: 0 0 0.25rem 0;
  word-break: break-all;
}

.key-item-date {
  color: var(--text-secondary);
  font-size: 0.75rem;
}

.key-item-actions {
  display: flex;
  gap: 0.25rem;
  margin-left: 1rem;
  flex-shrink: 0;
}

.action-btn {
  width: 34px;
  height: 34px;
  border: none;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn.copy-btn {
  background: var(--bg-secondary);
  color: var(--text-primary);
}

.action-btn.copy-btn:hover {
  background: #667eea;
  color: white;
}

.action-btn.export-btn {
  background: var(--bg-secondary);
  color: var(--text-primary);
}

.action-btn.export-btn:hover {
  background: #28a745;
  color: white;
}

.action-btn.delete-btn {
  background: #fee2e2;
  color: #dc2626;
}

.action-btn.delete-btn:hover {
  background: #dc2626;
  color: white;
}

.dropdown-menu {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
}

.dropdown-item {
  color: var(--text-primary);
  cursor: pointer;
}

.dropdown-item:hover {
  background: var(--bg-secondary);
}

@media (max-width: 768px) {
  .key-item {
    flex-direction: column;
    align-items: stretch;
  }
  
  .key-item-actions {
    margin-left: 0;
    margin-top: 0.5rem;
    justify-content: flex-end;
  }
  
  .list-filters {
    flex-direction: column;
  }
  
  .filter-group {
    width: 100%;
  }
  
  .filter-group .form-select {
    flex: 1;
  }
}
</style>
