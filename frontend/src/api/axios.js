import axios from 'axios'
import { toast } from 'vue3-toastify'

const API_BASE_URL = import.meta.env.VITE_API_URL || '/api'

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

axiosInstance.interceptors.request.use(
  (config) => {
    if (import.meta.env.DEV) {
      console.log(`🚀 ${config.method.toUpperCase()} ${config.url}`, config.data)
    }
    return config
  },
  (error) => Promise.reject(error)
)

axiosInstance.interceptors.response.use(
  (response) => {
    if (import.meta.env.DEV) {
      console.log(`✅ ${response.config.method.toUpperCase()} ${response.config.url}`, response.data)
    }
    return response
  },
  (error) => {
    let errorMessage = 'An unexpected error occurred'
    let status = null
    
    if (error.response) {
      status = error.response.status
      const data = error.response.data
      
      switch (status) {
        case 400:
          errorMessage = data.message || 'Bad request'
          break
        case 401:
          errorMessage = 'Unauthorized. Please login.'
          break
        case 403:
          errorMessage = 'Forbidden. You do not have permission.'
          break
        case 404:
          errorMessage = 'Resource not found'
          break
        case 500:
          errorMessage = 'Server error. Please try again later.'
          break
        default:
          errorMessage = data.message || `Error ${status}`
      }
    } else if (error.request) {
      errorMessage = 'Cannot connect to server. Please check your connection.'
    }
    
    const customError = new Error(errorMessage)
    customError.originalError = error
    customError.status = status
    
    return Promise.reject(customError)
  }
)

export default axiosInstance
