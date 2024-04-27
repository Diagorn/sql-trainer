<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import UserService from "@/services/user.service.js";
import StatisticsService from "@/services/statistics.service.js";
import TaskService from "@/services/task.service.js";

export default defineComponent({
  name: "app-task-statistics-page",
  data() {
    return {
      task: null,
      statistics: null,
    }
  },
  components: {AppNavbar},
  mounted() {
    const taskId = this.$route.params.taskId
    TaskService.getById(taskId)
        .then(res => this.task = res)
    StatisticsService.getTaskStatistics(taskId)
        .then(res => this.statistics = res)
  },
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <div class="user-info" v-if="task">
      <h4 class="text-h4">{{ task.title }}</h4>
      <p class="text-body-1">{{ task.description }}</p>
    </div>
    <div class="user-statistics mt-5" v-if="this.statistics">
      <p class="text-body-2">Процент пользователей, которые пытались её решить, от общего числа пользователей - {{ this.statistics.usersTriedPercent }}%</p>
      <p class="text-body-2">Процент процент пользователей, которые её решили, от числа пользователей, которые пытались - {{ this.statistics.usersSolvedPercent }}%</p>
      <p class="text-body-2">Процент корректных решений от общих решений - {{ this.statistics.correctAnswersPercent }}%</p>
      <p class="text-body-2">Позиция в рейтинге популярности - {{ this.statistics.popularityPosition }}</p>
    </div>
  </div>
</template>

<style scoped>
.content {
  margin: 20px;
}
</style>