<template>
  <div id="app">
    <!-- Mobile Menu Toggle -->
    <button class="menu-toggle" @click="toggleSidebar" aria-label="Toggle menu">
      <i class="bi bi-list"></i>
    </button>

    <!-- Overlay -->
    <div class="overlay" :class="{ show: isSidebarOpen }" @click="toggleSidebar"></div>

    <!-- Sidebar -->
    <AppHeader :class="{ open: isSidebarOpen }" />

    <!-- Main Content -->
    <main class="main-content">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>

    <AppFooter />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AppHeader from '@/components/common/AppHeader.vue'
import AppFooter from '@/components/common/AppFooter.vue'

const isSidebarOpen = ref(false)

function toggleSidebar() {
  isSidebarOpen.value = !isSidebarOpen.value
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-primary);
  color: var(--text-primary);
  transition: background-color 0.3s ease, color 0.3s ease;
}

/* ===== Sidebar Offset ===== */
.main-content {
  flex: 1;
  margin-left: 240px;
  padding: 2rem 2rem 1rem;
  min-height: 100vh;
  transition: margin-left 0.3s ease;
}

/* ===== Transitions ===== */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* ===== Scrollbar ===== */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: var(--bg-secondary);
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}

/* ===== Mobile Responsive ===== */
.menu-toggle {
  display: none;
  position: fixed;
  top: 1rem;
  left: 1rem;
  z-index: 1001;
  background: #1a1a2e;
  color: #fff;
  border: none;
  padding: 0.6rem 0.8rem;
  border-radius: 8px;
  font-size: 1.5rem;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.overlay {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
}

.overlay.show {
  display: block;
}

@media (max-width: 768px) {
  .main-content {
    margin-left: 0;
    padding: 1rem;
    padding-top: 4.5rem;
  }

  .menu-toggle {
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

@media (min-width: 769px) {
  .menu-toggle,
  .overlay {
    display: none !important;
  }
}
</style>
