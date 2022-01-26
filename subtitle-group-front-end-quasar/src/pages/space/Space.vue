<template>
  <div style="height: calc(100% - 60px); margin: 10px 10px 0 10px">
    <q-editor v-model="editor" style="height: 100%;" />
  </div>

</template>

<script>
export default {
  name: "Space",
  data: ()=>({
    editor: ""
  })
}
</script>

<style scoped>

</style>