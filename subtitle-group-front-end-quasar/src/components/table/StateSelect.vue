<template>
  <q-select
      label="状态"
      standout dense square
      option-value="id"
      option-label="label"
      v-model="data.state"
      :model-value="data.state"
      :readonly="!$store.state.isAdmin && (multiple?!data.producer.includes($store.state.uid):data.producer!==$store.state.uid)"
      :options="stateOptions"
      option-disable="inactive"
      emit-value map-options
      style="margin-bottom: 10px;">
    <!-- 下拉框选项 -->
    <template v-slot:option="scope">
      <q-item
          v-bind="scope.itemProps" dense
          :style="'background:'+scope.opt.color">
        <q-item-section style="text-align: center;">
          <q-item-label
              style="line-height: 28px !important; font-size: 16px"
              v-html="scope.opt.label"/>
        </q-item-section>
      </q-item>
    </template>
  </q-select>

</template>

<script>
export default {
  name: "StateSelect",
  props: {
    data: Object,
    multiple: false,
    stateOptions: Array
  }
}
</script>

<style scoped>

</style>