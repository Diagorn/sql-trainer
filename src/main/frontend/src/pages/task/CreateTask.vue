<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import AppTaskCreateForm from "@/components/task/TaskCreateForm.vue";
import {mapActions} from "vuex";

export default defineComponent({
  name: 'app-create-task',
  components: {AppTaskCreateForm, AppNavbar},
  methods: {
    ...mapActions({
      doCreateTask: "task/addNewTask"
    }),
    onCreateTask(task) {
      this.doCreateTask(task)
          .then(this.$router.push('/'))
    }
  }
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <v-sheet width="800">
      <app-task-create-form @createTask="onCreateTask"/>
    </v-sheet>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>