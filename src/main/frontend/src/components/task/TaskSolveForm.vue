<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-task-solve-form",
  props: {
    task: {
      type: [Object],
      required: true,
    }
  },
  data() {
    return {
      solution: '',
      taskInfo: null,
    }
  },
  methods: {
    send() {
      this.$emit('sendSolution', this.solution)
    }
  },
  emits: ['sendSolution'],
  computed: {
    categoriesString() {
      console.log(this.taskInfo)
      if (this.taskInfo) {
        return this.taskInfo.taskTypes.map(task => task.taskTypeName).join(', ')
      }

      return ''
    }
  },
  mounted() {
    this.taskInfo = this.task
  }
})
</script>

<template>
  <div v-if="taskInfo" class="pa-2 ma-2">
    <h6 class="text-h6 mb-3"> {{ categoriesString }} </h6>
    <p class="font-weight-regular mb-1">Вес - {{ taskInfo.weight }}</p>
    <p class="text-body-1 mb-5">{{ taskInfo.description }}</p>

    <hr/>

    <v-textarea v-model="solution"></v-textarea>
    <div class="options">
      <v-btn class="option" variant="plain" @click="solution = ''">Очистить</v-btn>
      <v-btn color="primary" variant="elevated" @click="send">Отправить</v-btn>
    </div>
  </div>
</template>

<style scoped>
.options {
  margin-top: 20px;
}

.option {
  margin-right: 15px;
}
</style>