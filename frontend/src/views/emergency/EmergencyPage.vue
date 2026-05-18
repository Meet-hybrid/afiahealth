<template>
  <div>
    <AppNavbar />
    <div class="min-h-screen bg-gray-50">
      <!-- Hero -->
      <div class="bg-gradient-to-br from-red-700 to-rose-600 py-16">
        <div class="container mx-auto px-4 text-center">
          <div class="text-6xl mb-4">🚨</div>
          <h1 class="font-display text-4xl md:text-5xl font-bold text-white mb-4">Emergency Services</h1>
          <p class="text-red-100 text-lg max-w-xl mx-auto">Find the nearest emergency hospital or request an ambulance. Every second counts.</p>
        </div>
      </div>

      <div class="container mx-auto px-4 py-12 -mt-8">
        <!-- Emergency action cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          <button @click="requestAmbulance"
            class="bg-white rounded-2xl p-8 shadow-card hover:shadow-card-hover transition-all text-center border-2 border-transparent hover:border-red-200 group">
            <div class="text-5xl mb-4">🚑</div>
            <h3 class="font-display font-bold text-xl text-gray-900 mb-2 group-hover:text-red-600">Request Ambulance</h3>
            <p class="text-gray-500 text-sm">Dispatch the nearest ambulance to your location</p>
          </button>
          <button @click="findNearestEmergency"
            class="bg-white rounded-2xl p-8 shadow-card hover:shadow-card-hover transition-all text-center border-2 border-transparent hover:border-sky-200 group">
            <div class="text-5xl mb-4">🏥</div>
            <h3 class="font-display font-bold text-xl text-gray-900 mb-2 group-hover:text-sky-600">Find Emergency Hospital</h3>
            <p class="text-gray-500 text-sm">Locate the nearest 24/7 emergency ward</p>
          </button>
          <a href="tel:199" class="bg-white rounded-2xl p-8 shadow-card hover:shadow-card-hover transition-all text-center border-2 border-transparent hover:border-emerald-200 group">
            <div class="text-5xl mb-4">📞</div>
            <h3 class="font-display font-bold text-xl text-gray-900 mb-2 group-hover:text-emerald-600">Call Emergency Line</h3>
            <p class="text-gray-500 text-sm">Nigeria Emergency: 199 | LASAMBUS: 767</p>
          </a>
        </div>

        <!-- Ambulance Request Form -->
        <div id="ambulance" class="bg-white rounded-3xl shadow-card p-8 mb-12">
          <h2 class="font-display text-2xl font-bold text-gray-900 mb-6">🚑 Request an Ambulance</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">Your Name</label>
              <input v-model="ambulanceForm.name" type="text" placeholder="Full name"
                class="w-full border border-gray-200 rounded-xl px-4 py-3 focus:outline-none focus:border-sky-400 focus:ring-2 focus:ring-sky-100" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">Phone Number *</label>
              <input v-model="ambulanceForm.phone" type="tel" placeholder="+234 xxx xxx xxxx"
                class="w-full border border-gray-200 rounded-xl px-4 py-3 focus:outline-none focus:border-sky-400 focus:ring-2 focus:ring-sky-100" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">Emergency Type</label>
              <select v-model="ambulanceForm.type"
                class="w-full border border-gray-200 rounded-xl px-4 py-3 focus:outline-none focus:border-sky-400">
                <option value="cardiac">Cardiac Emergency</option>
                <option value="accident">Road Accident / Trauma</option>
                <option value="stroke">Stroke</option>
                <option value="respiratory">Respiratory Emergency</option>
                <option value="obstetric">Obstetric Emergency</option>
                <option value="other">Other</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-700 mb-2">Patient Age (approx.)</label>
              <input v-model="ambulanceForm.age" type="number" placeholder="e.g. 45"
                class="w-full border border-gray-200 rounded-xl px-4 py-3 focus:outline-none focus:border-sky-400 focus:ring-2 focus:ring-sky-100" />
            </div>
            <div class="md:col-span-2">
              <label class="block text-sm font-semibold text-gray-700 mb-2">Pickup Address *</label>
              <textarea v-model="ambulanceForm.address" rows="2" placeholder="Enter full address or describe your location"
                class="w-full border border-gray-200 rounded-xl px-4 py-3 focus:outline-none focus:border-sky-400 focus:ring-2 focus:ring-sky-100"></textarea>
            </div>
            <div class="md:col-span-2">
              <button @click="useMyLocation" class="flex items-center gap-2 text-sky-600 text-sm font-semibold hover:text-sky-700 mb-4">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                </svg>
                Use My Current Location
              </button>
              <button @click="submitAmbulanceRequest"
                :disabled="requesting"
                class="w-full bg-red-600 hover:bg-red-700 disabled:bg-gray-300 text-white font-bold py-4 rounded-xl transition-colors text-lg flex items-center justify-center gap-3">
                <span v-if="requesting">⏳ Dispatching...</span>
                <span v-else>🚑 Request Ambulance Now</span>
              </button>
            </div>
          </div>
        </div>

        <!-- Nearest Emergency Hospitals -->
        <div v-if="nearbyHospitals.length">
          <h2 class="font-display text-2xl font-bold text-gray-900 mb-6">🏥 Nearest Emergency Hospitals</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div v-for="h in nearbyHospitals" :key="h.id"
              class="bg-white rounded-2xl p-5 shadow-card hover:shadow-card-hover transition-shadow">
              <div class="flex justify-between items-start mb-3">
                <h3 class="font-bold text-gray-900 text-base">{{ h.name }}</h3>
                <span class="bg-red-100 text-red-600 text-xs font-bold px-2 py-1 rounded-full flex-shrink-0">24/7</span>
              </div>
              <p class="text-gray-500 text-sm mb-4">{{ h.address }}</p>
              <div class="flex gap-3">
                <a :href="`tel:${h.phoneEmergency}`"
                  class="flex-1 bg-red-600 text-white text-sm font-semibold py-2.5 rounded-xl text-center hover:bg-red-700 transition-colors">
                  📞 Call Now
                </a>
                <router-link :to="`/hospitals/${h.uuid}`"
                  class="flex-1 border border-gray-200 text-gray-700 text-sm font-semibold py-2.5 rounded-xl text-center hover:bg-gray-50 transition-colors">
                  View Details
                </router-link>
              </div>
            </div>
          </div>
        </div>

        <!-- Emergency numbers -->
        <div class="mt-12 bg-gray-900 rounded-3xl p-8 text-white">
          <h2 class="font-display text-2xl font-bold mb-6">📞 Nigeria Emergency Contacts</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <div v-for="ec in emergencyContacts" :key="ec.name"
              class="bg-white/5 border border-white/10 rounded-2xl p-4 text-center">
              <div class="text-3xl mb-2">{{ ec.icon }}</div>
              <div class="font-bold text-white mb-1">{{ ec.name }}</div>
              <a :href="`tel:${ec.number}`" class="text-2xl font-display font-bold text-sky-400 hover:text-sky-300">{{ ec.number }}</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <AppFooter />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useToast } from 'vue-toastification'
