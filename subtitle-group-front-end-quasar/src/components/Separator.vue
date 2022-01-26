<template>
  <q-separator class="q-my-sm" style="height: 2px;margin-top: 15px" />
  <div style="text-align: center; margin-top: -22px; color: #757575;">
    {{text}}
  </div>
</template>

<script>
export default {
  name: "separator",
  props: {
    text: String
  }
}
</script>

<style scoped>

</style>