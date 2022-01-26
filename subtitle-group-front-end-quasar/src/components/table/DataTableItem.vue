<template>
  <div style="white-space: nowrap; border-bottom: 5px solid rgba(0, 0, 0, 0)">

    <div v-for="col in columns" class="column" :style="'width:'+ col.width+'px'">

      <!-- 备注/状态 -->
      <q-card
          v-if="col.field==='remark'"
          style="background:#87ffc1;"
          :style="{height:columnHeight+'px'}"
          flat bordered square>
        <!-- 文字居中box -->
        <div class="text-col-box cursor-pointer"
             :style="{height:columnHeight+'px'}">
          <div class="text-col">
            {{ tableData[col.field].value }}
          </div>
        </div>
        <!-- 弹出编辑 -->
        <popup-edit
            @show="showEdit(col.field)"
            @hide="hideEdit(col.field)"
            :model-value="tableData[col.field].value"
            :lock="tableData[col.field].lock">
          <q-select
              label="状态"
              standout dense square
              option-value="id"
              option-label="label"
              v-model="tableData.state"
              :model-value="tableData.state"
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
          <!-- 输入框 -->
          <q-input
              :label="col.title"
              v-model="tableData[col.field].value"
              :readonly="tableData[col.field].lock"
              filled autogrow dense autofocus counter />
        </popup-edit>
      </q-card>

      <!-- 切片名、标题 -->
      <q-card
          v-else-if="['cutName','title'].includes(col.field)"
          style="background:#87ffc1;"
          :style="{height:columnHeight+'px'}"
          flat bordered square>
        <!-- 文字居中box -->
        <div class="text-col-box cursor-pointer"
             :style="{height:columnHeight+'px'}">
          <div class="text-col">
            {{ tableData[col.field].value }}
          </div>
        </div>
        <!-- 弹出编辑 -->
        <popup-edit
            @show="showEdit(col.field)"
            @hide="hideEdit(col.field)"
            :model-value="tableData[col.field].value"
            :lock="tableData[col.field].lock">
          <!-- 输入框 -->
          <q-input
              :label="col.title"
              v-model="tableData[col.field].value"
              :readonly="tableData[col.field].lock"
              filled autogrow dense autofocus counter />
        </popup-edit>
      </q-card>


      <!-- 时长 -->
      <q-card
          v-else-if="col.field==='duration'"
          class="text-center text-no-wrap cursor-pointer"
          style="background:#87ffc1;"
          :style="{height:columnHeight+'px',lineHeight:columnHeight+'px'}"
          flat bordered square>
        {{ durationFi(tableData[col.field].value) }}
        <!-- 弹出编辑 -->
        <popup-edit
            @show="showEdit(col.field)"
            @hide="hideEdit(col.field)"
            :model-value="tableData[col.field].value"
            :lock="tableData[col.field].lock">
          <!-- 输入框 -->
          <q-input
              label="时长(分)"
              type="number"
              v-model.number="tableData[col.field].value"
              :readonly="tableData[col.field].lock"
              filled dense autofocus />
        </popup-edit>
      </q-card>


      <!-- 源 -->
      <q-card
          v-else-if="col.field==='origin'"
          :style="{height:columnHeight+'px'}"
          flat bordered square>
        <!-- 文字居中box -->
        <div class="text-col-box cursor-pointer"
             :style="{height:columnHeight+'px'}">
          <div class="text-col">
            {{ originTypeOptions.find(v => v.id === tableData[col.field].type).label }}
          </div>
        </div>
        <!-- 弹出编辑 -->
        <popup-edit
            @show="showEdit(col.field)"
            @hide="hideEdit(col.field)"
            :model-value="tableData[col.field].value"
            :lock="tableData[col.field].lock">
          <!-- 源类型选择 -->
          <q-select
              label="源类型"
              standout="bg-light-blue text-white" dense
              style="margin-bottom: 10px"
              emit-value map-options
              option-value="id"
              option-label="label"
              option-disable="inactive"
              v-model="tableData[col.field].type"
              :options="originTypeOptions"
              :readonly="tableData[col.field].lock">
            <template v-slot:option="scope">
              <q-item v-bind="scope.itemProps" dense>
                <q-item-section style="text-align: center;">
                  <q-item-label
                      style="line-height: 28px !important; font-size: 16px"
                      v-html="scope.opt.label"/>
                </q-item-section>
              </q-item>
            </template>
          </q-select>
          <!-- 源视频链接输入框 -->
          <q-input
              label="源视频链接"
              v-model="tableData[col.field].originLink"
              :readonly="tableData[col.field].lock"
              filled autogrow dense autofocus />
          <!-- 下载链接输入框 -->
          <q-input
              label="下载链接"
              v-model="tableData[col.field].downloadLink"
              :readonly="tableData[col.field].lock"
              filled square autogrow dense autofocus />
          <!-- 源视频标题输入框 -->
          <q-input
              label="源[视频/直播]标题"
              style="margin-bottom: 10px"
              v-model="tableData[col.field].title"
              :readonly="tableData[col.field].lock"
              filled square autogrow dense autofocus />
          <!-- 选人 -->
          <user-select :label="'源人'" :data="tableData[col.field]" :qq-data="qqData"/>
        </popup-edit>
      </q-card>

      <!-- 分段 -->
      <div v-else-if="col.field==='slivers'" >
        <q-card
            v-for="(sliver, index) in tableData.slivers"
            class="text-center text-no-wrap cursor-pointer"
            :style="{height:rowHeight+'px',lineHeight: rowHeight+'px'}"
            flat bordered square>
          {{ sliver.join('~') }}
        </q-card>
          <!-- 弹出编辑 -->
          <q-popup-edit
              auto-save
              v-if="$store.state.isAdmin"
              @show="showEdit(col.field)"
              @hide="hideEdit(col.field)"
              style="padding: 0"
              :model-value="tableData.slivers"
              v-slot="scope"
              v-model="tableData.slivers">
            <!-- 关闭按钮 -->
            <q-btn round color="primary"
                   style="float: right; top: 8px;right: 10px; margin-left: 13px; z-index: 10"
                   @click="scope.set()"
                   size="xs" icon="close" />
            <!-- 插槽 -->

            <!-- 分段tab -->
            <q-card flat bordered square>
              <q-tabs
                  v-model="tab" dense
                  class="text-grey"
                  active-color="primary"
                  indicator-color="primary"
                  align="justify"
                  narrow-indicator>
                <q-tab name="auto" label="自动分配" />
                <q-tab name="manual" label="手动分配" />
              </q-tabs>
              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <!-- 自动分配panel -->
                <q-tab-panel name="auto">
                  <q-input
                      label="开始时间(分)"
                      v-model.number="startTime"
                      type="number"
                      filled dense
                      style="max-width: 200px"/>
                  <q-input
                      label="每段时长(分)"
                      v-model.number="sliverDuration"
                      type="number"
                      filled dense square
                      style="max-width: 200px"/>
                  <q-input
                      label="分段数量(可选)"
                      v-model.number="sliverCount"
                      type="number"
                      filled dense square
                      style="max-width: 200px"/>
                  <q-btn
                      @click="sliver"
                      class="fit bg-primary text-white"
                      style="margin-top: 10px">
                    分配
                  </q-btn>
                </q-tab-panel>
                <!-- 手动分配panel -->
                <q-tab-panel name="manual">
                  <div v-for="(sliver, index) in tableData.slivers" class="row">
                    <q-input
                        label="开始时间"
                        v-model.number="sliver[0]"
                        type="number"
                        filled dense square
                        style="max-width: 100px"/>
                    <div style="line-height: 40px;font-size: 20px">~</div>
                    <q-input
                        label="结束时间"
                        v-model.number="sliver[1]"
                        type="number"
                        filled dense square
                        style="max-width: 90px"/>
                  </div>
                  <!-- 添加删除按钮 -->
                  <q-btn-group style="width: 100%; height: 30px; margin-top: 10px">
                    <q-btn padding="none" style="width: 50%; " color="red" class="text-white" icon="remove_circle" />
                    <q-btn padding="none" style="width: 50%; " @click="addSliver(tableData)" color="primary" class="text-white" icon="add_circle" />
                  </q-btn-group>
                </q-tab-panel>
              </q-tab-panels>
            </q-card>
          </q-popup-edit>

      </div>

      <!-- 时轴、翻译、校对、二校 -->
      <div v-else-if="['timeline','translator','proofreader','secondaryProofreader'].includes(col.field)">
        <q-card
            v-for="(data, index) in tableData[col.field]"
            class="text-center text-no-wrap cursor-pointer"
            :style="{height:data.row*rowHeight +'px',
                    lineHeight:data.row*rowHeight +'px',
                    background:currentColor(data.state)}"
            flat bordered square>
          {{data.producer===0?'':qqData.find(v => v.qq === data.producer).name}}

          <!-- 弹出编辑 -->
          <popup-edit
              @show="showEdit(col.field, index)"
              @hide="hideEdit(col.field, index)"
              :model-value="data.producer"
              :lock="data.lock">
            <!-- 状态选择 -->
            <state-select
                :data="data"
                :state-options="stateOptions"
                :style="{background: currentColor(data.state)}"/>
            <!-- 选人 -->
            <user-select :data="data" :qq-data="qqData"/>
            <!-- 接活按钮 -->
            <take-job-btn :data="data"/>
            <!-- 功能按钮 -->
            <q-btn-group v-if="$store.state.isAdmin" style="margin-bottom: 5px">
              <q-btn
                  label="拆分"
                  :disable="data.lock || data.row <= 1"
                  @click="splitCell(tableData[col.field], index)"
                  icon="swap_vert" color="deep-orange" />
              <q-btn
                  label="向下合并"
                  :disable="data.lock || tableData[col.field].length-1 === index"
                  @click="mergeCell(tableData[col.field], index)"
                  icon="arrow_downward" color="deep-orange" />
            </q-btn-group>
          </popup-edit>
        </q-card>
      </div>

      <!-- 剪辑、后期、封面、美工、压制 -->
      <div v-else-if="['cut','afterEffect','cover','artist','compaction'].includes(col.field)">
        <q-card
            class="text-center text-no-wrap"
            :style="{height:columnHeight +'px',
                    background:currentColor(tableData[col.field].state)}"
            flat bordered square>
          <!-- 文字居中box -->
          <div class="text-col-box cursor-pointer"
               :style="{height:columnHeight +'px'}">
            <div class="text-col">
              {{tableData[col.field].producer.length===0?'':multiName(tableData[col.field].producer)}}
            </div>
          </div>
          <!-- 弹出编辑 -->
          <popup-edit
              @show="showEdit(col.field)"
              @hide="hideEdit(col.field)"
              :model-value="tableData[col.field].producer"
              :lock="tableData[col.field].lock">
            <!-- 状态选择 -->
            <state-select
                :data="tableData[col.field]"
                :multiple="true"
                :state-options="stateOptions"
                :style="{background: currentColor(tableData[col.field].state)}"/>
            <!-- 选人 -->
            <user-select :multiple="true" :data="tableData[col.field]" :qq-data="qqData"/>
            <!-- 接活按钮 -->
            <take-job-btn :data="tableData[col.field]" :multiple="true"/>
          </popup-edit>
        </q-card>
      </div>
    </div>
  </div>

