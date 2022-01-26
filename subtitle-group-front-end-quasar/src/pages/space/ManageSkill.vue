<template>
  <div style="height: calc(100% - 90px); margin: 10px 10px 0 10px;">
    <div style="width: 100%;overflow: hidden;text-align: center">
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
        style="height: 100%;text-align: center">
      <div style="">
        <div v-for="user in users" style="white-space: nowrap">
          <div v-for="col in columns" class="column" :style="'width:'+ col.width+'px'">
            <q-card
                v-if="['name','qq'].includes(col.field)"
                class="col-card text-weight-bold"
                :style="{background: userColor[user.state].color}"
                flat bordered square>
              {{user[col.field]}}
            </q-card>
            <q-card
                v-else-if="col.field==='remark'"
                class="col-card"
                flat bordered square>
              {{user[col.field]}}
            </q-card>
            <q-card
                v-else
                class="col-card"
                :style="{backgroundColor: colColor[user.skill[col.field]].color}"
                flat bordered square>
              {{user.skill[col.field]!==0?colColor[user.skill[col.field]].label:''}}
            </q-card>
          </div>
        </div>
      </div>
    </q-scroll-area>
  </div>
</template>

<script>

export default {
  name: "ManageSkill",
  data: ()=>({
    userColor: {
      0: {
        color: "#87ffc1",
        label: "可工作"
      },
      1: {
        color: "#fe2c23",
        label: "无法工作"
      }
    },
    colColor: {
      0: {
        color: "#ffffff",
        label: "不会"
      },
      1: {
        color: "#a3e043",
        label: "一般"
      },
      2: {
        color: "#ffd900",
        label: "会"
      },
      3: {
        color: "#956fe7",
        label: "擅长"
      }
    },
    columns: [
      { field: "name",      title: "昵称", width: 100},
      { field: "qq",     title: "QQ", width: 100},
      { field: "remark",     title: "备注", width: 100},
      { field: "origin",      title: "源", width: 80 },
      { field: "rebroadcast",    title: "转播", width: 80 },
      { field: "timeline",  title: "时轴", width: 80 },
      { field: "effectTimeline", title: "特效轴", width: 80 },
      { field: "translator",         title: "翻译", width: 80 },
      { field: "proofreader", title: "校对", width: 80 },
      { field: "cut",  title: "剪辑", width: 80 },
      { field: "afterEffect",       title: "后期", width: 80 },
      { field: "artist",      title: "美工", width: 80 },
      { field: "compaction",      title: "压制", width: 80 },
      { field: "auth",      title: "管理", width: 80 },
    ],
    users: [],
    thumbStyle: {
      right: '2px',
      borderRadius: '5px',
      backgroundColor: '#027be3',
      opacity: 0.5
    },
  }),
  methods: {
    contentScroll({horizontalPosition}){
      this.$refs.title.style.marginLeft = -horizontalPosition + "px"
    },
  },
  mounted() {
    let _this = this
    let state = this.$store.state
    let pertain = state.router
    if (pertain === ''){
      let path = this.$route.path
      let pathSp = path.split('/')
      if (pathSp.length > 3){
        pertain = '/'+pathSp[1]+'/'+pathSp[2]
      }else{
        pertain = path
      }
    }
    if (state.userMap[pertain]==null){
      this.$api({
        url: '/userlist'+pertain
      }).then(res => {
        console.log(res.data)
        _this.users = res.data
        _this.$store.commit("addUserMap", {group: pertain, users: res.data})
      })
    }else{
      _this.users = state.userMap[pertain]
    }
  }
}
</script>

<style scoped>
  .column{
    display:inline-block;
    vertical-align: top;
  }
  .col-card{
    height: 35px;
    line-height: 35px;
  }
</style>