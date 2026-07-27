<template>
  <div class="key-list">
    <div class="list-header">
      <h2 class="list-title">
        <i class="bi bi-list-ul text-primary me-2"></i>
        My Keys
      </h2>
      <span class="badge bg-secondary">{{ keys.length }} keys</span>
    </div>

    <div class="list-filters">
      <div class="search-box">
        <i class="bi bi-search"></i>
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search keys..."
        />
      </div>
      <div class="filter-group">
        <select v-model="filterAlgorithm" class="filter-select">
          <option value="ALL">All</option>
          <option value="AES">AES</option>
          <option value="RSA">RSA</option>
        </select>
        <select v-model="sortBy" class="filter-select">
          <option value="newest">Newest</option>
          <option value="oldest">Oldest</option>
        </select>
        <button @click="fetchKeys" class="refresh-btn">
          <i class="bi bi-arrow-clockwise"></i>
        </button>
      </div>
    </div>

    <div v-if="loading" class="text-center py-4">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else-if="filteredKeys.length === 0" class="empty-state">
      <i class="bi bi-inbox"></i>
      <p>No keys found</p>
      <router-link to="/generate" class="btn btn-primary">
        <i class="bi bi-plus-circle me-2"></i>
        Generate First Key
      </router-link>
    </div>

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
          </div>
          <pre class="key-item-preview">{{ key.generatedKey.substring(0, 50) }}...</pre>
          <small class="key-item-date">
            <i class="bi bi-clock me-1"></i>
            {{ formatDate(key.createdAt) }}
          </small>
        </div>
        <div class="key-item-actions">
          <button
            @click="copyKey(key.generatedKey)"
            class="action-btn copy-btn"
            title="Copy"
          >
            <i class="bi bi-clipboard"></i>
          </button>
          <button
            @click="deleteKeyItem(key.id)"
            class="action-btn delete-btn"
            title="Delete"
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
}

const deleteKeyItem = async (id) => {
  if (confirm('Are you sure you want to delete this key?')) {
    await deleteKey(id)
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
}

.list-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0;
  color: var(--text-primary);
}

.list-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.search-box {
  flex: 1;
  min-width: 200px;
  position: relative;
}

.search-box i {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-secondary);
}

.search-box input {
  width: 100%;
  padding: 0.6rem 1rem 0.6rem 2.5rem;
  border: 2px solid var(--border-color);
  border-radius: 10px;
  background: var(--bg-secondary);
  color: var(--text-primary);
  transition: border-color 0.3s ease;
}

.search-box input:focus {
  outline: none;
  border-color: #667eea;
}

.filter-group {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.filter-select {
  padding: 0.6rem 1.5rem 0.6rem 0.75rem;
  border: 2px solid var(--border-color);
  border-radius: 10px;
  background: var(--bg-secondary);
  color: var(--text-primary);
  cursor: pointer;
}

.refresh-btn {
  padding: 0.6rem 1rem;
  border: 2px solid var(--border-color);
  border-radius: 10px;
  background: var(--bg-secondary);
  color: var(--text-primary);
  cursor: pointer;
  transition: all 0.3s ease;
}

.refresh-btn:hover {
  background: #667eea;
  color: white;
  border-color: #667eea;
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
}

.key-item-date {
  color: var(--text-secondary);
  font-size: 0.75rem;
}

.key-item-actions {
  display: flex;
  gap: 0.5rem;
  margin-left: 1rem;
}

.action-btn {
  width: 36px;
  height: 36px;
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

.action-btn.delete-btn {
  background: #fee2e2;
  color: #dc2626;
}

.action-btn.delete-btn:hover {
  background: #dc2626;
  color: white;
}

@media (max-width: 768px) {
  .key-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .key-item-actions {
    margin-left: 0;
    margin-top: 0.5rem;
    width: 100%;
    justify-content: flex-end;
  }
}
</style>
