<template>
  <div>
    <h1>{{tittle}}</h1>
    <template>
      <div>
        <p><i class="el-icon-time"></i> {{ `${hr}: ${min}: ${sec}` }}</p>
        <el-button v-show="[1,4,5].includes(exam.estate)" @click="begin" round type="primary">开始答题</el-button>
        <el-button v-show="exam.estate==2" type="primary" @click="alterexamHandler(exam,3)">暂停考试</el-button>
        <el-button v-show="[2,3].includes(exam.estate)" type="primary" @click="alterexamHandler(exam,4)">中断考试</el-button>
        <el-button v-show="[2,3].includes(exam.estate)" type="primary" @click="open">结束考试</el-button>
        <el-button v-show="[3].includes(exam.estate)" type="primary" @click="continueexam">继续考试</el-button>
      </div>
    </template>

    <div>
      <div style="width: 300px; height: 80px">
        <span class="demonstration">   考生状态:</span>
        <el-select  v-model="allowFrom.allowVo.awstate" @change="so">
          <el-option :value="null" :label="'全部'" ></el-option>
          <el-option :value="1" :label="'未进入'"></el-option>
          <el-option :value="2" :label="'正在考试'"></el-option>
          <el-option :value="3" :label="'暂停'"></el-option>
          <el-option :value="4" :label="'中断'"></el-option>
          <el-option :value="5" :label="'交卷'"></el-option>
          <el-option :value="7" :label="'离开'"></el-option>
        </el-select>
      </div>
    </div>

    <div class="demo-input-suffix" >
      <el-input
          style="width: 400px"
          placeholder="请输入考生名称"
          v-model="allowFrom.allowVo.eename">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
      <el-button  type="primary" @click="so">搜索</el-button>
    </div>
    <el-table
        :data="tableData"
        style="width: 100%"
        max-height="500">
      <el-table-column
          fixed="right"
          label="头像"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-image v-if="rowdata.row!=null"
                    style="width:100px; height: 50px"
                    :src="imgUrl+rowdata.row.eeimg">
            <div slot="placeholder" class="image-slot">
              加载中<span class="dot">...</span>
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       v-if="
                       item.prop!=='awstate'
                       &&item.prop!=='eeimg'
                       &&item.prop!=='ename'
                       &&item.prop!=='estime'
                       &&item.prop!=='eetime'
                       &&item.prop!=='estate'
                       &&item.prop!=='eid'"
                      >
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="awstateRule"
                       v-if="item.prop=='awstate'"
      >
      </el-table-column >
      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null&&[1,2,7].includes(rowdata.row.estate)&&[1,2,7].includes(rowdata.row.awstate)" type="primary" @click="alterHandler(rowdata.row,3)">暂停考试</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate===2&&rowdata.row.awstate===3" type="primary" @click="alterHandler(rowdata.row,1)">恢复考试</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.awstate===5" type="primary" @click="lookHandler(rowdata.row)">查看考试成绩</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.awstate===7"  type="primary">考生离开考试页面</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="limit"
          layout="total,sizes, prev, pager, next,jumper"
          :total="innerTotal">
      </el-pagination>
    </div>
    <!--        弹窗-->
  </div>
</template>

<script>
import {includes} from "core-js/internals/array-includes";
import {mapActions} from "vuex";


