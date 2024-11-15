<template>
  <pre>// API조회결과<br/><br/>{{ code }}</pre>
</template>

<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  props: {
    code: {
      required: true,
      type: String
    }
  }
})
</script>

<style scoped>
pre {
  background-color: #333;
  padding: 1rem;
  border-radius: 5px;
  white-space: pre-wrap;
  font-family: monospace;
  color: #f5f5f5;
  overflow: auto;
  font-size: 0.8rem;
}
</style>
