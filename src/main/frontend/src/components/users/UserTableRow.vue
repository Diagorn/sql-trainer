<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-user-table-row",
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  computed: {
    fio() {
      const middleName = this.user.middleName === '' ? '' : this.user.middleName.charAt(0) + '.'
      return `${this.user.lastName} ${this.user.firstName.charAt(0)}. ${middleName}`
    }
  }
})
</script>

<template>
  <tr class="row">
    <td class="avatar-container text-center">
      <v-img
          class="avatar"
          aspect-ratio="1/1"
          :src="user.avatarUrl"
          cover
      />
    </td>
    <td class="text-center">
      {{fio}}
    </td>
    <td class="text-center">
      {{this.user.email}}
    </td>
    <td class="text-center flex-row justify-center">
      <v-btn-group divided class="controls">
        <v-btn variant="elevated" class="btn-danger">
          Удалить
        </v-btn>
        <v-btn variant="tonal">
          Восстановить пароль
        </v-btn>
      </v-btn-group>
    </td>
  </tr>
</template>

<style scoped>
.row {
  min-height: 50px;
}

.avatar-container {
  margin: 0 auto;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin: 15px auto;
}

.controls {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 15px;
}

.btn-danger {
  background-color: #ff0000;
  color: white;
}
</style>