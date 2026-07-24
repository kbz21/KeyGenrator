<template>
  <div class="row justify-content-center">
    <div class="col-lg-10">
      <h1 class="text-white text-center mb-4">
        <i class="bi bi-list-ul me-2"></i>
        My Keys
      </h1>
      <KeyList />
    </div>
  </div>
</template>

<script setup>
import KeyList from '@/components/keys/KeyList.vue'
</script>