import AppNavbar from '@/components/layout/AppNavbar.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import api from '@/api/axios'

const toast = useToast()
const requesting = ref(false)
const nearbyHospitals = ref([])
const ambulanceForm = ref({ name: '', phone: '', type: 'accident', age: '', address: '' })

const emergencyContacts = [
  { name: 'Nigeria Emergency', icon: '🚨', number: '199' },
  { name: 'LASAMBUS Lagos', icon: '🚑', number: '767' },
  { name: 'Fire Service', icon: '🔥', number: '01-7944051' },
  { name: 'Police', icon: '👮', number: '199' },
]

function useMyLocation() {
  if (!navigator.geolocation) return toast.error('Geolocation not supported')
  navigator.geolocation.getCurrentPosition(pos => {
    ambulanceForm.value.lat = pos.coords.latitude
    ambulanceForm.value.lng = pos.coords.longitude
    toast.success('Location captured! Please also type your address above.')
    findNearestEmergency()
  })
}

async function findNearestEmergency() {
  navigator.geolocation.getCurrentPosition(async pos => {
    const { data } = await api.get('/hospitals/emergency', {
      params: { lat: pos.coords.latitude, lng: pos.coords.longitude, radiusKm: 20 }
    })
    nearbyHospitals.value = data.data
  })
}

async function submitAmbulanceRequest() {
  if (!ambulanceForm.value.phone || !ambulanceForm.value.address) {
    return toast.error('Please fill in phone and pickup address')
  }
  requesting.value = true
  try {
    await api.post('/emergency/ambulance', {
      requesterPhone: ambulanceForm.value.phone,
      patientName: ambulanceForm.value.name,
      patientAge: ambulanceForm.value.age,
      emergencyType: ambulanceForm.value.type,
      pickupAddress: ambulanceForm.value.address,
      pickupLatitude: ambulanceForm.value.lat,
      pickupLongitude: ambulanceForm.value.lng,
    })
    toast.success('🚑 Ambulance dispatched! Help is on the way.')
    ambulanceForm.value = { name: '', phone: '', type: 'accident', age: '', address: '' }
  } catch {
    toast.error('Failed to dispatch. Please call 199 immediately.')
  } finally {
    requesting.value = false
  }
}
</script>
