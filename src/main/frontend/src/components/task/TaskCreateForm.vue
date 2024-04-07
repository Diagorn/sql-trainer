<script>
import {defineComponent} from 'vue'
import TaskService from "@/services/task.service.js";
import AppRadio from "@/components/common/Radio.vue";

export default defineComponent({
  name: "app-task-create-form",
  components: {AppRadio},
  data() {
    return {
      types: [],
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
    },
    fetchTaskTypes() {
      TaskService.getTaskTypes()
          .then(res => this.types = res)
    }
  },
  mounted() {
    this.fetchTaskTypes()
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
        item-title="taskTypeName"
        item-value="id"
        no-data-text="Пожалуйста, подождите..."
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

    <v-label class="mt-5">Порядок сортировки важен?</v-label>
    <v-radio-group inline v-model="orderImportant" class="mt-2">
      <app-radio name="orderImportant"
                 v-model="orderImportant"
                 label="Нет"
                 @change="orderImportant = false"
                 :value="false">
      </app-radio>
      <app-radio v-model="orderImportant"
                 name="orderImportant"
                 label="Да"
                 :value="true"
                 @change="orderImportant = true"
                 class="ml-2">
      </app-radio>
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