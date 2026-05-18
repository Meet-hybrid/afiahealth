<template>
  <router-link :to="`/hospitals/${hospital.uuid}`"
    class="group bg-white rounded-2xl shadow-card hover:shadow-card-hover transition-all duration-300 overflow-hidden flex flex-col">
    <!-- Cover -->
    <div class="relative h-44 bg-gradient-to-br from-sky-100 to-teal-50 overflow-hidden">
      <img v-if="hospital.coverImageUrl" :src="hospital.coverImageUrl" :alt="hospital.name"
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
      <div v-else class="w-full h-full flex items-center justify-center text-6xl opacity-30">🏥</div>

      <!-- Badges -->
      <div class="absolute top-3 left-3 flex gap-2">
        <span v-if="hospital.isVerified"
          class="flex items-center gap-1 bg-emerald-500 text-white text-xs font-semibold px-2.5 py-1 rounded-full">
          ✓ Verified
        </span>
        <span v-if="hospital.isFeatured"
          class="bg-amber-500 text-white text-xs font-semibold px-2.5 py-1 rounded-full">
          ⭐ Featured
        </span>
      </div>

      <!-- Emergency badge -->
      <div v-if="hospital.hasEmergency" class="absolute top-3 right-3">
        <span class="flex items-center gap-1 bg-red-600 text-white text-xs font-bold px-2.5 py-1 rounded-full">
          <span class="w-1.5 h-1.5 bg-white rounded-full animate-pulse"></span> 24/7
        </span>
      </div>
    </div>

    <!-- Body -->
    <div class="p-5 flex-1 flex flex-col">
      <div class="flex items-start justify-between gap-3 mb-2">
        <h3 class="font-display font-bold text-gray-900 text-base leading-tight group-hover:text-sky-700 transition-colors">
          {{ hospital.name }}
        </h3>
      </div>

      <div class="flex items-center gap-1.5 text-gray-500 text-xs mb-3">
        <svg class="w-3.5 h-3.5" fill="currentColor" viewBox="0 0 20 20">
          <path fill-rule="evenodd" d="M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z" clip-rule="evenodd"/>
        </svg>
        {{ hospital.city }}, {{ hospital.state }}
        <span v-if="hospital.distance" class="ml-1 text-sky-600 font-medium">· {{ hospital.distance }}km away</span>
      </div>

      <p v-if="hospital.shortDescription" class="text-gray-500 text-xs leading-relaxed mb-4 line-clamp-2">
        {{ hospital.shortDescription }}
      </p>

      <!-- Tags -->
      <div class="flex flex-wrap gap-1.5 mb-4">
        <span class="bg-gray-100 text-gray-600 text-xs px-2.5 py-1 rounded-full">{{ hospital.category }}</span>
        <span v-if="hospital.hasTelemedicine" class="bg-sky-50 text-sky-600 text-xs px-2.5 py-1 rounded-full">💻 Telemedicine</span>
        <span v-if="hospital.hasAmbulance" class="bg-orange-50 text-orange-600 text-xs px-2.5 py-1 rounded-full">🚑 Ambulance</span>
      </div>

      <!-- Footer -->
      <div class="mt-auto pt-4 border-t border-gray-100 flex items-center justify-between">
        <div class="flex items-center gap-1.5">
          <div class="flex">
            <span v-for="i in 5" :key="i"
              class="text-sm"
              :class="i <= Math.round(hospital.averageRating) ? 'text-amber-400' : 'text-gray-200'">★</span>
          </div>
          <span class="text-sm font-semibold text-gray-800">{{ hospital.averageRating?.toFixed(1) }}</span>
          <span class="text-xs text-gray-400">({{ hospital.reviewCount }})</span>
        </div>
        <span class="text-sky-600 text-sm font-semibold group-hover:underline">View →</span>
      </div>
    </div>
  </router-link>
</template>

<script setup>
defineProps({
  hospital: { type: Object, required: true }
})
</script>
