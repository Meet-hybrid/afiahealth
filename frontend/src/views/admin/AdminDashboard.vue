<template>
  <div class="min-h-screen bg-gray-50 flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-900 text-white flex-shrink-0 flex flex-col min-h-screen sticky top-0">
      <div class="p-6 border-b border-gray-800">
        <div class="flex items-center gap-3">
          <div class="w-9 h-9 bg-sky-600 rounded-xl flex items-center justify-center font-bold text-lg">A</div>
          <div>
            <div class="font-bold text-sm">AfiaHealth</div>
            <div class="text-gray-400 text-xs">Admin Panel</div>
          </div>
        </div>
      </div>
      <nav class="flex-1 py-4 overflow-y-auto">
        <div v-for="group in sidebarGroups" :key="group.title" class="mb-6">
          <div class="px-6 py-1 text-xs font-semibold text-gray-500 uppercase tracking-wider">{{ group.title }}</div>
          <div v-for="item in group.items" :key="item.label"
            @click="activeSection = item.id"
            :class="['flex items-center gap-3 mx-2 px-4 py-2.5 rounded-xl cursor-pointer transition-colors text-sm',
              activeSection === item.id ? 'bg-sky-600 text-white' : 'text-gray-400 hover:bg-gray-800 hover:text-white']">
            <span class="text-base">{{ item.icon }}</span>
            {{ item.label }}
            <span v-if="item.badge" class="ml-auto bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">{{ item.badge }}</span>
          </div>
        </div>
      </nav>
    </aside>

    <!-- Main content -->
    <div class="flex-1 flex flex-col">
      <!-- Top bar -->
      <header class="bg-white border-b border-gray-200 px-8 py-4 flex items-center justify-between sticky top-0 z-10">
        <h1 class="font-display font-bold text-xl text-gray-900">{{ currentSectionTitle }}</h1>
        <div class="flex items-center gap-4">
          <button class="relative p-2 hover:bg-gray-100 rounded-lg">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/>
            </svg>
            <span class="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
          </button>
          <div class="flex items-center gap-2 bg-gray-100 rounded-xl px-3 py-2">
            <div class="w-7 h-7 bg-sky-600 rounded-lg flex items-center justify-center text-white text-xs font-bold">SA</div>
            <span class="text-sm font-medium text-gray-700">Super Admin</span>
          </div>
        </div>
      </header>

      <!-- Dashboard section -->
      <main class="flex-1 p-8">
        <!-- Overview Section -->
        <div v-if="activeSection === 'overview'">
          <!-- KPI Cards -->
          <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-6 mb-8">
            <div v-for="kpi in kpiCards" :key="kpi.label"
              class="bg-white rounded-2xl p-6 shadow-card">
              <div class="flex items-center justify-between mb-4">
                <div class="w-12 h-12 rounded-2xl flex items-center justify-center text-2xl" :class="kpi.bgClass">{{ kpi.icon }}</div>
                <span :class="['text-xs font-bold px-2.5 py-1 rounded-full', kpi.change > 0 ? 'bg-emerald-50 text-emerald-600' : 'bg-red-50 text-red-600']">
                  {{ kpi.change > 0 ? '↑' : '↓' }} {{ Math.abs(kpi.change) }}%
                </span>
              </div>
              <div class="text-3xl font-display font-bold text-gray-900 mb-1">{{ kpi.value }}</div>
              <div class="text-sm text-gray-500">{{ kpi.label }}</div>
            </div>
          </div>

          <!-- Charts row -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
            <div class="bg-white rounded-2xl p-6 shadow-card">
              <h3 class="font-bold text-gray-900 mb-4">Appointments by Month</h3>
              <canvas ref="appointmentChart" height="200"></canvas>
            </div>
            <div class="bg-white rounded-2xl p-6 shadow-card">
              <h3 class="font-bold text-gray-900 mb-4">Hospitals by State</h3>
              <canvas ref="stateChart" height="200"></canvas>
            </div>
          </div>

          <!-- Recent activity -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- Pending reviews -->
            <div class="bg-white rounded-2xl p-6 shadow-card">
              <div class="flex items-center justify-between mb-4">
                <h3 class="font-bold text-gray-900">Pending Reviews</h3>
                <span class="bg-amber-100 text-amber-700 text-xs font-bold px-2.5 py-1 rounded-full">{{ pendingReviews.length }} pending</span>
              </div>
              <div class="space-y-3">
                <div v-for="review in pendingReviews" :key="review.id"
                  class="flex items-start gap-3 p-3 bg-gray-50 rounded-xl">
                  <div class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center text-xs font-bold flex-shrink-0">
                    {{ review.author[0] }}
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="text-xs font-semibold text-gray-700">{{ review.author }} · {{ review.hospital }}</div>
                    <p class="text-xs text-gray-500 truncate">{{ review.text }}</p>
                    <div class="flex gap-2 mt-2">
                      <button class="text-xs bg-emerald-100 text-emerald-700 px-3 py-1 rounded-lg font-semibold hover:bg-emerald-200">Approve</button>
                      <button class="text-xs bg-red-100 text-red-600 px-3 py-1 rounded-lg font-semibold hover:bg-red-200">Reject</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Emergency monitoring -->
            <div class="bg-white rounded-2xl p-6 shadow-card">
              <div class="flex items-center justify-between mb-4">
                <h3 class="font-bold text-gray-900">Live Emergency Requests</h3>
                <div class="flex items-center gap-2 text-xs text-emerald-600 font-semibold">
                  <span class="w-2 h-2 bg-emerald-500 rounded-full animate-pulse"></span> Live
                </div>
              </div>
              <div class="space-y-3">
                <div v-for="em in liveEmergencies" :key="em.id"
                  class="flex items-center gap-3 p-3 bg-red-50 border border-red-100 rounded-xl">
                  <span class="text-xl">🚨</span>
                  <div class="flex-1">
                    <div class="text-xs font-bold text-gray-900">{{ em.type }} · {{ em.location }}</div>
                    <div class="text-xs text-gray-500">{{ em.time }} · {{ em.status }}</div>
                  </div>
                  <span :class="['text-xs px-2.5 py-1 rounded-full font-bold',
                    em.status === 'dispatched' ? 'bg-sky-100 text-sky-700' : 'bg-amber-100 text-amber-700']">
                    {{ em.status }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Hospitals Section -->
        <div v-else-if="activeSection === 'hospitals'">
          <div class="flex items-center justify-between mb-6">
            <div class="flex gap-3">
              <input placeholder="Search hospitals..." class="border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:border-sky-400 w-64" />
              <select class="border border-gray-200 rounded-xl px-3 py-2.5 text-sm focus:outline-none">
                <option>All States</option>
                <option>Lagos</option>
                <option>Abuja</option>
              </select>
            </div>
            <button class="bg-sky-600 text-white font-semibold px-5 py-2.5 rounded-xl text-sm hover:bg-sky-700 transition-colors">
              + Add Hospital
            </button>
          </div>
          <div class="bg-white rounded-2xl shadow-card overflow-hidden">
            <table class="w-full">
              <thead class="bg-gray-50 border-b border-gray-100">
                <tr>
                  <th class="text-left text-xs font-semibold text-gray-500 uppercase tracking-wider px-6 py-4">Hospital</th>
                  <th class="text-left text-xs font-semibold text-gray-500 uppercase tracking-wider px-6 py-4">Location</th>
                  <th class="text-left text-xs font-semibold text-gray-500 uppercase tracking-wider px-6 py-4">Rating</th>
                  <th class="text-left text-xs font-semibold text-gray-500 uppercase tracking-wider px-6 py-4">Status</th>
                  <th class="px-6 py-4"></th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-50">
                <tr v-for="h in adminHospitals" :key="h.id" class="hover:bg-gray-50 transition-colors">
                  <td class="px-6 py-4">
                    <div class="font-semibold text-gray-900 text-sm">{{ h.name }}</div>
                    <div class="text-xs text-gray-400">{{ h.category }}</div>
                  </td>
                  <td class="px-6 py-4 text-sm text-gray-600">{{ h.city }}, {{ h.state }}</td>
                  <td class="px-6 py-4">
                    <span class="text-amber-500 font-bold text-sm">★ {{ h.rating }}</span>
                  </td>
                  <td class="px-6 py-4">
                    <span :class="['text-xs font-bold px-3 py-1 rounded-full',
                      h.verified ? 'bg-emerald-100 text-emerald-700' : 'bg-amber-100 text-amber-700']">
                      {{ h.verified ? 'Verified' : 'Pending' }}
                    </span>
                  </td>
                  <td class="px-6 py-4">
                    <div class="flex gap-2 justify-end">
                      <button class="text-xs text-sky-600 hover:underline font-semibold">Edit</button>
                      <button v-if="!h.verified" class="text-xs text-emerald-600 hover:underline font-semibold">Verify</button>
                      <button class="text-xs text-red-500 hover:underline font-semibold">Remove</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Placeholder for other sections -->
        <div v-else class="flex items-center justify-center h-64 text-gray-400 bg-white rounded-2xl shadow-card">
          <div class="text-center">
            <div class="text-5xl mb-3">🚧</div>
            <p class="font-semibold">{{ currentSectionTitle }} — Coming Soon</p>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const activeSection = ref('overview')

