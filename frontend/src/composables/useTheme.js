import { ref, watch, onMounted } from 'vue'
import { usePreferredDark } from '@vueuse/core'

const THEME_KEY = 'app-theme'

export function useTheme() {
  const preferredDark = usePreferredDark()
  const currentTheme = ref(localStorage.getItem(THEME_KEY) || 'light')
  const isDark = ref(false)

  const themes = [
    { id: 'light', label: 'Light', icon: 'bi bi-sun' },
    { id: 'dark', label: 'Dark', icon: 'bi bi-moon' },
    { id: 'system', label: 'System', icon: 'bi bi-display' }
  ]

  function applyTheme(theme) {
    const html = document.documentElement
    html.classList.remove('theme-light', 'theme-dark')
    
    let actualTheme = theme
    if (theme === 'system') {
      actualTheme = preferredDark.value ? 'dark' : 'light'
    }
    
    html.classList.add(`theme-${actualTheme}`)
    isDark.value = actualTheme === 'dark'
    localStorage.setItem(THEME_KEY, theme)
    currentTheme.value = theme
  }

  function setTheme(theme) {
    applyTheme(theme)
  }

  watch(preferredDark, () => {
    if (currentTheme.value === 'system') {
      applyTheme('system')
    }
  })

  onMounted(() => {
    applyTheme(currentTheme.value)
  })

  return {
    currentTheme,
    isDark,
    themes,
    setTheme,
    applyTheme
  }
}
