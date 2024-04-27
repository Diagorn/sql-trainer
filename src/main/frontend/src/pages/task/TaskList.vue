<script>

import AppNavbar from "@/components/common/Navbar.vue";
import {defineComponent} from "vue";
import AppCardList from "@/components/UI/cards/CardList.vue";
import {isAuthAsAdmin, isAuthAsUser} from "@/helper/auth.helper.js";
import {mapActions, mapState} from "vuex";
import TaskService from "@/services/task.service.js";

export default defineComponent({
  name: "TaskList",
  components: {AppCardList, AppNavbar},
  data() {
    return {
      page: 1,
    }
  },
  methods: {
    ...mapActions({
      getTasks: "task/getTasks",
      deleteTask: "task/deleteTask",
    }),
    isAuthAsAdmin,
    isAuthAsUser,
    redirectToSolution(taskId) {
      const path = `/task/${taskId}/solve`
      this.$router.push(path)
    },
    redirectToAddTask() {
      this.$router.push('/task/create')
    },
    doDeleteTask(objectId) {
      this.deleteTask(objectId)
    }
  },
  computed: {
    ...mapState({
      tasks: state => state.task.tasks,
      totalPages: state => state.task.totalPages
    }),
    tasksForCards() {
      return this.tasks.map(task => {
        task.btnTitle = 'Решить'
        task.subtitle = task.taskTypes.map(taskType => taskType.taskTypeName).join(',')
        return task
      })
    }
  },
  emits: ['cardBtnClicked'],
  mounted() {
    this.getTasks()
  }
})
</script>

<template>
  <app-navbar/>

  <div class="container">
    <v-btn v-if="isAuthAsAdmin()"
           variant="tonal"
           class="d-block ml-auto mr-0 mb-3"
           @click="redirectToAddTask">
      Добавить задачу
    </v-btn>
    <app-card-list v-if="tasks.length"
                   :cards="tasksForCards"
                   :btnsVisible="isAuthAsUser()"
                   :closeBtnVisible="isAuthAsAdmin()"
                   @cardBtnClicked="redirectToSolution"
                   @cardBtnCloseClicked="doDeleteTask"
    />
    <h6 v-else class="text-h6">Пока что ни одной задачи нет</h6>
    <div class="position-fixed pagination-container">
      <v-pagination
          v-model="page"
          :length="totalPages"
          class="mx-auto w-33"
      ></v-pagination>
    </div>
  </div>
</template>

<style scoped>

.container {
  padding-left: 15px;
  padding-right: 15px;
}

.pagination-container {
  width: 100%;
  margin: 0 auto;
  bottom: 0;
}
</style>