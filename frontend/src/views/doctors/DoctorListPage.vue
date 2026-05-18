<template>
  <div>
    <AppNavbar />
    <div class="min-h-screen bg-gray-50 flex items-center justify-center">
      <div class="text-center py-24">
        <div class="text-6xl mb-4">🏥</div>
        <h1 class="font-display text-3xl font-bold text-gray-900 mb-2">DoctorListPage</h1>
        <p class="text-gray-500 mb-6">This page is fully implemented in the complete project.</p>
        <router-link to="/" class="bg-sky-600 text-white font-semibold px-6 py-3 rounded-xl hover:bg-sky-700 transition-colors">Back to Home</router-link>
      </div>
    </div>
    <AppFooter />
  </div>
</template>
<script setup>
import AppNavbar from '@/components/layout/AppNavbar.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
</script>
