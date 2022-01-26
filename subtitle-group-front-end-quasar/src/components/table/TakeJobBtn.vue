<template>
  <q-btn
      class="full-width"
      @click="seamless = true"
      style="background-image: url('https://img.colter.top/vivid/img/bell/topbg01.png')!important;
                background-position: center center!important; background-size: cover!important;
                font-weight: bold; width: 220px!important;
                display: flex; margin-bottom: 10px; font-size: 20px">
    <div style=" font-size: 30px;color: #f48fb1;
    text-shadow: 2px 2px 2px rgba(255,255,255,1);"> {{ btnText }} </div>
  </q-btn>
  <q-dialog v-model="seamless">
    <q-card>
      <q-card-section>
        <div class="text-h6">确定{{ btnText }}吗</div>
      </q-card-section>
      <q-card-section class="q-pt-none">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. In inventore iste libero omnis quibusdam. Alias aliquam eligendi facilis minima perspiciatis soluta voluptatum. Assumenda consequuntur culpa facilis! Aperiam distinctio quidem reprehenderit?
      </q-card-section>

      <q-card-actions align="right">
        <q-btn label="算了" color="red" v-close-popup />
        <q-btn :label="btnText" @click="takeJob" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name: "TakeJobBtn",
  props: {
    data: null,
    multiple: false,
  },
  data: () => ({
    seamless: false,
    state: 0,
    stateText: {
      0: '接!',
      1: '不干了',
      2: 'NTR',
    }
  }),
  computed:{
    btnText(){
      if (this.multiple){
        this.state = this.data.producer.includes(this.$store.state.user.qq)?1:0
      }else{
        this.state = this.data.producer===0?0:this.data.producer===this.$store.state.user.qq?1:2
      }
      return this.stateText[this.state]
    },
  },
  methods:{

    takeJob(){
      Date.prototype.format = function(fmt) {
        var o = {
          "M+" : this.getMonth()+1,                 //月份
          "d+" : this.getDate(),                    //日
          "h+" : this.getHours(),                   //小时
          "m+" : this.getMinutes(),                 //分
          "s+" : this.getSeconds(),                 //秒
          "q+" : Math.floor((this.getMonth()+3)/3), //季度
          "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
          fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
          if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
          }
        }
        return fmt;
      }
      if (this.multiple){
        if (this.state === 0){
          this.data.producer.push(this.$store.state.user.qq)
          this.data.state = 1
        }else if (this.state === 1){
          this.data.producer.splice(this.data.producer.indexOf(this.$store.state.user.qq),1)
          if (this.data.producer.length === 0){
            this.data.state = 0
          }
        }
      }else{
        if (this.state===0){
          this.data.producer = this.$store.state.user.qq
          this.data.receiveTime = new Date().format("yyyy-MM-dd hh:mm:ss")
          this.data.state = 1
        }else if (this.state === 1){
          this.data.producer = 0
          this.data.state = 0
        }else if (this.state === 2){
          this.data.producer = this.$store.state.user.qq
        }
      }
    }
  }
}
</script>

<style scoped>

</style>