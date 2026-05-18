import axios from 'axios'
import { useToast } from 'vue-toastification'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api/v1',
  timeout: 30000,
  headers: { 'Content-Type': 'application/json' }
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  const lang = localStorage.getItem('lang') || 'en'
  config.headers['X-Language'] = lang
  return config
})

api.interceptors.response.use(
  response => response,
  error => {
    const toast = useToast()
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    } else if (error.response?.status === 403) {
      toast.error('You do not have permission to do that.')
    } else if (error.response?.status >= 500) {
      toast.error('A server error occurred. Please try again.')
    }
    return Promise.reject(error)
  }
)

export default api
