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
    }
  },
  methods: {
    onCardBtnClicked(objectId) {
      this.$emit('cardBtnClicked', objectId)
    }
  }
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
            :btn-title="card.btnTitle"
            :subtitle="card.subtitle"
            :text="card.text"
            :btnVisible="btnsVisible"
            @btnClick="onCardBtnClicked"
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
  justify-content: space-between;
  align-items: center;
}

.card {
  width: 30%;
  margin-bottom: 15px;
}
</style>