import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/api/axios'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token'))

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN' || user.value?.role === 'SUPER_ADMIN')

  function initAuth() {
    const saved = localStorage.getItem('user')
    if (saved && token.value) {
      user.value = JSON.parse(saved)
      api.defaults.headers.common['Authorization'] = `Bearer ${token.value}`
    }
  }

  async function login(credentials) {
    const { data } = await api.post('/auth/login', credentials)
    token.value = data.data.accessToken
    user.value = data.data.user
    localStorage.setItem('token', token.value)
    localStorage.setItem('user', JSON.stringify(user.value))
    api.defaults.headers.common['Authorization'] = `Bearer ${token.value}`
    return data.data
  }

  async function register(payload) {
    const { data } = await api.post('/auth/register', payload)
    return data.data
  }

  async function logout() {
    try { await api.post('/auth/logout') } catch {}
    token.value = null
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    delete api.defaults.headers.common['Authorization']
  }

  async function fetchProfile() {
    const { data } = await api.get('/auth/me')
    user.value = data.data
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  return { user, token, isAuthenticated, isAdmin, initAuth, login, register, logout, fetchProfile }
})
