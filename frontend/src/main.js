import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createI18n } from 'vue-i18n'
import Toast from 'vue-toastification'
import 'vue-toastification/dist/index.css'
import App from './App.vue'
import router from './router'
import './assets/main.css'
import en from './locales/en.json'
import fr from './locales/fr.json'
import ha from './locales/ha.json'
import yo from './locales/yo.json'

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('lang') || 'en',
  fallbackLocale: 'en',
  messages: { en, fr, ha, yo }
})

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(i18n)
app.use(Toast, { position: 'top-right', timeout: 4000 })
app.mount('#app')
