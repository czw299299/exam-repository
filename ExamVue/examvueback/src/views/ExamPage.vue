<template>
  <div>
    <h1>{{tittle}}</h1>
    <div>
      <el-button type="primary" @click="addHandler">新建试卷</el-button>
    </div>
    <div class="block">

      <div class="block">
      <span class="demonstration">   科目:</span>
    <el-select  v-model="examFrom.examVo.sid" @change="initPapers">
      <el-option :value="null" :label="'全部'" ></el-option>
      <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
    </el-select>
      <span class="demonstration"> 考卷:</span>
      <el-select  v-model="examFrom.examVo.pid" >
        <el-option :value="null" :label="'全部'" ></el-option>
        <el-option  :value="paper.pid" :label="paper.pname" v-for="(paper,index) in innerPapers"></el-option>
      </el-select>
      <span class="demonstration"> 考官:</span>
      <el-select  v-model="examFrom.examVo.aid" >
      <el-option :value="null" :label="'全部'" ></el-option>
      <el-option  :value="examiner.aid" :label="examiner.aacc" v-for="(examiner,index) in innerAdmins"></el-option>
    </el-select>

    </div>
    <div class="block">
      <span class="demonstration">日期:</span>
      <el-date-picker
          v-model="value1"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right">
      </el-date-picker>
      <el-button  type="primary" @click="so">搜索</el-button>
    </div>
    </div>
    <el-table
        :data="tableData"
        style="width: 100%"
        max-height="500">
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       v-if="item.prop!=='sid'&& item.prop!=='aid'&&item.prop!=='pid'&&item.prop!=='estate'"
                      >
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="sidRule"
                       v-if="item.prop=='sid'"
      >
      </el-table-column >
        <el-table-column v-for="(item,index) in tabCol"
                         fixed
                         :prop="item.prop"
                         :label="item.desc"
                         :key="index"
                         width="150"
                         :formatter="aidRule"
                         v-if="item.prop=='aid'"
        >
        </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="sidRule"
                       v-if="item.prop=='pid'"
      >
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="estateRule"
                       v-if="item.prop=='estate'"
      >
      </el-table-column >

      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null" type="primary" @click="showHandler(rowdata.row)">查看试卷信息</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改考试信息</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="delHandler(rowdata.row)">删除试卷</el-button>
          <el-button v-if="rowdata.row==null" type="primary" @click="addHandler">新建试卷</el-button>
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
    <el-dialog ref="infoShow" :visible.sync="isDialogShow">
      <el-form>
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='eid'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'||isuporadd!=1&& item.prop!='sid'&&item.prop!='aid'&&item.prop!='pid'&&item.prop!='estime'&&item.prop!='eetime'&&item.prop!='estate'" v-model="AObject[item.prop]"></el-input>
          <el-select  @change="initPapers2" :disabled="isEdit" v-if="item.type==='select' && item.prop=='sid'" v-model="AObject[item.prop]">
            <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
          </el-select>
          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='aid'" v-model="AObject[item.prop]">
            <el-option  :value="examiner.aid" :label="examiner.aacc" v-for="(examiner,index) in innerAdmins"></el-option>
          </el-select>
          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='pid'" v-model="AObject[item.prop]">
            <el-option :value="null" :label="'空试卷'" ></el-option>
            <el-option  :value="paper.pid" :label="paper.pname" v-for="(paper,index) in innerPapers2"></el-option>
          </el-select>
          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='estate'" v-model="AObject[item.prop]">
            <el-option :value="null" label="'无状态'" ></el-option>
            <el-option :value="1" label="未开始" ></el-option>
            <el-option :value="2" label="进行中" ></el-option>
            <el-option :value="3" label="暂停" ></el-option>
            <el-option :value="4" label="中断" ></el-option>
            <el-option :value="5" label="结束" ></el-option>
          </el-select>
            <div class="block" v-if="item.prop=='estime'">
              <el-date-picker
                  @change="changestarttime"
                  v-model="value2"
                  type="datetime"
                  placeholder="选择日期时间">
              </el-date-picker>
            </div>
          <div class="block"   v-if="item.prop=='eetime'">
            <el-date-picker
                @change="changeendtime"
                v-model="value3"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
                <span>
                    <el-button type="primary" @click="saveHandler">{{option}}</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
      </el-form>
    </el-dialog >
    <el-dialog ref="infoShow" :visible.sync="isDialogShow2">
      <el-container>
        <el-main>
          <el-row>
            <el-col :span="12">
              <div class="grid-content bg-purple">
                <div>
                <h3 class="demonstration"> 单选题</h3>
                <div  v-for="(test,index) in radioTopics" :key="index">
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
                <div>
                <h3 class="demonstration"> 多选题</h3>
                <div  v-for="(test,index) in checkTopics" :key="index">
                  <p>{{index + 1}}.{{test.tcontent}}</p>
                  <el-checkbox-group    @change="p(test)" v-model="checks[index]">
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
<!--              <div class="grid-content bg-purple-light">-->
<!--                <h3>题号:</h3>-->
<!--                <div class="tihao">-->
<!--                   <span class="ti active">{{index + 1}}</span>-->
<!--                  <div v-for="(test,index) in innerTopics" :key="index">-->
<!--                <span v-if="das[index].length == '0'" class="ti">-->
<!--                  {{index + 1}}-->
<!--                  &lt;!&ndash; {{key}} &ndash;&gt;-->
<!--                </span>-->
<!--                    <span v-else class="ti active">{{index + 1}}</span>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->

            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "examManger",
  data(){

    return{
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      tittle:"考试管理",
      AObject:[],
      OldAObject:[],
      oldSid:'',
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      isDialogShow: false,
      isDialogShow2: false,
      innerSubjects:[],
      innerAdmins:[],
      innerPapers:[],
      innerPapers2:[],
      innerTopics:[],
      radioTopics:[],
      checkTopics:[],
      isuporadd:1,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      isEdit:false,
      value1: [new Date(),new Date()],
      value2:"",
      value3:"",
      das:new Array(),
      radios:new Array(),
      checks:new Array(),
      examFrom:{
        examVo:
            { sid:" ",
              pid:" ",
              aid:" "
            },
        exam:
            {

            },
        paperVo:
            {
          paper:
              {
                sid:" "
              }
            }
      },
      nowTotal:'',
    };
  },
  methods:{
    p(r){
      console.log(this.das)
      console.log(r)
    },
    changestarttime(){
      const starttime = this.formatDate(this.value2, "yyyy-MM-dd hh:mm:ss")
      this.AObject.estime=starttime;

    },
    changeendtime(){
      const endtime = this.formatDate(this.value3, "yyyy-MM-dd hh:mm:ss")
      this.AObject.eetime=endtime;
    },
    padLeftZero(str) {
  return ('00' + str).substr(str.length);
},

 formatDate(date, fmt) {
      if(!date){
        return '';
      }
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
      }
      let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
      };
      for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
          let str = o[k] + '';
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : this.padLeftZero(str));
        }
      }
      return fmt;
    },
    sleep(ms) { //sleep延迟方法2
      var unixtime_ms = new Date().getTime();
      while(new Date().getTime() < unixtime_ms + ms) {}
    },
    ownTopics(r){
      this.innerTopics=new Array()
      this.radioTopics=new Array()
      this.checkTopics=new Array()
      this.radios=new Array()
      this.checks=new Array()
      this.examFrom.paperVo.paper.pid=r.pid
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
          this.isDialogShow2=true
      }).catch(error=>console.log(error))

    },
   estateRule(row,index){
      const estates=['未开始','考试中','暂停','中断','结束']
      if (row!=null){
        for (let i = 0; i <estates.length ; i++) {
          if ((i+1)==row.estate){
            return estates[i]
          }
        }

      }
    },
    sidRule(row,index){
      if (row!=null){
        for (let i = 0; i <this.innerSubjects.length ; i++) {
          if (this.innerSubjects[i].sid==row.sid){
            return this.innerSubjects[i].sname+""
          }
        }

      }
    },
    aidRule(row,index){
      if (row!=null){
        for (let i = 0; i <this.innerAdmins.length ; i++) {
          if (this.innerAdmins[i].aid==row.aid){
            return this.innerAdmins[i].aacc+""
          }
        }

      }
    },
    so(){
      const starttime= this.formatDate(this.value1[0], "yyyy-MM-dd hh:mm:ss")
      const endtime=this.formatDate(this.value1[1], "yyyy-MM-dd hh:mm:ss")
      this.examFrom.examVo.estime=starttime
      this.examFrom.examVo.eetime=endtime
      console.log(this.examFrom.examVo)
       this.$axios(
           {
             url:"/exam/examSo",
             method:'POST',
             data:this.examFrom.examVo
           }
       ).then(res=>{
         console.log(res.data);
         if (res.data.id==1){
           this.innerData=res.data.map.datas.dataList
           this.nowTotal=res.data.map.datas.dataTotal
           this.innerTotal=res.data.map.datas.dataTotal
           this.tabCol=res.data.map.datas.fieldDesc
           if (this.tabCol!=null){
             this.tabCol.forEach(r=>this.AObject[r.prop]="");
           }
           if (this.nowTotal/this.limit<this.currentPage){
             this.currentPage=(this.nowTotal/this.limit)+1
             this.currentPage=Math.floor(this.currentPage)
             console.log(this.currentPage)
           }
           this.initTable(this.currentPage,this.limit)
         }

       }).catch(error=>console.log(error));
    },
    initSubjects()
    {
      this.$axios({
        url:"/exam/subjects",
        method:'POST',
      }).then(res=>{
        console.log(res.data);
        if (res.data.id==1){
          this.innerSubjects=res.data.map.subjects
        }
      }).catch(error=>console.log(error));

    },
    initExaminers()
    {
      this.$axios({
        url:"/exam/examiners",
        method:'POST',
      }).then(res=>{
        console.log(res.data);
        if (res.data.id==1){
          this.innerAdmins=res.data.map.examiners
        }
      }).catch(error=>console.log(error));

    },
    initPapers()
    {
      this.examFrom.paperVo.paper.sid=this.examFrom.examVo.sid
      console.log(this.examFrom.paperVo.paper)
      this.$axios({
        url:"/exam/papers",
        method:'POST',
        data:this.examFrom.paperVo
      }).then(res=>{
        console.log(res.data);
        if (res.data.id==1){
          this.innerPapers=res.data.map.papers
        }
      }).catch(error=>console.log(error));

    },
    initPapers2()
    {
      const paperVo={
        paper:{
          sid:this.AObject.sid
        }
      }

      this.$axios({
        url:"/exam/papers",
        method:'POST',
        data:paperVo
      }).then(res=>{
        console.log(res.data);
        if (res.data.id==1){

          this.innerPapers2=res.data.map.papers
          if (this.isuporadd==3){
            let key=0;
            for (let i = 0; i <this.innerPapers2.length ; i++) {
              if (this.innerPapers2[i].pid==this.AObject.pid){
                key=1;
              }
            }
            if (key!=1){
              this.AObject.pid=null
            }


          }
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
    addHandler: function () {
      // this.tabCol.forEach(r=>this.AObject[r.prop]="");
      this.isuporadd=1;
      this.isEdit=false;
      this.option="添加";
      this.AObject = {};
      this.value2=new Date()
      this.value3=new Date()
      this.isDialogShow = true;
    },
    showHandler:function (r) {
      // this.isuporadd=2;
      // this.isEdit=true
      // this.option="确认"
      // const paper={}
      // for (let i = 0; i <this.tabCol.length ; i++) {
      //      paper[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      // }
      // this.AObject=paper
      // this.isDialogShow = true;
      this.ownTopics(r)
    },
    alterHandler:function (r) {

      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
          this.oldSid=this.OldAObject['sid']
      const exam={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        exam[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      this.AObject=exam
      this.value2=exam.estime
      this.value3=exam.eetime
      this.initPapers2()
      console.log(this.AObject)
      this.isDialogShow = true;

    },

    delHandler:function (r) {
      console.log(r.rid);
      let p = Object.getOwnPropertyNames(r);
      let ind = -1;
      let ind2 = -1;
      for(let i=0;i<this.innerData.length;i++){
        if(Object.is(this.innerData[i],r)){
          ind = i;
          break;
        }
      }

      if(ind!=-1) console.log("ind="+ind);
      this.innerData.splice(ind,1);

      this.innerTotal=this.innerTotal-1;
      if (this.innerData.length%this.limit==0){
        this.currentPage=this.currentPage-1;
      }
      this.initTable(this.currentPage,this.limit)
      this.examFrom.examVo.eid=r.eid
      console.log(this.examFrom)
      this.$axios.post("/exam/delexam",this.examFrom.examVo)
          .then(res=>{
            console.log(res.data);
            this.$message(
                {
                  showClose:true,
                  center:true,
                  message:res.data.message,
                  type:'success'
                }
            )
          }).catch(error=>console.log(error));;
    },
    //保存
    saveHandler:function () {
      console.log(this.AObject);
      this.examFrom.exam=this.AObject
      if (this.isuporadd==1){
        this.changestarttime()
        this.changeendtime()
        this.examFrom.exam=this.AObject
        this.$axios({
          url:"/exam/addexam",
          method:'POST',
          data:this.examFrom.exam
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.eid=res.data.map.exam.eid
            const exam={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              exam[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(exam);
            this.innerTotal+=1;
            this.initTable(this.currentPage,this.limit);
          }
          this.$message(
              {
                showClose:true,
                center:true,
                message:res.data.message,
                type:'success'
              }
          )
        })
      }
      if (this.isuporadd==3){
        this.examFrom.exam.emtime=this.getCurrentTime();
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.eid == this.tableData[i].eid) {
            for (let j = 0; j <this.tabCol.length ; j++) {
              this.tableData[i][this.tabCol[j].prop]=this.AObject[this.tabCol[j].prop]
            }
            console.log(this.tableData[i])
            break;
          }
        }
        console.log(this.examFrom)
        this.$axios({
          url:"/exam/alterexam",
          method:'POST',
          data:this.examFrom.exam
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.$message(
                {
                  showClose:true,
                  center:true,
                  message:res.data.message,
                  type:'success'
                }
            )

          }
        }).catch(error=>console.log(error));
      }
      this.isDialogShow = false;
    },
    //取消
    cancelHandler:function () {
      // if (this.isuporadd==3) {
      //   for (let i = 0; i < this.tableData.length; i++) {
      //     if (this.AObject.pid == this.tableData[i].pid) {
      //       for (let j = 0; j <this.tabCol.length ; j++) {
      //         this.tableData[i][this.tabCol[j].prop]=this.OldAObject[this.tabCol[j].prop]
      //       }
      //       console.log(this.tableData[i])
      //       break;
      //     }
      //   }
      // }
      this.isDialogShow = false;
      console.log(this.OldAObject)
    },
    initTable(page,limit){
      this.tableData= new Array();
      let now=page*limit-limit;
      for (let i =now; i <now+limit; i++) {
        this.tableData.push(this.innerData[i])
      }
      console.log(this.tableData)
    }
  },
  created() {
    console.log("exam")
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.nowTotal=this.innerTotal
    this.tabCol.forEach(r=>this.AObject[r.prop]="");
    this.initSubjects();
    this.initExaminers();
    this.initPapers();
    this.initTable(1,10);
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>