<template>
  <div class="container py-4">
    <div class="row">
      <div class="col-12">
        <div class="keys-header">
          <h1>My Keys</h1>
          <router-link to="/generate" class="btn btn-primary">
            <i class="bi bi-plus-circle me-2"></i>
            Generate
          </router-link>
        </div>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-12">
        <KeyList />
      </div>
    </div>
  </div>
</template>

<script setup>
import KeyList from '@/components/keys/KeyList.vue'
</script>

<style scoped>
.keys-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 1rem;
  border-bottom: 2px solid var(--border-color);
}

.keys-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}
</style>
