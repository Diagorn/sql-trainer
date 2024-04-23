<script>
import {defineComponent} from 'vue'
import AppNavbar from "@/components/common/Navbar.vue";
import AppUserTable from "@/components/users/UserTable.vue";
import AddUserDialog from "@/components/users/AddUserDialog.vue";
import UserService from "@/services/user.service.js";

export default defineComponent({
  name: "app-admin-panel",
  components: {AddUserDialog, AppUserTable, AppNavbar},
  data() {
    return {
      // users: [
      //   {
      //     id: 'qwe',
      //     lastName: 'Гасин',
      //     firstName: 'Михаил',
      //     middleName: 'Александрович',
      //     avatarUrl: 'https://sun9-35.userapi.com/impg/ldcal3PZZYcAsCv7vRLwS4uvPfFdn5aNFLJzkg/p_swJK_NAj8.jpg?size=2560x1707&quality=95&sign=37a87f13323a953a7dd92a1af3620b1a&type=album',
      //     email: 'GasinMA@mpei.ru'
      //   },
      // ],
      users: [],
      dialog: false,
    }
  },
  methods: {
    createUser(email) {
      // todo send email to backend
      this.dialog = false
    },
    onDialogClose() {
      this.dialog = false
    }
  },
  mounted() {
    UserService.getUsers()
        .then(res => this.users = res)
  }
})
</script>

<template>
  <app-navbar/>

  <div class="content-wrapper">
    <div class="user-header">
      <h3>Список пользователей</h3>
      <v-btn @click="this.dialog = true">Пригласить</v-btn>
      <add-user-dialog
          :active="dialog"
          @saveUser="createUser"
          @closeDialog="onDialogClose"
      />
    </div>
    <app-user-table :users="users"/>
  </div>
</template>

<style scoped>
.content-wrapper {
  padding: 15px;
}

.user-header {
  display: flex;
  justify-content: space-between;
}
</style>