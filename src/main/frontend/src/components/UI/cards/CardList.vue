<script>
import {defineComponent} from 'vue'
import AppCard from "@/components/UI/cards/Card.vue";

export default defineComponent({
  name: "app-card-list",
  components: {AppCard},
  props: {
    cards: {
      type: Array,
      required: true
    },
    btnsVisible: {
      type: Boolean,
      required: false,
      default() {
        return true
      }
    },
    closeBtnVisible: {
      type: Boolean,
      required: true,
    }
  },
  methods: {
    onCardBtnClicked(objectId) {
      this.$emit('cardBtnClicked', objectId)
    },
    onCardBtnCloseClicked(objectId) {
      this.$emit('cardBtnCloseClicked', objectId)
    },
    onCardBtnQuestionClicked(objectId) {
      this.$emit('cardBtnQuestionClicked', objectId)
    },
    onCardBtnEditClicked(objectId) {
      this.$emit('cardBtnEditClicked', objectId)
    }
  },
  emits: ['cardBtnClicked', 'cardBtnCloseClicked', 'cardBtnQuestionClicked', 'cardBtnEditClicked']
})
</script>

<template>
  <div class="cards">
    <div class="card" v-for="card in cards" :key="card.id">
      <v-hover v-slot="{ isHovering, props }">
        <app-card
            v-bind="props"
            :elevation="isHovering ? 24 : 6"
            :objectId="card.id"
            :title="card.title"
            :btnTitle="card.btnTitle"
            :subtitle="card.subtitle"
            :text="card.description"
            :btnVisible="btnsVisible"
            :closeBtnVisible="closeBtnVisible"
            @btnClick="onCardBtnClicked"
            @closeClick="onCardBtnCloseClicked"
            @questionClick="onCardBtnQuestionClicked"
            @editClick="onCardBtnEditClicked"
        />
      </v-hover>
    </div>
  </div>
</template>

<style scoped>
.cards {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  align-items: flex-start;
  gap: 10%;
}

.card {
  width: 30%;
  margin-bottom: 15px;
}
</style>