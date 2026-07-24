<template>
  <div class="card shadow">
    <div class="card-body">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="card-title mb-0">
          <i class="bi bi-list-ul text-primary me-2"></i>
          My Keys
        </h2>
        <span class="badge bg-secondary">{{ keys.length }} keys</span>
      </div>

      <!-- Filters -->
      <div class="row g-3 mb-4">
        <div class="col-md-5">
          <div class="input-group">
            <span class="input-group-text">
              <i class="bi bi-search"></i>
            </span>
            <input
              v-model="searchQuery"
              type="text"
              class="form-control"
              placeholder="Search keys..."
            />
          </div>
        </div>
        <div class="col-md-3">
          <select
            v-model="filterAlgorithm"
            class="form-select"
          >
            <option value="ALL">All Algorithms</option>
            <option value="AES">AES</option>
            <option value="RSA">RSA</option>
          </select>
        </div>
        <div class="col-md-3">
          <select
            v-model="sortBy"
            class="form-select"
          >
            <option value="newest">Newest First</option>
            <option value="oldest">Oldest First</option>
          </select>
        </div>
        <div class="col-md-1">
          <button @click="fetchKeys" class="btn btn-outline-primary w-100">
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
      <div v-else-if="filteredKeys.length === 0" class="text-center py-5">
        <i class="bi bi-inbox display-1 text-muted"></i>
        <p class="text-muted mt-3">No keys found</p>
        <router-link to="/generate" class="btn btn-primary">
          <i class="bi bi-plus-circle me-2"></i>
          Generate First Key
        </router-link>
      </div>

      <!-- Key Cards -->
      <div v-else class="list-group">
        <div
          v-for="key in filteredKeys"
          :key="key.id"
          class="list-group-item list-group-item-action"
        >
          <div class="row align-items-center">
            <div class="col-md-6">
              <div class="d-flex align-items-center gap-2 mb-1">
                <span class="badge bg-primary">{{ key.algorithm }}</span>
                <span class="badge bg-secondary">{{ key.keySize }} bits</span>
                <span class="badge bg-light text-dark">#{{ key.id }}</span>
              </div>
              <pre class="mb-0 text-break font-monospace small text-muted">{{ key.generatedKey.substring(0, 50) }}...</pre>
              <small class="text-muted">
                <i class="bi bi-clock me-1"></i>
                {{ formatDate(key.createdAt) }}
              </small>
            </div>
            <div class="col-md-6 text-end">
              <button
                @click="copyKey(key.generatedKey)"
                class="btn btn-sm btn-outline-primary me-2"
              >
                <i class="bi bi-clipboard"></i>
              </button>
              <button
                @click="deleteKeyItem(key.id)"
                class="btn btn-sm btn-outline-danger"
              >
                <i class="bi bi-trash"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useKeys } from '@/composables/useKeys'
import { toast } from 'vue3-toastify'

const {
  keys,
  loading,
  fetchKeys,
  deleteKey
} = useKeys()

const searchQuery = ref('')
const filterAlgorithm = ref('ALL')
const sortBy = ref('newest')

onMounted(() => {
  console.log('📋 KeyList mounted')
  fetchKeys()
})

const filteredKeys = computed(() => {
  let result = keys.value

  if (filterAlgorithm.value !== 'ALL') {
    result = result.filter(k => k.algorithm === filterAlgorithm.value)
  }

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(k => 
      k.generatedKey.toLowerCase().includes(query) ||
      k.algorithm.toLowerCase().includes(query)
    )
  }

  if (sortBy.value === 'newest') {
    result = [...result].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } else if (sortBy.value === 'oldest') {
    result = [...result].sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt))
  }

  return result
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
  try {
    await navigator.clipboard.writeText(key)
    toast.success('Key copied! 📋')
  } catch (err) {
    toast.error('Failed to copy')
  }
}

const deleteKeyItem = async (id) => {
  if (confirm('Are you sure you want to delete this key?')) {
    await deleteKey(id)
  }
}
</script>
