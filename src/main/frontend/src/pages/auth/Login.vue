<script>
import {defineComponent} from 'vue'
import authService from "@/services/auth.service.js";

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
      const user = {
        email: this.email,
        password: this.password,
      }

      this.$store.dispatch('auth/login', user).then(
          () => {
            this.$router.push('/')
          }, (error) => {
            const message = (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
            alert(error)
          }
      )
    }
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn
    }
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/')
    }
  }
})
</script>

<template>
  <div class="d-flex align-center justify-center" style="height: 100vh">
    <v-sheet width="400" class="mx-auto">
      <img src="../../assets/Your-logo-here.png" alt="">
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