export default {
  name: "examineeManger",
  data(){

    return{
      isshow1: true,
      time: '',
      hr: 3,
      min: 30,
      sec: 0,
      tittle:'',
      exam:{},
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      nowTotal:'',
      imgUrl:'',
      clock:"",
      allowFrom:{
        allowVo:
            {
                awstate:null,
            },
      },
    };
  },
  methods:{
    begin() {
      // 点击按钮后开始计算指定长度的时间
      console.log(this.$store.getters.getExam)
      let exam=this.$store.getters.getExam
      this.time = (Date.parse(exam.eetime));
      // 开始执行倒计时
        this.clock=setInterval(this.countdown,1000);
      // 更换按钮，根据情况选择v-if或v-show
      this.$message({
        type: 'success',
        message: '进入考试'
      });
        this.alterexamHandler(exam, 2)
    },
    continueexam() {
      // 点击按钮后开始计算指定长度的时间
      console.log(this.$store.getters.getExam)
      let exam=this.$store.getters.getExam
      this.time = (Date.parse(exam.eetime));
      // 开始执行倒计时
      this.clock=setInterval(this.countdown,1000);
      // 更换按钮，根据情况选择v-if或v-show
      this.$message({
        type: 'success',
        message: '继续考试'
      });
      this.alterexamHandler(exam, 2)
    },
    marry(){
      const  allowVo={
        allowQos:this.innerData
      }
      this.$axios(
          {
            url:"/exam/marry",
            method:'POST',
            data:allowVo
          }
      ).then(res=>{
        if (res.data.id==1){
          // this.so()
          this.innerData=res.data.map.allowQos
          this.initTable(this.currentPage,this.limit)
        }
      }).catch(err=>{
        console.log(err)
      })
    },
    countdown() {
      const end = this.time; // 定义结束时间
      const now = Date.parse(new Date()); // 获取本地时间
      const msec = end - now; // 定义总共所需的时间
      // 将时间戳进行格式化
      let hr = parseInt(msec / 1000 / 60 / 60 % 24);
      let min = parseInt(msec / 1000 / 60 % 60);
      let sec = parseInt(msec / 1000 % 60);
      // 倒计时结束时的操作
      if (this.hr <= 0 && this.min <= 0 && this.sec <= 0) {
        console.log('时间已经结束，考试结束');
        this.hr = 3;
        this.min = 30;
        this.sec = 0;
        this.alterexamHandler(this.exam,5)
      } else {
        // 如时间未归零则继续在一秒后执行
        this.hr = hr > 9 ? hr : '0' + hr;
        this.min = min > 9 ? min : '0' + min;
        this.sec = sec > 9 ? sec : '0' + sec;

      }
      this.marry()
    },

    open() {
      this.$confirm('即将结束考试, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((action) => {
        // eleUI的确定结束回调函数方法
        if (action === 'confirm') {
          this.hr = 3;
          this.min = 30;
          this.sec = 0;
          console.log(this.hr + ',' + this.min + ',' + this.sec);
         this.alterexamHandler(this.exam,5)
        }
        this.$message({
          type: 'success',
          message: '交卷成功!'
        });
      }).catch(() => {
        // 点击取消后
        this.$message({
          type: 'info',
          message: '已取消交卷'
        });
      });

    },
    ...mapActions(['asynExam']),
    alterexamHandler:function (r,key) {
      const exam={
        eid:r.eid,
        estate:key
      }
      this.$axios(
          {
            url:"/exam/alterestate",
            method:'POST',
            data:exam
          }
      ).then(res=>{
        if (res.data.id==1){
          this.exam.estate=key
          this.asynExam(this.exam)
            for (let i = 0; i <this.innerTotal ; i++) {
              this.innerData[i].estate=key
            }
          if (key===4||key===3||key===5){
            clearInterval(this.clock)
          }else if (key==2){
            this.so()
          }

        }
      }).catch(err=>console.log(err))

    },
    alterHandler(r,key){
      const  allowVo={
        awid:r.awid,
        awstate:key,
        awmtime:this.getCurrentTime()
      }
      this.$axios(
          {
            url:"/exam/alterallow",
            method:'POST',
            data:allowVo
          }
      ).then(res=>{
        if (res.data.id==1)
        {
          this.$message(
              {
                showClose:true,
                center:true,
                message:res.data.message,
                type:'success'
              }
          )
          let p = Object.getOwnPropertyNames(r);
          for(let i=0;i<this.innerData.length;i++){
            if(Object.is(this.innerData[i],r)){
              this.innerData[i].awstate=allowVo.awstate
              break;
            }
          }
        }
        else {
          this.$message(
              {
                showClose:true,
                center:true,
                message:res.data.message,
                type:'error'
              }
          )
        }
      }).catch(err=>console.log(err))
    },
    lookHandler(r) {

    },

    awstateRule(row,index){
      if (row!=null){
          if (row.awstate==1){

            return '未进入'
        }else if (row.awstate==2){
            return '正在考试'
          }
          else if (row.awstate==3){
            return '考试暂停'
          }
          else if (row.awstate==4){
            return '考试中断'
          }
          else if (row.awstate==5){
            return '交卷'
          }
          else if (row.awstate==6){
            return '取消资格'
          }
          else if (row.awstate==7){
            return '中途离开'
          }

    }
      },
    ridRule(row,index){
      if (row!=null){
        return "考生"
      }

    },
    so(){
      this.allowFrom.allowVo.eid=this.exam.eid
       this.$axios(
           {
             url:"/exam/onlineallowSo",
             method:'POST',
             data:this.allowFrom.allowVo
           }
       ).then(res=>{
         console.log(res.data);
         if (res.data.id==1){
           this.innerData=res.data.map.datas.dataList
           this.nowTotal=res.data.map.datas.dataTotal
           this.innerTotal=res.data.map.datas.dataTotal
           this.tabCol=res.data.map.datas.fieldDesc
           if (this.nowTotal/this.limit<this.currentPage){
             this.currentPage=(this.nowTotal/this.limit)+1
             this.currentPage=Math.floor(this.currentPage)
             console.log(this.currentPage)
           }
           this.initTable(this.currentPage,this.limit)
         }

       }).catch(error=>console.log(error));
    },


    getCurrentTime() {
      var date = new Date();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentDate = date.getFullYear() + "-" + month + "-" + strDate
          + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
      return currentDate;
    },
    //  时间字符串格式化 yyyy-mm-dd
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.limit=val;
      this.initTable(this.currentPage,val);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
      this.initTable(val,this.limit)

    },
    initTable(page,limit){
      this.tableData= new Array();
      let now=page*limit-limit;
      for (let i =now; i <now+limit; i++) {
        this.tableData.push(this.innerData[i])
      }
    }
  },
  created() {
    console.log("allow")
    this.exam=this.$store.getters.getExam
    this.tittle=this.$store.getters.getExam.ename
    this.imgUrl=this.$store.getters.getImgUrl
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.nowTotal=this.innerTotal

    this.initTable(1,10);
    if (this.exam.estate==2){
      this.begin()
    }
  },
  mounted() {
    this.getCurrentTime();

  },
  destroyed() {
    clearInterval(this.clock)
  }
}
</script>

<style scoped>

</style>