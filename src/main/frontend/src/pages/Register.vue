<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: 'app-register',
  data() {
    return {
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      middleName: '',
    }
  },
  methods: {
    register() {
      // todo call backend
    }
  }
})
</script>

<template>
  <div class="d-flex align-center justify-center" style="height: 100vh">
    <v-sheet width="400" class="mx-auto">
      <img src="../assets/Your-logo-here.png" alt="">
      <v-form fast-fail @submit.prevent="register">
        <v-text-field v-model="email" label="Электронная почта"></v-text-field>
        <v-text-field v-model="password" label="Пароль"></v-text-field>
        <v-text-field v-model="firstName" label="Имя"></v-text-field>
        <v-text-field v-model="lastName" label="Фамилия"></v-text-field>
        <v-text-field v-model="middleName" label="Отчество (необязательно)"></v-text-field>
        <v-btn type="submit" color="primary" block class="mt-2">Sign in</v-btn>
      </v-form>
      <div class="mt-2">
        <p class="text-body-2">Уже есть учетная запись? <router-link to="/login">Войти</router-link></p>
      </div>
    </v-sheet>
  </div>
</template>

<style scoped>

</style>