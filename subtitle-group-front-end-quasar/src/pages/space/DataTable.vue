<template>
  <div style="height: calc(100% - 90px); margin: 10px 10px 0 10px;">
    <div style="width: 100%;overflow: hidden">
      <div ref="title" style="white-space: nowrap;">
        <q-card
            v-for="col in columns"
            class="q-pa-sm text-center"
            :style="'display:inline-block;width:'+ col.width+'px'"
            style="background:#eee; font-weight: bold;"
            flat bordered square>
          {{col.title}}
        </q-card>
      </div>
    </div>

    <q-scroll-area
        :thumb-style="thumbStyle"
        @scroll="contentScroll"
        ref="scrollArea"
        class="fit">
      <div style="background: rgba(0, 0, 0, 0.12); padding-top: 10px;">
        <data-table-item v-for="data in tableData" :columns="columns" :qq-data="qqData" :table-data="data"/>
      </div>
    </q-scroll-area>

    <q-fab style="position: absolute; right: 20px; bottom: 20px;" color="purple" icon="add" direction="up">
<!--      <q-fab-action color="primary" @click="addJob" icon="mail" />-->
    </q-fab>
  </div>

</template>

<script>
import DataTableItem from "components/table/DataTableItem";
import TableData from "assets/data.json"
import QQData from "assets/qqdata.json"

export default {
  name: "DataTable",
  components: {DataTableItem},
  data: ()=>({
    columns: [
      { field: "remark",     title: "备注", width: 80},
      { field: "cutName",     title: "切片名", width: 300},
      { field: "title",       title: "标题", width: 200},
      { field: "origin",      title: "源", width: 100 },
      { field: "duration",    title: "时长", width: 100 },
      { field: "slivers",     title: "分片", width: 100 },
      { field: "timeline",    title: "时轴", width: 100 },
      { field: "translator",  title: "翻译", width: 100 },
      { field: "proofreader", title: "校对", width: 100 },
      { field: "cut",         title: "剪辑", width: 100 },
      { field: "afterEffect", title: "后期", width: 100 },
      { field: "compaction",  title: "压制", width: 100 },
      { field: "cover",       title: "封面", width: 100 },
      { field: "artist",      title: "美工", width: 100 },
      { field: "secondaryProofreader", title: "二校", width: 100 },
    ],
    tableData: null,
    qqData: QQData.data,
    thumbStyle: {
      right: '2px',
      borderRadius: '5px',
      backgroundColor: '#027be3',
      opacity: 0.5
    },

    path: ''
  }),
  methods: {
    contentScroll({horizontalPosition}){
      this.$refs.title.style.marginLeft = -horizontalPosition + "px"
    },
    addJob(){

    }
  },
  mounted() {
    let path = this.$route.path
    let pathSp = path.split('/')
    if (pathSp.length > 3){
      this.path = '/'+pathSp[1]+'/'+pathSp[2]
    }else{
      this.path = path
    }
    this.$api({
      url: '/query'+this.path
    }).then(res => {
      this.tableData = res.data
    })
    let _this = this
    setTimeout(()=>{
      _this.$store.state.socket.onmessage = function(msg) {
        console.log(msg.data);
        // console.log("sssssssssssssss")
        let jsonData = JSON.parse(msg.data)
        if (jsonData.action === "update"){
          // console.log(this.tableData)
          console.log(_this.tableData);
          for (let d in _this.tableData){
            let data = _this.tableData[d]
            if (data.sid === jsonData.data.sid){
              for (let k in jsonData.data){
                // if (typeof jsonData.data[k] === '')
                // console.log(typeof jsonData.data[k])
                if (['timeline','translator','proofreader','secondaryProofreader'].includes(k)){
                  if (jsonData.data[k][0]['index']!==undefined){
                    data[k][jsonData.data[k][0]['index']] = jsonData.data[k][0]
                  }else{
                    data[k] = jsonData.data[k]
                  }
                }else{
                  data[k] = jsonData.data[k]
                }

              }
              // data.cutName.value = jsonData.data.cutName.value
            }
          }
          // this.tableData.forEach(v => {
          //   // console.log(v)
          //
          // })
        }

      };
    },1000)

  },
  watch: {
    // path(){
    //   console.log(this.path)
    //   this.$api({
    //     url: '/query'+this.path
    //   }).then(res => {
    //     this.tableData = res.data
    //   })
    //   // getSheetData(this.path).then(res => {
    //   //   console.log(res.data)
    //   // })
    // }
  }
}
</script>
