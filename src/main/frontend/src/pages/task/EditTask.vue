<script>
import {defineComponent, watch} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import AppTaskCreateForm from "@/components/task/TaskCreateForm.vue";
import {mapActions} from "vuex";
import TaskService from "@/services/task.service.js";

export default defineComponent({
  name: 'app-edit-task',
  components: {AppTaskCreateForm, AppNavbar},
  data() {
    return {
      task: null,
    }
  },
  methods: {
    ...mapActions({
      doCreateTask: "task/editTask"
    }),
    onCreateTask(task) {
      const taskForEdit = {...task}
      taskForEdit.taskTypeIds = task.taskTypeIds.map(taskType => taskType.id)
      taskForEdit.id = this.$route.params.taskId
      this.doCreateTask(taskForEdit)
          .then(this.$router.push('/'))
    }
  },
  mounted() {
    TaskService.getByIdForEdit(this.$route.params.taskId)
        .then(res => {
          this.task = res
        })
  },
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <v-sheet width="800">
      <app-task-create-form v-if="task !== null" @createTask="onCreateTask" :task="this.task"/>
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