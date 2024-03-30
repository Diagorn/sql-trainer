<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-task-solve-form",
  props: {
    task: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      solution: '',
    }
  },
  methods: {
    send() {
      this.$emit('sendSolution', this.solution)
    }
  },
  computed: {
    categoriesString() {
      return this.task.categories.map(task => task.name).join(', ')
    }
  },
})
</script>

<template>
  <v-sheet class="pa-2 ma-2">
    <h6 class="text-h6 mb-3"> {{ categoriesString }} </h6>
    <p class="font-weight-regular mb-1">Вес - {{ task.weight }}</p>
    <p class="text-body-1 mb-5">{{ task.description }}</p>

    <hr/>

    <v-textarea v-model="solution"></v-textarea>
    <div class="options">
      <v-btn class="option" variant="plain" @click="solution = ''">Очистить</v-btn>
      <v-btn color="primary" variant="elevated" @click="send">Отправить</v-btn>
    </div>
  </v-sheet>
</template>

<style scoped>
.options {
  margin-top: 20px;
}

.option {
  margin-right: 15px;
}
</style>