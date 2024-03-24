<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "add-user-dialog",
  props: {
    active: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      email: null
    }
  },
  methods: {
    saveUser() {
      this.$emit('saveUser', this.email)
    },
    closeDialog() {
      this.$emit('closeDialog')
    }
  }
})
</script>

<template>
  <v-dialog
      v-model="this.active"
      max-width="600"
      @click:outside="closeDialog"
  >
    <v-card
        prepend-icon="mdi-account"
        title="User Profile"
    >
      <v-card-text>
        <v-text-field
            v-model="email"
            class="textField"
            placeholder="VlasovVA@mpei.ru"
            label="Email*"
            required
            autofocus
        ></v-text-field>
      </v-card-text>

      <v-divider/>

      <v-card-actions>
        <v-spacer/>

        <v-btn
            text="Закрыть"
            variant="plain"
            @click="closeDialog"
        ></v-btn>

        <v-btn
            color="primary"
            text="Сохранить"
            variant="tonal"
            @click="saveUser"
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>