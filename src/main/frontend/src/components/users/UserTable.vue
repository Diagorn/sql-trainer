<script>
import {defineComponent} from 'vue'
import AppUserTableRow from "@/components/users/UserTableRow.vue";

export default defineComponent({
  name: "app-user-table",
  components: {AppUserTableRow},
  props: {
    users: {
      type: Array,
      required: true
    }
  }
})
</script>

<template>
  <v-table>
    <thead>
    <tr>
      <th class="text-center font-weight-bold header-cell"/>
      <th class="text-center font-weight-bold header-cell">
        ФИО
      </th>
      <th class="text-center font-weight-bold header-cell">
        Почта
      </th>
      <th class="text-center font-weight-bold header-cell">
        Действия
      </th>
    </tr>
    </thead>
    <tbody>
      <app-user-table-row
          v-for="user in users"
          :key="user.id"
          :user="user"
      />
    </tbody>
  </v-table>
</template>

<style scoped>
.header-cell {
  font-size: large;
}
</style>