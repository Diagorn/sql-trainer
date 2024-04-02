<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "app-card",
  props: {
    objectId: {
      type: String,
      required: true,
    },
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
    btnVisible: {
      type: Boolean,
      required: true,
    },
    btnTitle: {
      type: String,
      required: true
    },
  },
  methods: {
    onBtnClicked() {
      this.$emit('btnClick', this.objectId)
    }
  }
})
</script>

<template>
  <v-card :title="title" :subtitle="subtitle" :text="text">
    <v-card-actions>
      <v-spacer/>
      <v-btn variant="tonal" @click="onBtnClicked" v-if="btnVisible">{{btnTitle}}</v-btn>
    </v-card-actions>
  </v-card>
</template>

<style scoped>

</style>