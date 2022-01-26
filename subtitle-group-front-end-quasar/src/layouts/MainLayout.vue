<template>
  <q-layout view="hHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title>
          字幕组工作台
        </q-toolbar-title>

        <q-chip
            @click="$store.state.user==null?loginDialog = true:''"
            clickable
            size="lg" color="grey-1"
            class="cursor-pointer">
          <q-avatar font-size="40">
            <img :src="'http://q.qlogo.cn/headimg_dl?dst_uin='+($store.state.user==null?0:$store.state.user.qq)+'&spec=40&img_type=jpg'" alt="头像">
          </q-avatar>
          {{ $store.state.user?$store.state.user.name:'登录' }}
        </q-chip>

      </q-toolbar>
    </q-header>

    <q-drawer
        :width="256"
        v-model="leftDrawerOpen"
        show-if-above
        bordered>
      <q-list style="font-size: 18px; font-weight: 500; padding-top: 10px" >
        <q-item
            to="/"
            active-class="active-link"
            clickable v-ripple exact>
          <q-item-section avatar>
            <q-icon name="home"/>
          </q-item-section>
          <q-item-section>首页</q-item-section>
        </q-item>

        <group-link
            v-for="g in group"
            :data="g"/>

      </q-list>
    </q-drawer>

    <q-dialog v-model="loginDialog" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">QQ登录</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input label="QQ号 *"
                   v-model="qq" autofocus
                   :rules="[ val => val && val.length > 0 || 'QQ号不能为空']"
                    />
          <q-input label="验证码 *"
                   v-model="code"
                   :rules="[
                      val => val && val.length > 0 || '验证码不能为空',
                      val => val.length === 6 || '验证码为6位数字'
                    ]">
            <template v-slot:append>
              <q-btn
                  @click="getCode"
                  color="primary" label="获取验证码" />
            </template>
          </q-input>
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn label="取消" v-close-popup />
          <q-btn
              @click="login"
              label="登录" color="primary" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-page-container>
      <router-view/>
    </q-page-container>

    <q-footer class="bg-grey-4 text-dark" style="padding-left: 10px">
      &copy;{{ new Date().getFullYear() }} -
      <strong><a href="https://github.com/Colter23" target="_blank">Colter<q-icon name="mdi-github" size="xs"></q-icon></a></strong>
    </q-footer>
  </q-layout>
</template>

<script>

import GroupLink from "components/GroupLink";
import GroupData from "assets/group.json"

export default{
  name: 'MainLayout',
  components: {
    GroupLink,
  },
  data: ()=>({
    loginDialog: false,
    qq: null,
    code: null,

    leftDrawerOpen: false,
    group: null,

    socket:null,
    userId: 3375582524,
    toUserId:'2',
    content:'3'

  }),
  methods:{
    toggleLeftDrawer () {
      this.leftDrawerOpen = !this.leftDrawerOpen
    },
    login(){
      let _this = this
      this.$api({
        url: '/verify/'+this.qq+'/'+this.code,
      }).then(res => {
        if (res.data === true){
          _this.$api({
            url: '/userInfo/'+_this.qq
          }).then(res => {
            _this.$store.commit("addUser", res.data)
            _this.group = res.data.organization
          })
        }else{
          _this.$q.notify({
            message: '登录失败',
            color: 'red'
          })
        }
      })
    },
    getCode(){
      this.$api({
        url: '/sendVerify/'+this.qq,
      }).then(res => {
        if (res.data === true){
          this.$q.notify({
            message: '验证码发送成功，请注意查收QQ消息',
            color: 'secondary'
          })
        }else{
          this.$q.notify({
            message: '发送失败, 请检查QQ号是否正确, 以及你是否在字幕组中',
            color: 'red'
          })
        }
      })
    }

  },
  mounted() {
    // this.openSocket()
    this.$openSocket(this.$store)

    let _this = this
    this.$api({
      url: '/isVerify',
    }).then(res => {
      console.log(res.data)
      if (res.data !== false){
        _this.$api({
          url: '/userInfo/'+res.data
        }).then(res => {
          _this.$store.commit("addUser", res.data)
          _this.group = res.data.organization
        })
      }
    })
  }
}
</script>
<style>
a {
  color: #1976d2;
  text-decoration: none;
}
.active-link{
  color: #1976d2;
  background: #e4effa;
}

</style>
