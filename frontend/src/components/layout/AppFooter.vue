<template>
  <footer class="bg-gray-900 text-gray-400">
    <!-- Main footer -->
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-10">
        <!-- Brand -->
        <div class="lg:col-span-2">
          <div class="flex items-center gap-2.5 mb-5">
            <div class="w-9 h-9 bg-gradient-to-br from-sky-500 to-teal-500 rounded-xl flex items-center justify-center">
              <span class="text-white font-bold text-lg">A</span>
            </div>
            <span class="font-display font-bold text-xl text-white">Afia<span class="text-sky-400">Health</span></span>
          </div>
          <p class="text-sm leading-relaxed mb-6 max-w-sm">
            Africa's most trusted healthcare discovery platform. Connecting patients with verified hospitals, doctors, and healthcare services across Nigeria and beyond.
          </p>
          <div class="flex items-center gap-3">
            <a href="tel:+2348001234567" class="flex items-center gap-2 bg-red-600/20 border border-red-500/30 text-red-400 text-sm px-4 py-2.5 rounded-xl hover:bg-red-600/30 transition-colors">
              <span>🚨</span> Emergency: 0800-AFIA-HELP
            </a>
          </div>
        </div>

        <!-- Links -->
        <div v-for="col in footerLinks" :key="col.title">
          <h4 class="font-semibold text-white text-sm mb-4 uppercase tracking-wider">{{ col.title }}</h4>
          <ul class="space-y-2.5">
            <li v-for="link in col.links" :key="link.label">
              <router-link :to="link.to" class="text-sm hover:text-white transition-colors">{{ link.label }}</router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Bottom bar -->
    <div class="border-t border-gray-800 py-6">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8 flex flex-col md:flex-row items-center justify-between gap-4">
        <p class="text-xs text-gray-500">© {{ new Date().getFullYear() }} AfiaHealth Africa Ltd. All rights reserved.</p>
        <div class="flex items-center gap-6">
          <span class="text-xs text-gray-500">🇳🇬 Made for Africa</span>
          <router-link to="/privacy" class="text-xs hover:text-white transition-colors">Privacy Policy</router-link>
          <router-link to="/terms" class="text-xs hover:text-white transition-colors">Terms of Use</router-link>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
const footerLinks = [
  {
    title: 'Services',
    links: [
      { label: 'Find Hospitals', to: '/hospitals' },
      { label: 'Find Doctors', to: '/doctors' },
      { label: 'Book Appointment', to: '/appointments' },
      { label: 'Emergency Services', to: '/emergency' },
      { label: 'Find Pharmacy', to: '/pharmacy' },
      { label: 'Telemedicine', to: '/doctors?telemedicine=true' },
    ]
  },
  {
    title: 'Company',
    links: [
      { label: 'About Us', to: '/about' },
      { label: 'Health Blog', to: '/blog' },
      { label: 'Contact Us', to: '/contact' },
      { label: 'List Your Hospital', to: '/register?type=hospital' },
      { label: 'Join as a Doctor', to: '/register?type=doctor' },
      { label: 'Careers', to: '/careers' },
    ]
  }
]
</script>
