<template>
  <div>
  <div>
    <h1>{{allowQo.ename}}</h1>
    <p><i class="el-icon-time"></i> {{ `${hr}: ${min}: ${sec}` }}</p>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple">
              <div v-if="radioTopics.length!=0">
                <h3 class="demonstration"> 单选题</h3>
                <div :id="test.tid"  v-for="(test,index) in radioTopics" :key="index">
                      <p>{{index + 1}}.{{test.tcontent}}</p>
                  <el-radio-group  v-model="radios[index]">
                    <el-radio v-if="test.optionA!=null"
                              :label="'A'"
                              :key="'A'"
                    >A.{{test.optionA}}</el-radio>
                    <el-radio  v-if="test.optionB!=null"
                               :label="'B'"
                               :key="'B'"
                    >B.{{test.optionB}}</el-radio>
                    <el-radio  v-if="test.optionC!=null"
                               :label="'C'"
                               :key="'C'"
                    >C.{{test.optionC}}</el-radio>
                    <el-radio  v-if="test.optionD!=null"
                               :label="'D'"
                               :key="'D'"
                    >D.{{test.optionD}}</el-radio>
                    <el-radio v-if="test.optionE!=null"
                              :label="'E'"
                              :key="'E'"
                    >E.{{test.optionE}}</el-radio>
                  </el-radio-group>
                </div>
              </div>
              <div v-if="checkTopics.length!=0">
                <h3 class="demonstration"> 多选题</h3>
                <div  :id="test.tid" v-for="(test,index) in checkTopics" :key="index">
                  <p>{{index + 1}}.{{test.tcontent}}</p>
                  <el-checkbox-group    @change="p()" v-model="checks[index]">
                    <!-- label绑定答案的值,可以绑定索引index,也可以绑定答案内容city -->
                    <el-checkbox  v-if="test.optionA!=null"
                                  :label="'A'"
                                  :key="'A'"
                    >A.{{test.optionA}}</el-checkbox>
                    <el-checkbox  v-if="test.optionB!=null"
                                  :label="'B'"
                                  :key="'B'"
                    >B.{{test.optionB}}</el-checkbox>
                    <el-checkbox  v-if="test.optionC!=null"
                                  :label="'C'"
                                  :key="'C'"
                    >C.{{test.optionC}}</el-checkbox>
                    <el-checkbox  v-if="test.optionD!=null"
                                  :label="'D'"
                                  :key="'D'"
                    >D.{{test.optionD}}</el-checkbox>
                    <el-checkbox v-if="test.optionE!=null"
                                 :label="'E'"
                                 :key="'E'"
                    >E.{{test.optionE}}</el-checkbox>
                  </el-checkbox-group>
                </div>
              </div>
            </div>
          </el-col>

          <el-col :span="12">
            <div class="grid-content bg-purple-light">
              <h3 class="demonstration"> 单选题</h3>
              <h3>题号:</h3>
              <div class="tihao">
                <!-- <span class="ti active">{{index + 1}}</span> -->
               <div v-for="(test,index) in radioTopics " :key="index">
                 <a href="#" :name="test.tid">
               <span v-if="radios[index]==' '" class="ti">
                   {{index + 1}}
                  <!-- {{key}} -->
                </span>
                  <span v-else class="ti active">{{index + 1}}</span>
                 </a>
                </div>
              </div>

            </div>
            <div>
              <div>
                <div class="grid-content bg-purple-light">
                  <h3 class="demonstration"> 多选题</h3>
                  <h3>题号:</h3>
                  <div class="tihao">
                    <!-- <span class="ti active">{{index + 1}}</span> -->
                       <div v-for="(test,index) in checkTopics " :key="index">
                         <a href="#" :name="test.tid">
                           <span v-if="checks[index].length==0" class="ti">
                        {{index + 1}}
                  <!-- {{key}} -->
                           </span>
                      <span v-else class="ti active">{{index + 1}}</span>
                         </a>
                       </div>
                  </div>
                </div>
              </div>
              <el-button @click="open"  type="primary">提交考卷</el-button>
            </div>
          </el-col>

        </el-row>
      </el-main>
    </el-container>
  </div>
    <div>
      <el-dialog  ref="infoShow" :visible.sync="isshow1" :close-on-click-modal="false">
        //html部分
        <template>
          <div class="model">
            <div class="mask">123</div>
            <div class="model-dialog">
              <div class="model-header">
                <span>提示</span>
              </div>
              <div class="model-body">
                <div class="body">考试暂停</div>
              </div>
            </div>
          </div>
        </template>

      </el-dialog>
      </div>
    <div>
      <el-dialog ref="infoShow" :visible.sync="isshow2" :close-on-click-modal="false">
        <el-descriptions class="margin-top" title="个人信息确认" :column="3" :size="size" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            考生号:
          </template>
          {{allowQo.awid}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            考生名:
          </template>
          {{allowQo.eename}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            考场:
          </template>
          {{allowQo.eid}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            成绩:
          </template>
          {{grade}} 分
        </el-descriptions-item>
        </el-descriptions>
         <span>
        <el-button  @click="tofirst"  type="primary">确定</el-button>
          </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      size:"",
      grade:0,
      clock:"",
      isshow1: false,
      isshow2: false,
      time: '',
      hr: 3,
      min: 30,
      sec: 0,
      allowQo:{},
      innerTopics:[],
      radioTopics:[],
      checkTopics:[],
      das:new Array(),
      radios:new Array(),
      checks:new Array(),
      examFrom:{
        paperVo:
            {
              paper:
                  {
                    sid:" ",
                    pid:""
                  }
            }
      }
    }
  },

  created() {
    this.allowQo=this.$store.getters.getAllowQo
    console.log(this.allowQo)
      this.ownTopics(this.allowQo.pid)
    this.begin()
  },

  mounted() {

  },
  destroyed() {
    if (this.allowQo.estate!=5&&this.allowQo.estate!=4&&this.allowQo.awstate!=5){
      this.allowQo.awstate=7
      this.allowHandler()
    }
    clearInterval(this.clock)
  },

  methods: {
    tofirst(){
      this.$router.push("/menu/first")
      this.$message(
          {
            showClose:true,
            center:true,
            message:"考试结束",
            type:'success'
          }
      )
    },
    addGrade(){
        const gradeVo={
            awid:this.allowQo.awid,
            grade:this.grade,
        }
      this.$axios(
          {
            url:"/exam/addGrade",
            method:'POST',
            data:gradeVo
          }
      ).then(res=>{
         if (res.data.id==1){
             this.isshow2=true
         }
      }).catch(err=>{
        console.log(err)
      })
    },
    submitCount(){
      for (let i = 0; i <this.radioTopics.length ; i++) {
          if (this.radios[i]==this.radioTopics[i].da[0]){
                      this.grade+=5;
          }
      }
      for (let i = 0; i <this.checkTopics.length ; i++) {
        let size=0;
        for (let j = 0; j <this.checks[i].length ; j++) {
              let key=this.checkTopics[i].da.findIndex((item)=>(
                   item==this.checks[i][j]
              ))
          if (key!=-1){
            size+=1;
          }
        }
        if (size==this.checks[i].length){
                 this.grade+=5;
        }else if (size!=0){
          this.grade+=3
        }
      }
      console.log(this.grade)
      this.allowQo.awstate=5
      this.allowHandler();
    },
    p(){
      console.log(this.das)
    },

    allowHandler(){
      const allowVo={}
      allowVo.awid=this.allowQo.awid
      allowVo.awmtime=this.getCurrentTime()
      allowVo.awstate=this.allowQo.awstate
      this.$axios(
          {
            url:"/exam/alterallow",
            method:'POST',
            data:allowVo
          }
      ).then(res=>{
        if (res.data.id==1){
          if (allowVo.awstate==5){
                this.addGrade();
          }else {
          this.$message({
            showClose:true,
            center:true,
            message:res.data.message,
            type:'success'
          })
          }
        }else {
          this.$message({
            showClose:true,
            center:true,
            message:res.data.message,
            type:'error'
          })
        }
      }).catch(err=>{
        console.log(err)
      })

    },

    marry(){
      const  allowVo={
        awid:this.allowQo.awid,
        eid:this.allowQo.eid,
        estate:this.allowQo.estate,
        awstate:this.allowQo.awstate
      }
      this.$axios(
          {
            url:"/exam/marry",
            method:'POST',
            data:allowVo
          }
      ).then(res=>{
        if (res.data.id==1){
          this.allowQo.awstate=res.data.map.allowQo.awstate
          this.allowQo.estate=res.data.map.allowQo.estate
         if (this.allowQo.awstate==2||this.allowQo.awstate==1||this.allowQo.estate==2){
           this.isshow1=false
         }
         if (this.allowQo.awstate==3||this.allowQo.estate==3){
            //暂停
           this.isshow1=true
          }
         if (this.allowQo.estate==4){
            //中断
           this.$router.push("/menu/first")
          }else if (this.allowQo.estate==5){
            //结束
           this.submitCount()
            //交卷
          }
        }
      }).catch(err=>{
        console.log(err)
      })
    },
    begin() {
      // 点击按钮后开始计算指定长度的时间
      console.log(this.$store.getters.getExam)
      this.time = (Date.parse(this.allowQo.eetime));
      // 开始执行倒计时
      this.clock=setInterval(this.countdown,1000);
      // 更换按钮，根据情况选择v-if或v-show
      this.$message({
        type: 'success',
        message: '进入考试'
      });
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
      } else {
        // 如时间未归零则继续在一秒后执行
        this.hr = hr > 9 ? hr : '0' + hr;
        this.min = min > 9 ? min : '0' + min;
        this.sec = sec > 9 ? sec : '0' + sec;
        this.marry()
      }

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
        }
        this.submitCount()
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
    ownTopics(pid){
      this.innerTopics=new Array()
      this.radioTopics=new Array()
      this.checkTopics=new Array()
      this.radios=new Array()
      this.checks=new Array()
      this.examFrom.paperVo.paper.pid=pid
      this.$axios({
        url:'/exam/owntopics',
        method:'POST',
        data:this.examFrom.paperVo
      }).then(res=>{
        for (let j = 0; j <res.data.map.topics.length; j++) {
          this.innerTopics[j]=res.data.map.topics[j]
          this.innerTopics[j].da=this.innerTopics[j].isAsr.split(',');
          const da=[]
          const da2=" "
          if (this.innerTopics[j].da.length>1){
            this.checkTopics.push(this.innerTopics[j])
            this.checks.push(da)
          }
          if (this.innerTopics[j].da.length==1) {

            this.radioTopics.push(this.innerTopics[j])
            this.radios.push(da2)
          }
        }
        console.log(this.radioTopics)
        console.log(this.radios)
        console.log(this.checks)
        console.log(this.checkTopics)
      }).catch(error=>console.log(error))

    },
  },
}
</script>
<style>
.mask {
  position: fixed;
top: 0;
  height: 100%;
  width: 100%;
  background-color: rgba(167, 165, 165, 0.486);
  opacity: 0.5;
  z-index: 9;
}
.model-dialog {
  position: absolute;
top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border-radius: 12px;
  width: 600px;
  height: 300px;
  border: 1px solid #f5f5f5;
  overflow: hidden;
  z-index: 10;
}
.model-header {
  position: relative;
  height: 50px;
  padding-left: 10px;
  padding-top: 10px;
  font-size: 20px;
  line-height: 50px;
  background-color: #f5f5f5;
  border-bottom: 1px solid rgb(177, 176, 176);
}
.model-body {
  height: 150px;
  line-height: 150px;
  font-size: 28px;
  text-align: center;
  background-color: #fff;
}
.model-footer {
  background-color: #f5f5f5;
  height: 100px;
  text-align: center;
  line-height: 100px;
}
.btn {
  width: 180px;
  height: 40px;
  border-radius: 8px;
  background-color: rgb(180, 103, 103);
  color: #fff;
  font-size: 18px;
  border: none;
}


h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
/* 题目*/
.tihao {
  display: flex;
  justify-content: space-around;
}
.ti {
  display: inline-block;
  width: 25px;
  height: 25px;
  line-height: 27px;
  border: 1px solid #bbb;
  color: #bbb;
  border-radius: 50%;
  text-align: center;
}
.active {
  background-color: blue;
  color: white;
}
</style>