const sidebarGroups = [
  {
    title: 'Overview',
    items: [{ id: 'overview', icon: '📊', label: 'Dashboard' }]
  },
  {
    title: 'Management',
    items: [
      { id: 'hospitals', icon: '🏥', label: 'Hospitals', badge: 5 },
      { id: 'doctors', icon: '👨‍⚕️', label: 'Doctors' },
      { id: 'appointments', icon: '📅', label: 'Appointments' },
      { id: 'reviews', icon: '⭐', label: 'Reviews', badge: 12 },
      { id: 'users', icon: '👤', label: 'Users' },
      { id: 'pharmacies', icon: '💊', label: 'Pharmacies' },
    ]
  },
  {
    title: 'Emergency',
    items: [
      { id: 'emergency', icon: '🚨', label: 'Live Emergencies', badge: 2 },
      { id: 'ambulances', icon: '🚑', label: 'Ambulances' },
    ]
  },
  {
    title: 'Content',
    items: [
      { id: 'articles', icon: '📝', label: 'Articles' },
      { id: 'insurance', icon: '🛡️', label: 'Insurance' },
    ]
  },
  {
    title: 'System',
    items: [
      { id: 'analytics', icon: '📈', label: 'Analytics' },
      { id: 'settings', icon: '⚙️', label: 'Settings' },
    ]
  }
]

