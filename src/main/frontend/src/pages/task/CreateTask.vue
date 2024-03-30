<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import AppTaskCreateForm from "@/components/task/TaskCreateForm.vue";

export default defineComponent({
  name: 'app-create-task',
  components: {AppTaskCreateForm, AppNavbar},
  methods: {
    onCreateTask(task) {
      // todo call backend
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