<template>
  <aside class="sidebar">
    <div class="sidebar-brand">
      <span class="brand-icon">🔑</span>
      <span class="brand-text">Key Generator</span>
    </div>

    <nav class="sidebar-nav">
      <router-link 
        v-for="link in navLinks" 
        :key="link.path"
        class="nav-link" 
        active-class="active" 
        :to="link.path"
        exact
      >
        <i :class="link.icon"></i>
        <span>{{ link.label }}</span>
      </router-link>
    </nav>

    <div class="sidebar-footer">
      <ThemeToggle />
    </div>
  </aside>
</template>

<script setup>
import ThemeToggle from './ThemeToggle.vue'

const navLinks = [
  { path: '/', label: 'Home', icon: 'bi bi-house' },
  { path: '/generate', label: 'Generate Key', icon: 'bi bi-plus-circle' },
  { path: '/keys', label: 'Keys History', icon: 'bi bi-clock-history' },
  { path: '/algorithms', label: 'Algorithms', icon: 'bi bi-code-square' },
  { path: '/about', label: 'About', icon: 'bi bi-info-circle' }
]
</script>

<style scoped>
/* ===== Sidebar Styles ===== */
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 240px;
  height: 100vh;
  background: #1a1a2e;
  color: #e9ecef;
  display: flex;
  flex-direction: column;
  z-index: 1000;
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s ease;
}

/* ===== Brand ===== */
.sidebar-brand {
  display: flex;
  align-items: center;
  padding: 1.5rem 1.25rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  gap: 0.75rem;
}

.brand-icon {
  font-size: 1.8rem;
}

.brand-text {
  font-weight: 700;
  font-size: 1.2rem;
  background: linear-gradient(135deg, #667eea, #764ba2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* ===== Navigation ===== */
.sidebar-nav {
  flex: 1;
  padding: 1rem 0.75rem;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.7rem 1rem;
  border-radius: 10px;
  color: #b2bec3;
  text-decoration: none;
  font-weight: 500;
  font-size: 0.95rem;
  transition: all 0.3s ease;
}

.nav-link i {
  font-size: 1.2rem;
  width: 1.5rem;
  text-align: center;
}

.nav-link:hover {
  background: rgba(255, 255, 255, 0.08);
  color: #fff;
  transform: translateX(4px);
}

.nav-link.active {
  background: rgba(102, 126, 234, 0.25);
  color: #fff;
  box-shadow: inset 3px 0 0 #667eea;
}

/* ===== Footer ===== */
.sidebar-footer {
  padding: 1rem 1.25rem;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
  display: flex;
  justify-content: center;
}

/* ===== Responsive ===== */
@media (max-width: 768px) {
  .sidebar {
    transform: translateX(-100%);
    width: 280px;
  }

  .sidebar.open {
    transform: translateX(0);
  }

  /* Mobile hamburger button */
  .menu-toggle {
    display: flex;
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
}

@media (min-width: 769px) {
  .menu-toggle,
  .overlay {
    display: none !important;
  }
}
</style>
