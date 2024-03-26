<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/UI/Navbar.vue";

// Изменяемая таблица input
// Очистить отправить

export default defineComponent({
  name: 'app-create-task',
  components: {AppNavbar},
  data() {
    return {
      types: [
        {id: 1, name: 'Базовый SELECT'},
        {id: 2, name: 'Базовый UPDATE'},
        {id: 3, name: 'Базовый DELETE'},
        {id: 4, name: 'Базовый INSERT'},
      ],
      weight: null,
      orderImportant: false,
      description: '',
      solution: '',
      changingTable: '',
    }
  },
  methods: {
    createTask() {
      // todo call backend
    }
  }
})
</script>

<template>
  <app-navbar/>
  <div class="content">
    <v-sheet width="800">
      <v-form fast-fail @submit.prevent="createTask">
        <v-label>Тип задания</v-label>
        <v-select
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
    </v-sheet>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  align-items: center;
  justify-content: center;
}

.options {
  gap: 10px;
}

.btn-danger {
  background-color: #ff0000;
  color: white;
}
</style>