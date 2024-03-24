<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-card",
  props: {
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      required: true
    },
    text: {
      type: String,
      required: true
    },
    btnTitle: {
      type: String,
      required: true
    }
  }
})
</script>

<template>
  <v-card :title="title" :subtitle="subtitle" :text="text">
    <v-card-actions>
      <v-spacer/>
      <v-btn variant="tonal">{{btnTitle}}</v-btn>
    </v-card-actions>
  </v-card>
</template>

<style scoped>

</style>