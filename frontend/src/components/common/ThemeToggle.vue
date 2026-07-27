<template>
  <div class="theme-toggle">
    <div class="dropdown">
      <button 
        class="btn btn-outline-light btn-sm dropdown-toggle w-100" 
        type="button" 
        data-bs-toggle="dropdown" 
        aria-expanded="false"
      >
        <i :class="currentIcon"></i>
        <span class="ms-1">{{ currentLabel }}</span>
      </button>
      <ul class="dropdown-menu dropdown-menu-end">
        <li v-for="theme in themes" :key="theme.id">
          <a 
            class="dropdown-item" 
            :class="{ active: currentTheme === theme.id }"
            href="#" 
            @click.prevent="setTheme(theme.id)"
          >
            <i :class="theme.icon" class="me-2"></i>
            {{ theme.label }}
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useTheme } from '@/composables/useTheme'

const { currentTheme, themes, setTheme } = useTheme()

const currentIcon = computed(() => {
  const theme = themes.find(t => t.id === currentTheme.value)
  return theme ? theme.icon : 'bi bi-sun'
})

const currentLabel = computed(() => {
  const theme = themes.find(t => t.id === currentTheme.value)
  return theme ? theme.label : 'Light'
})
</script>

<style scoped>
.theme-toggle {
  width: 100%;
}

.dropdown {
  width: 100%;
}

.btn-outline-light {
  border-color: rgba(255, 255, 255, 0.2);
  width: 100%;
  text-align: left;
  padding: 0.5rem 1rem;
}

.btn-outline-light:hover {
  background: rgba(255, 255, 255, 0.1);
}

.dropdown-item.active {
  background-color: #667eea;
  color: white;
}

.dropdown-menu {
  background: #2d3436;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.dropdown-item {
  color: #dfe6e9;
}

.dropdown-item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}
</style>