</template>

<script>

import UserSelect from "components/table/UserSelect";
import StateSelect from "components/table/StateSelect";
import PopupEdit from "components/table/PopupEdit";
import TakeJobBtn from "components/table/TakeJobBtn";
export default {
  name: "DataTableItem",
  components: {TakeJobBtn, PopupEdit, StateSelect, UserSelect},
  props: {
    columns: null,
    tableData: null,
    qqData: null
  },
  data: () => ({
    rowHeight: 30,

    rowColor: "",

    tab: 'auto',
    model: null,
    state: null,

    optionsSanitize: false,
    isShowAddSliver: true,

    isAdmin: false,
    currentUser: 3375582524,

    startTime: 0,
    sliverDuration: 10,
    sliverCount: null,

    tempObject: null,
    tempText: null,
    tempBoolean: null,
    tempNumber: null,

    stateOptions: [
      {
        id: 0,
        label: "未接",
        color: "#ffffff"
      },
      {
        id: 1,
        label: "已接",
        color: "#80bdf1"
      },
      {
        id: 3,
        label: "完成",
        color: "#87ffc1"
      }
    ],
    originTypeOptions: [
      {
        id: 0,
        label: "BiliBili直播"
      },
      {
        id: 1,
        label: "BiliBili视频"
      },
      {
        id: 2,
        label: "YouTuBe直播"
      },
      {
        id: 3,
        label: "YouTuBe视频"
      },
      {
        id: 4,
        label: "Twitter视频"
      },
      {
        id: 8,
        label: "自制"
      },
      {
        id: 9,
        label: "其他"
      }
    ],

  }),
  computed: {
    columnHeight(){
      // return (this.tableData.row + (this.isShowAddSliver?1:0)) * this.rowHeight
      return this.tableData.row * this.rowHeight
    },
    currentColor(){
      return state => {
        return this.stateOptions.find(v=>v.id===state).color
      }
    },
    multiName(){
      return producers => {
        return producers.map(p => this.qqData.find(v => v.qq === p).name).join('/')
      }
    },
    durationFi(){
      return value => {
        if (value < 60) {
          return value + '分';
        }else{
          return parseInt(value/60)+'小时'+(value%60)+'分'
        }
      }
    }
  },
  created() {
    this.options = this.qqData
  },
  methods: {
    showEdit(col, index){
      if (['timeline','translator','proofreader','secondaryProofreader'].includes(col)){
        this.tempObject = JSON.stringify(this.tableData[col][index])
      }else{
        this.tempObject = JSON.stringify(this.tableData[col])
      }

      if (col === 'remark'){
        // this.tempText = this.tableData[col].value;
        // this.tempBoolean = this.tableData[col].lock;
        this.tempNumber = this.tableData.state;
      }

      // console.log(this.tempObject)
      //
      // if (col === 'remark'){
      //   this.tempText = this.tableData[col].value;
      //   this.tempBoolean = this.tableData[col].lock;
      //   this.tempNumber = this.tableData.state;
      // }else if (['cutName','title'].includes(col)){
      //   this.tempText = this.tableData[col].value;
      //   this.tempBoolean = this.tableData[col].lock;
      // }else if (col === 'duration'){
      //   this.tempNumber = this.tableData[col].value;
      //   this.tempBoolean = this.tableData[col].lock;
      // }else if (col === 'origin'){
      //   this.tempNumber = this.tableData[col].value;
      //   this.tempBoolean = this.tableData[col].lock;
      // }

    },
    hideEdit(col, index){
      if (['timeline','translator','proofreader','secondaryProofreader'].includes(col)){
        if (this.tempObject !== JSON.stringify(this.tableData[col][index])){
          let colMap = {}
          this.tableData[col][index].index = index
          colMap[col] = [this.tableData[col][index]]
          console.log(this.tableData)
          this.sendMsg('update', colMap)
        }
      }else if (this.tempObject !== JSON.stringify(this.tableData[col])){
        let colMap = {}
        if (col === 'remark'){
          if (this.tempNumber !== this.tableData.state){
            colMap['state'] = this.tableData[col]
          }
        }else if (col === 'slivers'){
          for (let k in this.tableData){
            if (['row','slivers','timeline','translator','proofreader','secondaryProofreader'].includes(k)){
              colMap[k] = this.tableData[k]
            }
          }
        }
        colMap[col] = this.tableData[col]
        console.log(this.tableData)
        this.sendMsg('update', colMap)
      }

      // if (col === 'remark'){
      //   if (this.tempText !== this.tableData[col].value ||
      //       this.tempBoolean !== this.tableData[col].lock ||
      //       this.tempNumber !== this.tableData.state){
      //     this.sendMsg('update', col)
      //   }
      // }else if(['cutName','title'].includes(col)){
      //   if (this.tempText !== this.tableData[col].value ||
      //       this.tempBoolean !== this.tableData[col].lock){
      //     this.sendMsg('update', col)
      //   }
      // }else if (col === 'duration'){
      //   if (this.tempNumber !== this.tableData[col].value ||
      //       this.tempBoolean !== this.tableData[col].lock){
      //     this.sendMsg('update', col)
      //   }
      // }

    },
    sendMsg(action, colMap){
      let data = {
        action: action,
        data: {
          sid: this.tableData.sid
        }
      }
      for (let col in colMap){
        data.data[col] = colMap[col]
      }

      // let data = `{
      //   "action": "${action}",
      //   "data": {
      //     "sid": "${this.tableData.sid}",
      //     "${col}": ${JSON.stringify(this.tableData[col])}
      //     }
      // }`
      console.log(data)
      this.$sendMessage(this.$store, JSON.stringify(data))
    },
    addSliver(data){
      let du = data.duration.value;
      let se = data.slivers.length+1;
      let len = parseInt(du/se);

      data.slivers = [];
      for (let i = 0; i<data.row; i++){
        let temp = []
        temp[0]=len*i;
        temp[1]=len*(i+1);
        data.slivers[i] = temp;
      }
      let temp = []
      temp[0]=len*data.row;
      temp[1]=du;
      data.slivers[data.row]=temp;
      data.row++;

      let vv = ['timeline','translator','proofreader']
      for (let v in vv){
        data[vv[v]].push({
          "row": 1,
          "state": 0,
          "link": "",
          "receiveTime": "",
          "finishTime": "",
          "producer": 0,
          "lock": false
        })
      }

      data.secondaryProofreader[0].row++;


    },

    sliver(data){
      let du = data.duration.value;
      let se = data.slivers.length+1;
      let le = du-this.startTime;

    },

    /**
     * 拆分单元格
     * @param data
     * @param index
     */
    splitCell(data, index){
      let row = data[index].row
      data[index].row = 1
      let arr =[]
      for (let i = 1; i < row; i++){
        arr.push({
          "row": 1,
          "state": 0,
          "link": "",
          "receiveTime": "",
          "finishTime": "",
          "producer": 0,
          "lock": false
        })
      }
      data = data.splice(index+1,0,...arr)
    },
    /**
     * 合并单元格
     * @param data
     * @param index
     */
    mergeCell(data, index){
      data[index].row = data[index].row + data[index+1].row
      data = data.splice(index+1,1)
    }

  }
}
</script>

<style scoped>
  .column{
    display:inline-block;
    vertical-align: top;
  }
  .text-col-box{
    display:flex;
    padding: 5px;
    align-items: flex-start;
    justify-content:center;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    cursor: default;
  }
  .text-col{
    display:flex;
    margin: auto 0;
    white-space: normal;
    word-break:break-all;
  }
</style>
