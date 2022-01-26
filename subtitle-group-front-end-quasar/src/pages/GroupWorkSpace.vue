<template>
  <q-page style="height: 0;">
    <q-tabs
        outside-arrows
        mobile-arrows
        v-model="tab"
        dense inline-label
        align="left"
        class="shadow-2"
        :breakpoint="0">
      <q-route-tab exact name="name" :label="basePath" icon="article" />
      <q-route-tab :to="basePath" exact name="space" label="公告板" icon="article" />
      <q-route-tab :to="basePath+'/sheet'" exact name="sheet" label="工作表" icon="feed" />
      <q-route-tab :to="basePath+'/skill'" exact name="skill" label="职能表" icon="feed" />
      <q-route-tab :to="basePath+'/manage'" exact name="manage" label="组员" icon="feed" />
      <q-route-tab :to="basePath+'/live'" exact name="live" label="直播记录" icon="feed" />
<!--      <q-route-tab to="time" exact name="alarms" label="完成汇总" icon="alarm" />-->
<!--      <q-route-tab to="time" exact name="alarms" label="直播记录" icon="alarm" />-->
    </q-tabs>
    <router-view/>
  </q-page>
</template>

<script>
export default {
  name: "GroupWorkSpace",
  data: ()=>({
    tab: 'sheet',
    basePath: ''
  }),
  methods:{
    updatePath(path){
      let pathSp = path.split('/')
      if (pathSp.length > 3){
        this.basePath = '/'+pathSp[1]+'/'+pathSp[2]
      }else{
        this.basePath = path
      }
      this.$store.commit('changeRouter', this.basePath)

    }
  },
  mounted() {
    let path = this.$route.path
    this.updatePath(path)
  },
  beforeRouteUpdate(to, from, next){
    this.updatePath(to.path)
    next()
  },
  watch: {
    "$store.state.user"(){
      this.$store.commit('updateAdmin', this.$store.state.user.pertain[this.basePath].auth !== 0)
    }
  }
}
</script>

<style scoped>

</style>