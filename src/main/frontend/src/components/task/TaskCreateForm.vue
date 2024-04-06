<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-task-create-form",
  data() {
    return {
      types: [
        {id: 1, name: 'Базовый SELECT'},
        {id: 2, name: 'Базовый UPDATE'},
        {id: 3, name: 'Базовый DELETE'},
        {id: 4, name: 'Базовый INSERT'},
      ],
      selectedTypes: [],
      title: '',
      weight: null,
      orderImportant: false,
      description: '',
      solution: '',
      changingTable: '',
    }
  },
  methods: {
    createTask() {
      const task = {
        taskTypeIds: this.selectedTypes,
        title: this.title,
        weight: this.weight,
        orderImportant: this.orderImportant,
        description: this.description,
        solution: this.solution,
        changingTable: this.changingTable
      }
      this.$emit('createTask', task)
    }
  }
})
</script>

<template>
  <v-form fast-fail @submit.prevent="createTask">
    <v-label>Название</v-label>
    <v-text-field v-model="title"></v-text-field>

    <v-label class="mt-4">Тип задания</v-label>
    <v-select
        v-model="selectedTypes"
        label="Выберите тип"
        :items="types"
        item-title="name"
        item-value="id"
        multiple>
    </v-select>

    <v-label>Вес</v-label>
    <v-text-field
        v-model="weight"
        hide-details
        single-line
        type="number"
    >
    </v-text-field>

    <v-label class="mt-4">Порядок сортировки важен</v-label>
    <v-radio-group inline v-model="orderImportant">
      <v-radio label="Нет" :value="false"></v-radio>
      <v-radio label="Да" :value="true" class="ml-2"></v-radio>
    </v-radio-group>

    <v-label>Описание</v-label>
    <v-textarea v-model="description"></v-textarea>

    <v-label>Решение</v-label>
    <v-textarea v-model="solution"></v-textarea>

    <v-label>Изменяемая таблица</v-label>
    <v-text-field v-model="changingTable"></v-text-field>

    <v-divider/>

    <div class="mt-4 mb-4 flex-row d-flex justify-end options">
      <v-btn class="btn-danger">Очистить</v-btn>
      <v-btn type="submit" variant="elevated" color="primary">Сохранить</v-btn>
    </div>
  </v-form>
</template>

<style scoped>


.options {
  gap: 10px;
}

.btn-danger {
  background-color: #ff0000;
  color: white;
}
</style>