<script>

import AppNavbar from "@/components/common/Navbar.vue";
import {defineComponent} from "vue";
import AppCardList from "@/components/UI/cards/CardList.vue";

export default defineComponent({
  name: "TaskList",
  components: {AppCardList, AppNavbar},
  data() {
    return {
      tasks: [
        {
          id: 'qwe',
          title: "Базовый SELECT",
          subtitle: "Обычный SQL-запрос",
          btnTitle: "Решить",
          text: "Необходимо вывести всех сотрудников, у которых зарплата меньше 15 000 рублей"
        }
      ]
    }
  },
  methods: {
    redirectToSolution(taskId) {
      console.log(taskId)
    }
  }
})
</script>

<template>
  <app-navbar/>

  <div class="container">
    <app-card-list :cards="tasks" @onCardBtnClicked="redirectToSolution"/>
  </div>
</template>

<style scoped>

.container {
  padding-left: 15px;
  padding-right: 15px;
}
</style>