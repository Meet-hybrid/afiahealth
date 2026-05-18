<template>
  <nav class="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-gray-100 shadow-sm">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between h-16">
        <!-- Logo -->
        <router-link to="/" class="flex items-center gap-2.5">
          <div class="w-9 h-9 bg-gradient-to-br from-sky-500 to-teal-500 rounded-xl flex items-center justify-center">
            <span class="text-white font-bold text-lg">A</span>
          </div>
          <span class="font-display font-bold text-xl text-gray-900">Afia<span class="text-sky-600">Health</span></span>
        </router-link>

        <!-- Desktop Nav -->
        <div class="hidden lg:flex items-center gap-1">
          <router-link v-for="link in navLinks" :key="link.to" :to="link.to"
            class="px-4 py-2 rounded-lg text-sm font-medium text-gray-600 hover:text-sky-700 hover:bg-sky-50 transition-colors"
            active-class="text-sky-700 bg-sky-50">
            {{ link.label }}
          </router-link>
        </div>

        <!-- Right actions -->
        <div class="flex items-center gap-3">
          <!-- Language switcher -->
          <select @change="switchLang" :value="locale" class="hidden md:block text-xs border border-gray-200 rounded-lg px-2 py-1.5 text-gray-600 focus:outline-none focus:border-sky-400">
            <option value="en">🇬🇧 EN</option>
            <option value="fr">🇫🇷 FR</option>
            <option value="ha">🇳🇬 HA</option>
            <option value="yo">🇳🇬 YO</option>
          </select>

          <router-link to="/emergency"
            class="hidden md:flex items-center gap-1.5 bg-red-50 border border-red-200 text-red-600 text-sm font-semibold px-3 py-2 rounded-lg hover:bg-red-100 transition-colors">
            <span class="w-2 h-2 bg-red-500 rounded-full animate-pulse"></span>
            Emergency
          </router-link>

          <template v-if="!authStore.isAuthenticated">
            <router-link to="/login" class="text-sm font-semibold text-gray-700 hover:text-sky-700 px-3 py-2">Sign in</router-link>
            <router-link to="/register" class="bg-sky-600 hover:bg-sky-700 text-white text-sm font-semibold px-4 py-2 rounded-lg transition-colors">Get Started</router-link>
          </template>
          <template v-else>
            <div class="relative" ref="profileRef">
              <button @click="showProfile = !showProfile" class="flex items-center gap-2 p-1.5 rounded-xl hover:bg-gray-100 transition-colors">
                <div class="w-8 h-8 rounded-full bg-sky-600 flex items-center justify-center text-white font-bold text-sm">
                  {{ authStore.user?.firstName?.[0] }}{{ authStore.user?.lastName?.[0] }}
                </div>
              </button>
              <div v-if="showProfile" class="absolute right-0 mt-2 w-52 bg-white border border-gray-100 rounded-2xl shadow-xl py-2">
                <div class="px-4 py-2 border-b border-gray-100 mb-1">
                  <div class="font-semibold text-gray-900 text-sm">{{ authStore.user?.firstName }}</div>
                  <div class="text-xs text-gray-400">{{ authStore.user?.email }}</div>
                </div>
                <router-link to="/dashboard" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-50">My Dashboard</router-link>
                <router-link to="/appointments" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-50">My Appointments</router-link>
                <router-link v-if="authStore.isAdmin" to="/admin" class="block px-4 py-2 text-sm text-sky-600 hover:bg-sky-50">Admin Panel</router-link>
                <div class="border-t border-gray-100 mt-1 pt-1">
                  <button @click="logout" class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50">Sign out</button>
                </div>
              </div>
            </div>
          </template>

          <!-- Mobile menu btn -->
          <button @click="mobileOpen = !mobileOpen" class="lg:hidden p-2 rounded-lg hover:bg-gray-100">
            <svg class="w-6 h-6 text-gray-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path v-if="!mobileOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile nav -->
    <transition name="slide-down">
      <div v-if="mobileOpen" class="lg:hidden bg-white border-t border-gray-100 py-4 px-4">
        <div class="flex flex-col gap-1">
          <router-link v-for="link in navLinks" :key="link.to" :to="link.to"
            @click="mobileOpen = false"
            class="px-4 py-3 rounded-xl text-sm font-medium text-gray-700 hover:bg-gray-50">
            {{ link.label }}
          </router-link>
          <router-link to="/emergency" @click="mobileOpen = false"
            class="px-4 py-3 rounded-xl text-sm font-semibold text-red-600 hover:bg-red-50 flex items-center gap-2">
            🚨 Emergency Services
          </router-link>
        </div>
      </div>
    </transition>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { useAuthStore } from '@/store/auth'
import { useRouter } from 'vue-router'

const { locale } = useI18n()
const authStore = useAuthStore()
const router = useRouter()
const mobileOpen = ref(false)
const showProfile = ref(false)

const navLinks = [
  { to: '/hospitals', label: 'Find Hospitals' },
  { to: '/doctors', label: 'Doctors' },
  { to: '/pharmacy', label: 'Pharmacies' },
  { to: '/appointments', label: 'Appointments' },
  { to: '/blog', label: 'Health Tips' },
  { to: '/about', label: 'About' },
]

function switchLang(e) {
  locale.value = e.target.value
  localStorage.setItem('lang', e.target.value)
}

async function logout() {
  await authStore.logout()
  router.push('/')
  showProfile.value = false
}
</script>

<style scoped>
.slide-down-enter-active, .slide-down-leave-active { transition: all 0.2s ease; }
.slide-down-enter-from, .slide-down-leave-to { opacity: 0; transform: translateY(-10px); }
</style>
