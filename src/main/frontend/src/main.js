import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import router from "@/router/router.js";
import store from "@/store/store.js";

const vuetify = createVuetify({
    components,
    directives,
})

createApp(App)
    .use(router)
    .use(store)
    .use(vuetify)
    .mount('#app')
