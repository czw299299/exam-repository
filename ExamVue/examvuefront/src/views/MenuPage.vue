<template>
  <el-row class="tac" >
    <el-col :span="12" style="width: 100%">
      <h5>学生考试平台</h5>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-submenu   v-for="(item1,key1) in menuh" :index="key1+''">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>{{item1.mname}}</span>
          </template>
          <!--                    <el-submenu  v-for="(item2,key2)  in menus2" v-if="item2.pmid===item1.mid" :index="key1+'-'+key2">-->
          <!--                        <template slot="title">{{item2.menuName}}</template>-->
          <el-menu-item @click="tomenus(item2.murl)"  :index="key1+'-'+key2"  v-for="(item2,key2) in menus">
            <!--                          <router-link :to="('/menu/'+item2.menuUrl)">-->
            {{item2.mname}}
            <!--                          </router-link>-->
          </el-menu-item>
          <!--                    </el-submenu>-->
        </el-submenu>
        <el-menu-item index="2"  @click="tofirst">个人中心</el-menu-item>
        <el-menu-item index="3"  @click="toLogin">退出登陆</el-menu-item>
      </el-menu>

      <el-container>
        <el-main>
          <router-view class="view">

          </router-view>
        </el-main>
      </el-container>
    </el-col>
    <!--        <el-carousel :interval="4000" type="card" height="200px">-->
    <!--            <el-carousel-item v-for="(img,key) in imgs" :key="key">-->
    <!--&lt;!&ndash;                <img :src="imgUrl+img.img">&ndash;&gt;-->
    <!--            </el-carousel-item>-->
    <!--        </el-carousel>-->
  </el-row>
</template>

<script>
import {mapActions} from 'vuex'
export default {

  name: "MenuPage",
  data(){

    return{
      activeIndex: '1',
      isCollapse: true,
      loginStatus:"",
      menus:[],
      menuh:[],
      imgUrl:"",
      url:"onGoods"
      // imgs:[{img:"123.png"}],


    }
  },
  created() {
    this.menuh=this.$store.getters.getMenuh;
    this.menus=this.$store.getters.getMenus;
    this.imgUrl=this.$store.getters.getImgUrl;
    this.loginStatus=this.$store.getters.getLoginFlag;
    if (this.loginStatus!=true){
      this.$router.push("/")
    }else {
      this.tofirst();
    }
  },
  methods: {
    ...mapActions(['asynSaveTabCol']),
    ...mapActions(['asynSaveTabData']),
    ...mapActions(['asynSaveTabTotal']),
    ...mapActions(['asynLoginFlag']),
    toLogin(){
      this.asynLoginFlag(false)
      this.$router.push("/");
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    tofirst(){
              this.$router.push("/menu/first");

    },
    tomenus(key){
      this.$axios({url:"/exam/"+key,method:"POST",data:this.$store.getters.getExaminee})
          .then(res=>{
            if (res.data.id==1){
              console.log(res.data.map.datas)
              this.asynSaveTabCol(res.data.map.datas.fieldDesc)
              this.asynSaveTabData(res.data.map.datas.dataList)
              this.asynSaveTabTotal(res.data.map.datas.dataTotal)
              this.$router.push("/menu/"+key);
          }
          }).catch(error=>console.log(error));

    },
  }
}
</script>

<style scoped>

</style>