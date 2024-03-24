<script>

import AppNavbar from "@/components/UI/Navbar.vue";
import {defineComponent} from "vue";
import AppCardList from "@/components/common/cards/CardList.vue";

export default defineComponent({
  name: "TaskList",
  components: {AppCardList, AppNavbar},
  data() {
    return {
      tasks: [
        {
          id: 1,
          title: "Базовый SELECT",
          subtitle: "Обычный SQL-запрос",
          btnTitle: "Решить",
          text: "Необходимо вывести всех сотрудников, у которых зарплата меньше 15 000 рублей"
        }
      ]
    }
  }
})
</script>

<template>
  <app-navbar/>

  <div class="container">
    <app-card-list :cards="tasks"/>
  </div>
</template>

<style scoped>

.container {
  padding-left: 15px;
  padding-right: 15px;
}
</style>