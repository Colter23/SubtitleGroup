<template>
  <q-select
      :label="label"
      filled dense square
      v-if="$store.state.isAdmin"
      v-model="data.producer"
      :model-value="data.producer"
      @input-value="data.state===0?data.state=1:''"
      @add="data.state===0?data.state=1:''"
      :readonly="data.lock"
      use-input
      :hide-selected="!multiple"
      fill-input
      input-debounce="0"
      option-value="qq"
      option-label="name"
      :multiple="multiple"
      :use-chips="multiple"
      :stack-label="multiple"
      emit-value
      map-options
      option-disable="inactive"
      :options="options"
      @filter="filterFn"
      input-style="cursor: text"
      style="width: 220px; padding-bottom: 10px;">
    <!-- 下拉框选项 -->
    <template v-slot:option="scope">
      <q-item v-bind="scope.itemProps">
        <q-item-section>
          <q-item-label>{{ scope.opt.name }}</q-item-label>
          <q-item-label caption>{{ scope.opt.qq }}</q-item-label>
        </q-item-section>
      </q-item>
    </template>
    <!-- 清除按钮 -->
    <template v-if="multiple?data.producer.length>0:data.producer!==0" v-slot:append>
      <q-icon name="cancel" @click.stop="data.state=0;multiple?data.producer=[]:data.producer=0" class="cursor-pointer" />
    </template>
    <!-- 无数据 -->
    <template v-slot:no-option>
      <q-item>
        <q-item-section class="text-grey">
          无数据
        </q-item-section>
      </q-item>
    </template>
  </q-select>
</template>

<script>
export default {
  name: "UserSelect",
  props: {
    data: null,
    multiple: false,
    qqData: Array,
    label: {
      type: String,
      default: () => {
        return '抓人'
      }
    }
  },
  data: () => ({
    options: null,
  }),
  created() {
    this.options = this.qqData
  },
  methods: {
    filterFn (val, update, abort) {
      update(() => {
        const needle = val.toLowerCase();
        this.options = this.qqData.filter(v => v.name.toLowerCase().indexOf(needle) > -1);
      })
    }
  }
}
</script>

<style scoped>

</style>