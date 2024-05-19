<script>
import {defineComponent} from 'vue'
import 'material-icons/iconfont/material-icons.css';

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
    closeBtnVisible: {
      type: Boolean,
      required: true,
    }
  },
  methods: {
    onBtnClicked() {
      this.$emit('btnClick', this.objectId)
    },
    onCloseBtnClicked() {
      this.$emit('closeClick', this.objectId)
    },
    onQuestionClicked() {
      this.$emit('questionClick', this.objectId)
    },
    onEditBtnClicked() {
      this.$emit('editClick', this.objectId)
    }
  },
  emits: ['btnClick', 'closeClick', 'questionClick', 'editClick'],
})
</script>

<template>
  <v-card :title="title" :subtitle="subtitle" :text="text">
    <template v-slot:append v-if="closeBtnVisible">
      <v-btn variant="text" @click.prevent="onQuestionClicked">
        <span class="material-icons">
          question_mark
        </span>
      </v-btn>
      <v-btn variant="text" @click.prevent="onCloseBtnClicked">
        <span class="material-icons">
          close
        </span>
      </v-btn>
      <v-btn variant="text" @click.prevent="onEditBtnClicked">
        <span class="material-icons">
          edit
        </span>
      </v-btn>
    </template>
    <v-card-actions>
      <v-spacer/>
      <v-btn variant="tonal" @click="onBtnClicked" v-if="btnVisible">{{ btnTitle }}</v-btn>
    </v-card-actions>
  </v-card>
</template>

<style scoped>

</style>