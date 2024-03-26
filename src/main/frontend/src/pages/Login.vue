<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-login",
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    login() {
      // todo send to backend
    }
  }
})
</script>

<template>
  <div class="d-flex align-center justify-center" style="height: 100vh">
    <v-sheet width="400" class="mx-auto">
      <img src="../assets/Your-logo-here.png" alt="">
      <v-form fast-fail @submit.prevent="login">
        <v-text-field v-model="email" label="Электронная почта"></v-text-field>
        <v-text-field v-model="password" label="Пароль"></v-text-field>
        <v-btn type="submit" color="primary" block class="mt-2">Sign in</v-btn>
      </v-form>
      <div class="mt-2">
        <p class="text-body-2">Нет учетной записи? <router-link to="/register">Зарегистрироваться</router-link></p>
      </div>
    </v-sheet>
  </div>
</template>

<style scoped>

</style>