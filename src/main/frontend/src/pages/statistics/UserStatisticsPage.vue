<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import StatisticsService from "@/services/statistics.service.js";
import UserService from "@/services/user.service.js";

export default defineComponent({
  name: "app-user-statistics-page",
  data() {
    return {
      user: null,
      statistics: null,
    }
  },
  components: {AppNavbar},
  mounted() {
    const userId = this.$route.params.userId
    UserService.getUserById(userId)
        .then(res => this.user = res)
    StatisticsService.getUserStatistics(userId)
        .then(res => this.statistics = res)
  },
  computed: {
    fio() {
      if (!this.user) return ''
      const middleName = this.user.middleName === '' ? '' : this.user.middleName.charAt(0) + '.'
      return `${this.user.lastName} ${this.user.firstName.charAt(0)}. ${middleName}`
    }
  }
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <div class="user-info" v-if="user">
      <h4 class="text-h4">{{ fio }}</h4>
      <p class="text-body-1">{{ this.user.email }}</p>
    </div>
    <div class="user-statistics mt-5" v-if="this.statistics">
      <p class="text-body-2">Процент задач от общего числа, которые пользователь пытался решить - {{ this.statistics.tasksTriedPercent }}%</p>
      <p class="text-body-2">Процент решённых задач от задач, которые пользователь пытался решить - {{ this.statistics.tasksSolvedPercent }}%</p>
      <p class="text-body-2">Процент решенных задач от общего числа задач - {{ this.statistics.tasksTotalSolvedPercent }}%</p>
      <p class="text-body-2">Среднее количество попыток для решения задачи - {{ this.statistics.averageTry }}</p>
    </div>
  </div>
</template>

<style scoped>
.content {
  margin: 20px;
}
</style>