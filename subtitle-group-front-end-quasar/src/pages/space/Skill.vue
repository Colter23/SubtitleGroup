<template>
  <div style="height: calc(100% - 90px); margin: 10px 10px 0 10px">
    <q-card style="width: 300px;margin: 50px auto; padding: 20px;">
      <skill-select label="源" :data="model" :state-options="options"/>
      <skill-select label="转播" :data="model" :state-options="options"/>
      <skill-select label="轴" :data="model" :state-options="options"/>
      <skill-select label="特效轴" :data="model" :state-options="options"/>
      <skill-select label="翻译" :data="model" :state-options="options"/>
      <skill-select label="校对" :data="model" :state-options="options"/>
      <skill-select label="剪辑" :data="model" :state-options="options"/>
      <skill-select label="后期" :data="model" :state-options="options"/>
      <skill-select label="美工" :data="model" :state-options="options"/>
      <skill-select label="压制" :data="model" :state-options="options"/>

<!--      <q-btn label="清空选择" />-->
<!--      <q-btn label="保存" />-->
    </q-card>
  </div>
</template>

<script>
import StateSelect from "components/table/StateSelect";
import SkillSelect from "components/SkillSelect";
export default {
  name: "Skill",
  components: {SkillSelect, StateSelect},
  data: ()=>({
    model: -1,
    options: [
      {label: '剪辑', value: -1},

    ]
  })
}
</script>
