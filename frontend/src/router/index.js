import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/home/HomePage.vue') },
  { path: '/hospitals', name: 'Hospitals', component: () => import('@/views/hospitals/HospitalListPage.vue') },
  { path: '/hospitals/:uuid', name: 'HospitalDetail', component: () => import('@/views/hospitals/HospitalDetailPage.vue') },
  { path: '/doctors', name: 'Doctors', component: () => import('@/views/doctors/DoctorListPage.vue') },
  { path: '/appointments', name: 'Appointments', component: () => import('@/views/appointments/AppointmentPage.vue') },
  { path: '/emergency', name: 'Emergency', component: () => import('@/views/emergency/EmergencyPage.vue') },
  { path: '/pharmacy', name: 'Pharmacy', component: () => import('@/views/pharmacy/PharmacyLocatorPage.vue') },
  { path: '/blog', name: 'Blog', component: () => import('@/views/blog/BlogPage.vue') },
  { path: '/about', name: 'About', component: () => import('@/views/about/AboutPage.vue') },
  { path: '/contact', name: 'Contact', component: () => import('@/views/contact/ContactPage.vue') },
  { path: '/login', name: 'Login', component: () => import('@/views/auth/LoginPage.vue') },
  { path: '/register', name: 'Register', component: () => import('@/views/auth/RegisterPage.vue') },
  { path: '/dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/PatientDashboard.vue') },
  { path: '/admin', name: 'Admin', component: () => import('@/views/admin/AdminDashboard.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() { return { top: 0, behavior: 'smooth' } }
})

export default router
