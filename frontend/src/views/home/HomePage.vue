<template>
  <div class="home-page">
    <AppNavbar />

    <!-- HERO SECTION -->
    <section class="hero-section relative overflow-hidden bg-gradient-to-br from-sky-900 via-sky-800 to-teal-700 min-h-[90vh] flex items-center">
      <!-- Background decoration -->
      <div class="absolute inset-0 overflow-hidden">
        <div class="absolute -top-40 -right-40 w-[600px] h-[600px] bg-sky-600/20 rounded-full blur-3xl"></div>
        <div class="absolute -bottom-40 -left-40 w-[500px] h-[500px] bg-teal-500/20 rounded-full blur-3xl"></div>
        <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[800px] bg-blue-800/10 rounded-full blur-3xl"></div>
      </div>

      <!-- Grid overlay -->
      <div class="absolute inset-0" style="background-image: linear-gradient(rgba(255,255,255,0.03) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,0.03) 1px, transparent 1px); background-size: 60px 60px;"></div>

      <div class="relative z-10 container mx-auto px-4 sm:px-6 lg:px-8 py-24">
        <div class="max-w-4xl mx-auto text-center">
          <!-- Badge -->
          <div class="inline-flex items-center gap-2 bg-white/10 backdrop-blur-sm border border-white/20 rounded-full px-4 py-1.5 mb-8">
            <span class="w-2 h-2 bg-emerald-400 rounded-full animate-pulse"></span>
            <span class="text-white/90 text-sm font-medium">Africa's Most Trusted Healthcare Directory</span>
          </div>

          <!-- Headline -->
          <h1 class="font-display text-5xl sm:text-6xl lg:text-7xl font-bold text-white leading-tight mb-6">
            Find the Right Hospital,
            <span class="text-transparent bg-clip-text bg-gradient-to-r from-teal-300 to-sky-300"> Right Now</span>
          </h1>

          <p class="text-xl text-sky-100/80 max-w-2xl mx-auto mb-12 leading-relaxed">
            Discover verified hospitals, book doctor appointments, and access emergency services across Nigeria and Africa — all in one place.
          </p>

          <!-- Search Box -->
          <div class="bg-white rounded-2xl shadow-2xl p-2 max-w-3xl mx-auto">
            <div class="flex flex-col sm:flex-row gap-2">
              <div class="flex-1 flex items-center gap-3 px-4 py-3 bg-gray-50 rounded-xl">
                <svg class="w-5 h-5 text-gray-400 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="Hospital name, specialty..."
                  class="flex-1 bg-transparent text-gray-800 placeholder-gray-400 focus:outline-none text-base"
                  @keyup.enter="doSearch"
                />
              </div>
              <div class="flex items-center gap-3 px-4 py-3 bg-gray-50 rounded-xl sm:border-l border-gray-200 sm:border-0 sm:ml-0">
                <svg class="w-5 h-5 text-gray-400 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"/>
                </svg>
                <select v-model="selectedState" class="bg-transparent text-gray-700 focus:outline-none text-base pr-2">
                  <option value="">All States</option>
                  <option v-for="state in nigerianStates" :key="state" :value="state">{{ state }}</option>
                </select>
              </div>
              <button
                @click="doSearch"
                class="flex-shrink-0 bg-sky-600 hover:bg-sky-700 text-white font-semibold px-8 py-3.5 rounded-xl transition-all duration-200 flex items-center gap-2 justify-center"
              >
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                Search
              </button>
            </div>
          </div>

          <!-- Quick links -->
          <div class="flex flex-wrap justify-center gap-3 mt-8">
            <button
              v-for="quick in quickSearches" :key="quick.label"
              @click="quickSearch(quick)"
              class="flex items-center gap-2 bg-white/10 hover:bg-white/20 backdrop-blur-sm border border-white/20 text-white/90 text-sm px-4 py-2 rounded-full transition-all duration-200"
            >
              <span>{{ quick.icon }}</span>
              {{ quick.label }}
            </button>
          </div>
        </div>
      </div>

      <!-- Bottom wave -->
      <div class="absolute bottom-0 left-0 right-0">
        <svg viewBox="0 0 1440 80" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M0 80L60 74.7C120 69.3 240 58.7 360 53.3C480 48 600 48 720 53.3C840 58.7 960 69.3 1080 72C1200 74.7 1320 69.3 1380 66.7L1440 64V80H0Z" fill="#f9fafb"/>
        </svg>
      </div>
    </section>

    <!-- STATS SECTION -->
    <section class="py-16 bg-gray-50">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-2 lg:grid-cols-4 gap-6">
          <div v-for="stat in stats" :key="stat.label"
            class="bg-white rounded-2xl p-6 text-center shadow-card hover:shadow-card-hover transition-shadow duration-300">
            <div class="text-3xl font-display font-bold text-sky-700 mb-1">{{ stat.value }}</div>
            <div class="text-sm text-gray-500 font-medium">{{ stat.label }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- CATEGORIES -->
    <section class="py-20 bg-white">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="font-display text-4xl font-bold text-gray-900 mb-4">Browse by Specialty</h2>
          <p class="text-gray-500 max-w-xl mx-auto">Find hospitals and doctors by the type of care you need</p>
        </div>
        <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-6 gap-4">
          <router-link
            v-for="cat in categories" :key="cat.slug"
            :to="`/hospitals?specialty=${cat.slug}`"
            class="group flex flex-col items-center gap-3 p-5 rounded-2xl border border-gray-100 hover:border-sky-200 hover:bg-sky-50 transition-all duration-200 cursor-pointer"
          >
            <div class="w-14 h-14 rounded-2xl flex items-center justify-center text-2xl"
              :style="`background: ${cat.bg}`">
              {{ cat.icon }}
            </div>
            <span class="text-sm font-semibold text-gray-700 text-center group-hover:text-sky-700 transition-colors">{{ cat.name }}</span>
          </router-link>
        </div>
      </div>
    </section>

    <!-- EMERGENCY BANNER -->
    <section class="py-16 bg-red-50">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-gradient-to-r from-red-600 to-rose-600 rounded-3xl p-8 md:p-12 flex flex-col md:flex-row items-center justify-between gap-8">
          <div class="flex items-center gap-6">
            <div class="w-16 h-16 bg-white/20 rounded-2xl flex items-center justify-center text-4xl flex-shrink-0">🚨</div>
            <div>
              <h3 class="font-display text-2xl md:text-3xl font-bold text-white mb-2">Need Emergency Care?</h3>
              <p class="text-red-100">Find the nearest emergency hospitals or request an ambulance immediately</p>
            </div>
          </div>
          <div class="flex flex-col sm:flex-row gap-3 flex-shrink-0">
            <router-link to="/emergency"
              class="bg-white text-red-600 font-bold px-8 py-4 rounded-xl hover:bg-red-50 transition-colors text-center whitespace-nowrap">
              🏥 Find Emergency
            </router-link>
            <router-link to="/emergency#ambulance"
              class="bg-white/20 border border-white/40 text-white font-semibold px-8 py-4 rounded-xl hover:bg-white/30 transition-colors text-center whitespace-nowrap">
              🚑 Request Ambulance
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- FEATURED HOSPITALS -->
    <section class="py-20 bg-gray-50">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between mb-12">
          <div>
            <h2 class="font-display text-4xl font-bold text-gray-900 mb-2">Featured Hospitals</h2>
            <p class="text-gray-500">Verified and top-rated healthcare facilities</p>
          </div>
          <router-link to="/hospitals" class="hidden sm:flex items-center gap-2 text-sky-600 font-semibold hover:text-sky-700">
            View all <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/></svg>
          </router-link>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <HospitalCard v-for="hospital in featuredHospitals" :key="hospital.id" :hospital="hospital" />
        </div>
        <div class="text-center mt-8 sm:hidden">
          <router-link to="/hospitals" class="inline-flex items-center gap-2 text-sky-600 font-semibold">
            View all hospitals <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/></svg>
          </router-link>
        </div>
      </div>
    </section>

    <!-- HOW IT WORKS -->
    <section class="py-20 bg-white">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="font-display text-4xl font-bold text-gray-900 mb-4">How AfiaHealth Works</h2>
          <p class="text-gray-500 max-w-xl mx-auto">From search to appointment in just a few steps</p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8 relative">
          <div v-for="(step, i) in howItWorks" :key="step.title" class="flex flex-col items-center text-center relative">
            <div class="w-16 h-16 rounded-2xl flex items-center justify-center text-2xl mb-5"
              :class="step.bgClass">{{ step.icon }}</div>
            <div class="absolute top-8 left-[calc(50%+40px)] right-0 h-px bg-gray-200 hidden md:block"
              v-if="i < howItWorks.length - 1"></div>
            <h3 class="font-display font-bold text-gray-900 text-lg mb-2">{{ step.title }}</h3>
            <p class="text-gray-500 text-sm leading-relaxed">{{ step.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- TESTIMONIALS -->
    <section class="py-20 bg-sky-900">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="font-display text-4xl font-bold text-white mb-4">Trusted by Patients Across Africa</h2>
          <p class="text-sky-200">Real stories from real people</p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div v-for="review in testimonials" :key="review.name"
            class="bg-white/10 backdrop-blur-sm border border-white/10 rounded-2xl p-6">
            <div class="flex gap-1 mb-4">
              <span v-for="n in 5" :key="n" class="text-yellow-400 text-lg">★</span>
            </div>
            <p class="text-white/90 mb-5 leading-relaxed">"{{ review.text }}"</p>
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-sky-600 flex items-center justify-center text-white font-bold">
                {{ review.name[0] }}
              </div>
              <div>
                <div class="text-white font-semibold text-sm">{{ review.name }}</div>
                <div class="text-sky-300 text-xs">{{ review.location }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- HEALTH ARTICLES PREVIEW -->
    <section class="py-20 bg-gray-50">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between mb-12">
          <div>
            <h2 class="font-display text-4xl font-bold text-gray-900 mb-2">Health Tips & Articles</h2>
            <p class="text-gray-500">Stay informed with expert health content</p>
          </div>
          <router-link to="/blog" class="hidden sm:flex items-center gap-2 text-sky-600 font-semibold hover:text-sky-700">
            Read all <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/></svg>
          </router-link>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div v-for="article in articles" :key="article.slug"
            class="bg-white rounded-2xl overflow-hidden shadow-card hover:shadow-card-hover transition-shadow duration-300 group">
            <div class="h-48 bg-gradient-to-br from-sky-100 to-teal-50 flex items-center justify-center text-5xl">
              {{ article.emoji }}
            </div>
            <div class="p-6">
              <span class="text-xs font-semibold text-sky-600 uppercase tracking-wide">{{ article.category }}</span>
              <h3 class="font-display font-bold text-gray-900 text-lg mt-2 mb-3 group-hover:text-sky-700 transition-colors">
                {{ article.title }}
              </h3>
              <p class="text-gray-500 text-sm leading-relaxed line-clamp-2">{{ article.excerpt }}</p>
              <div class="flex items-center gap-3 mt-4 pt-4 border-t border-gray-100">
                <span class="text-xs text-gray-400">{{ article.readTime }} min read</span>
                <span class="text-gray-200">·</span>
                <span class="text-xs text-gray-400">{{ article.date }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA -->
    <section class="py-24 bg-gradient-to-br from-teal-600 to-sky-700">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 class="font-display text-4xl md:text-5xl font-bold text-white mb-6">
          Are you a Hospital or Doctor?
        </h2>
        <p class="text-xl text-teal-100 max-w-2xl mx-auto mb-10">
          Join AfiaHealth and connect with thousands of patients across Africa. Get verified, manage appointments, and grow your practice.
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <router-link to="/register?type=hospital"
            class="bg-white text-sky-700 font-bold px-10 py-4 rounded-xl hover:bg-gray-50 transition-colors text-lg">
            List Your Hospital
          </router-link>
          <router-link to="/register?type=doctor"
            class="bg-white/10 border border-white/30 text-white font-semibold px-10 py-4 rounded-xl hover:bg-white/20 transition-colors text-lg">
            Join as a Doctor
          </router-link>
        </div>
      </div>
    </section>

    <AppFooter />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import AppNavbar from '@/components/layout/AppNavbar.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import HospitalCard from '@/components/hospital/HospitalCard.vue'

const router = useRouter()
const searchQuery = ref('')
const selectedState = ref('')

const nigerianStates = [
  'Abia','Adamawa','Akwa Ibom','Anambra','Bauchi','Bayelsa','Benue','Borno',
  'Cross River','Delta','Ebonyi','Edo','Ekiti','Enugu','FCT Abuja','Gombe',
  'Imo','Jigawa','Kaduna','Kano','Katsina','Kebbi','Kogi','Kwara','Lagos',
  'Nasarawa','Niger','Ogun','Ondo','Osun','Oyo','Plateau','Rivers','Sokoto',
  'Taraba','Yobe','Zamfara'
]

const quickSearches = [
  { label: 'Emergency 24/7', icon: '🚨', params: { hasEmergency: true, isOpen247: true } },
  { label: 'Maternity', icon: '🤱', params: { specialty: 'obs-gyn' } },
  { label: 'Cardiologist', icon: '❤️', params: { specialty: 'cardiology' } },
  { label: 'Pediatrics', icon: '👶', params: { specialty: 'pediatrics' } },
  { label: 'Eye Clinic', icon: '👁️', params: { specialty: 'ophthalmology' } },
  { label: 'Telemedicine', icon: '💻', params: { hasTelemedicine: true } },
]

const stats = [
  { value: '2,500+', label: 'Verified Hospitals' },
  { value: '12,000+', label: 'Registered Doctors' },
  { value: '36 States', label: 'Coverage in Nigeria' },
  { value: '500K+', label: 'Patients Helped' },
]

const categories = [
  { name: 'Cardiology', slug: 'cardiology', icon: '❤️', bg: '#FEE2E2' },
  { name: 'Pediatrics', slug: 'pediatrics', icon: '👶', bg: '#D1FAE5' },
  { name: 'Maternity', slug: 'obs-gyn', icon: '🤱', bg: '#FCE7F3' },
  { name: 'Orthopedics', slug: 'orthopedics', icon: '🦴', bg: '#FEF3C7' },
  { name: 'Neurology', slug: 'neurology', icon: '🧠', bg: '#EDE9FE' },
  { name: 'Eye Clinic', slug: 'ophthalmology', icon: '👁️', bg: '#CFFAFE' },
  { name: 'Dental', slug: 'dentistry', icon: '🦷', bg: '#F0F9FF' },
  { name: 'Oncology', slug: 'oncology', icon: '🎗️', bg: '#FFF1F2' },
  { name: 'Mental Health', slug: 'psychiatry', icon: '🧘', bg: '#F0FDF4' },
  { name: 'Dermatology', slug: 'dermatology', icon: '🌿', bg: '#FAFAF5' },
  { name: 'Urology', slug: 'urology', icon: '🔬', bg: '#EFF6FF' },
  { name: 'ENT', slug: 'ent', icon: '👂', bg: '#FFF7ED' },
]

const featuredHospitals = ref([
  { id: 1, uuid: '1', name: 'Lagos University Teaching Hospital', city: 'Lagos', state: 'Lagos', averageRating: 4.7, reviewCount: 312, hasEmergency: true, hasTelemedicine: true, logoUrl: null, isVerified: true, category: 'Teaching Hospital', shortDescription: 'Premier tertiary hospital serving Lagos State and beyond.' },
  { id: 2, uuid: '2', name: 'National Hospital Abuja', city: 'Abuja', state: 'FCT', averageRating: 4.5, reviewCount: 198, hasEmergency: true, hasTelemedicine: false, logoUrl: null, isVerified: true, category: 'General Hospital', shortDescription: 'Federal government referral hospital in the capital.' },
  { id: 3, uuid: '3', name: 'Reddington Hospital', city: 'Lagos', state: 'Lagos', averageRating: 4.8, reviewCount: 245, hasEmergency: true, hasTelemedicine: true, logoUrl: null, isVerified: true, category: 'Specialist Hospital', shortDescription: 'Premium private hospital with world-class specialists.' },
])

const howItWorks = [
  { icon: '🔍', title: 'Search', desc: 'Search by location, specialty or hospital name', bgClass: 'bg-sky-100', },
  { icon: '📋', title: 'Compare', desc: 'Compare hospitals, read verified reviews and check facilities', bgClass: 'bg-teal-100' },
  { icon: '📅', title: 'Book', desc: 'Book appointments online with your preferred doctor', bgClass: 'bg-emerald-100' },
  { icon: '✅', title: 'Get Care', desc: 'Visit in-person or attend a telemedicine consultation', bgClass: 'bg-green-100' },
]

const testimonials = [
  { name: 'Amaka Okafor', location: 'Lagos, Nigeria', text: "AfiaHealth helped me find a cardiologist near me within minutes. The booking process was seamless and the hospital was exactly as described. Truly a lifesaver!" },
  { name: 'Chidi Nwosu', location: 'Abuja, Nigeria', text: "When my father had a stroke, I used AfiaHealth to find the nearest emergency hospital with a neurology unit. I cannot thank this platform enough." },
  { name: 'Fatima Al-Hassan', location: 'Kano, Nigeria', text: "As a mother of three, being able to find a trusted pediatric hospital online and read real reviews before going is incredible. AfiaHealth changed how my family accesses healthcare." },
]

const articles = [
  { slug: 'malaria-prevention', emoji: '🦟', category: 'Prevention', title: 'How to Protect Your Family from Malaria This Rainy Season', excerpt: 'Malaria remains one of the leading causes of death in sub-Saharan Africa. Here are evidence-based strategies to protect your household.', readTime: 6, date: 'May 2025' },
  { slug: 'hypertension-nigeria', emoji: '❤️', category: 'Wellness', title: 'Understanding Hypertension: Why Africans Are at Higher Risk', excerpt: 'High blood pressure affects 1 in 3 adults in Nigeria. Learn how to manage it with lifestyle changes and when to see a doctor.', readTime: 8, date: 'Apr 2025' },
  { slug: 'telemedicine-guide', emoji: '💻', category: 'Digital Health', title: "Your Complete Guide to Telemedicine in Nigeria", excerpt: "Telemedicine is transforming healthcare access in Africa. We break down everything you need to know to get your first virtual consultation.", readTime: 5, date: 'Apr 2025' },
]

function doSearch() {
  router.push({ name: 'Hospitals', query: { q: searchQuery.value, state: selectedState.value } })
}

function quickSearch(quick) {
  router.push({ name: 'Hospitals', query: quick.params })
}
</script>