const currentSectionTitle = computed(() => {
  for (const group of sidebarGroups) {
    const item = group.items.find(i => i.id === activeSection.value)
    if (item) return item.label
  }
  return 'Dashboard'
})

const kpiCards = [
  { icon: '🏥', label: 'Total Hospitals', value: '2,541', change: 12, bgClass: 'bg-sky-100' },
  { icon: '👨‍⚕️', label: 'Registered Doctors', value: '12,089', change: 8, bgClass: 'bg-teal-100' },
  { icon: '📅', label: 'Appointments Today', value: '847', change: 23, bgClass: 'bg-emerald-100' },
  { icon: '🚨', label: 'Emergency Requests', value: '34', change: -5, bgClass: 'bg-red-100' },
]

const pendingReviews = [
  { id: 1, author: 'Emeka O.', hospital: 'Lagos General Hospital', text: 'Excellent service, the doctors were very professional and...' },
  { id: 2, author: 'Ngozi A.', hospital: 'St. Nicholas Hospital', text: 'Waited 3 hours but overall care was good. The nurses were...' },
  { id: 3, author: 'Ibrahim M.', hospital: 'Aminu Kano Teaching Hospital', text: 'Would recommend this hospital for specialist care...' },
]

const liveEmergencies = [
  { id: 1, type: 'Cardiac Emergency', location: 'Lagos Island', time: '2 min ago', status: 'dispatched' },
  { id: 2, type: 'Road Accident', location: 'Ikeja, Lagos', time: '5 min ago', status: 'pending' },
]

const adminHospitals = [
  { id: 1, name: 'Lagos University Teaching Hospital', category: 'Teaching Hospital', city: 'Lagos', state: 'Lagos', rating: 4.7, verified: true },
  { id: 2, name: 'National Hospital Abuja', category: 'General Hospital', city: 'Abuja', state: 'FCT', rating: 4.5, verified: true },
  { id: 3, name: 'Reddington Hospital', category: 'Specialist Hospital', city: 'Lagos', state: 'Lagos', rating: 4.8, verified: true },
  { id: 4, name: 'Sunrise Clinic Ibadan', category: 'General Hospital', city: 'Ibadan', state: 'Oyo', rating: 3.9, verified: false },
  { id: 5, name: 'Meadows Hospital', category: 'Maternity Clinic', city: 'Port Harcourt', state: 'Rivers', rating: 4.2, verified: false },
]
</script>
