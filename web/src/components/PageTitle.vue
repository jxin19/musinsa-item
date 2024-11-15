<template>
  <h1>{{ title }}</h1>
</template>

<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  props: {
    title: {
      required: true,
      type: String
    }
  }
})
</script>

<style scoped>
h1 {
  text-decoration: none;
  font-weight: bold;
  font-size: 1.75rem;
  line-height: 34px;
  padding: 52px 20px 24px;
}
</style>
