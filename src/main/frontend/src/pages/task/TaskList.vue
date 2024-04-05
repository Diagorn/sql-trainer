<script>

import AppNavbar from "@/components/common/Navbar.vue";
import {defineComponent} from "vue";
import AppCardList from "@/components/UI/cards/CardList.vue";
import {isAuthAsUser} from "@/helper/auth.helper.js";
import {mapActions, mapState} from "vuex";

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
    }),
    isAuthAsUser,
    redirectToSolution(taskId) {
      console.log(taskId) // todo actually redirect
    },
    redirectToAddTask() {
      this.$router.push('/task/create')
    }
  },
  computed: {
    ...mapState({
      tasks: state => state.task.tasks,
      totalPages: state => state.task.totalPages
    })
  },
  mounted() {
    this.getTasks()
  }
})
</script>

<template>
  <app-navbar/>

  <div class="container">
    <v-btn variant="tonal" class="d-block ml-auto mr-0" @click="redirectToAddTask">Добавить задачу</v-btn>
    <app-card-list v-if="tasks.length"
                   :cards="tasks"
                   :btnsVisible="isAuthAsUser()"
                   @onCardBtnClicked="redirectToSolution"
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