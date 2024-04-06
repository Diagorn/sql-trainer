<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import AppTaskSolveForm from "@/components/task/TaskSolveForm.vue";
import TaskService from "@/services/task.service.js";
import SolutionService from "@/services/solution.service.js";

export default defineComponent({
  name: "app-task-solution",
  components: {AppTaskSolveForm, AppNavbar},
  props: {
    taskId: {
      type: String,
      required: false,
    }
  },
  data() {
    return {
      task: null,
      results: null,
    }
  },
  methods: {
    send(solution) {
      // todo call backend
    }
  },
  mounted() {
    const taskId = this.$route.params.taskId
    try {
      TaskService.getById(taskId)
          .then(res => {
            this.task = res
          })
    } catch (e) {
    }
    try {
      SolutionService.getLatestSolution(taskId)
          .then(res => this.results = res)
    } catch (e) {
    }
  },
  computed: {
    resultsPercent() {
      if (!this.results.percent) {
        return ''
      }

      return this.results.percent + '%'
    },
    timePercent() {
      if (!this.results.time) {
        return ''
      }

      return `Время - ${this.results.time}%`
    },
    lengthPercent() {
      if (!this.results.length) {
        return ''
      }

      return `Длина запроса - ${this.results.length}%`
    },
    passText() {
      if (this.results.passed == null) {

      }
    }
  }
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <h4 v-if="task" class="text-h4">{{ task.title }}</h4>
    <v-container>
      <v-row>
        <v-col cols="8">
          <app-task-solve-form v-if="task !== null" :task="task" @sendSolution="send" class="left-block"/>
        </v-col>
        <v-col cols="4">
          <div v-if="results" class="right-block">
            <v-sheet class="pa-2 ma-2">
              <h6 class="text-h6 mb-3"> {{ resultsPercent }} </h6>
              <p class="text-body-1"> {{ timePercent }} </p>
              <p class="text-body-1 mb-10"> {{ lengthPercent }} </p>

              <h4 class="text-h4"></h4>
            </v-sheet>
          </div>
          <div v-else>
            <h6 class="text-h6">Результатов решения пока нет</h6>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>
.content {
  margin: 15px;
}

.left-block {
  border-right: 3px solid lightslategrey;
}
</style>