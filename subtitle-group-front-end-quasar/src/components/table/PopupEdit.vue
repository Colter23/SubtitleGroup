<template>
  <q-popup-edit
      auto-save
      v-slot="scope"
      v-model="modelValue">
    <!-- 锁定开关 -->
    <q-toggle
        label="锁定"
        v-if="$store.state.isAdmin"
        v-model="lock"
        :model-value="lock"/>
    <!-- 关闭按钮 -->
    <q-btn round color="primary"
           style="float: right; top: 8px; margin-left: 13px; z-index: 10"
           @click="scope.set()"
           size="xs" icon="close" />
    <!-- 插槽 -->
    <slot></slot>
  </q-popup-edit>
</template>

<script>
export default {
  name: "PopupEdit",
  props: {
    modelValue: null,
    lock: Boolean
  }
}
</script>

<style scoped>

</style>