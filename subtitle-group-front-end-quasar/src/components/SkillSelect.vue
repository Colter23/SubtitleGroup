<template>
  <div style="margin-bottom: 10px">
<!--    <span style="font-size: 20px"> 剪辑 </span>-->
    <q-btn
        push
        no-wrap
        no-caps
        @click="data = -1"
        color="secondary" :label="label"
        style="width: 70px; margin-right: 10px; font-weight: bold">
      <q-tooltip anchor="center left" self="center right" :offset="[10, 10]">
        点击清空选择
      </q-tooltip>
    </q-btn>
    <q-btn-toggle
        push
        no-caps
        no-wrap
        v-model="data"
        toggle-color="primary"
        :options="options"
    >
      <template v-slot:lable>
        <div style="font-size: 18px;">{{options[0].labelData}}</div>
      </template>
    </q-btn-toggle>
  </div>

</template>

<script>
export default {
  name: "SkillSelect",
  props: {
    label: null,
    data: null,
  },
  data: ()=>({
    options: [
      // {labelData: '', value: -1, slot: 'lable'},
      {label: '一般', value: 0},
      {label: '会', value: 1},
      {label: '擅长', value: 2}
    ]
  }),
  mounted() {
    // this.options[0].labelData = this.label
  }
}
</script>

<style scoped>

</style>