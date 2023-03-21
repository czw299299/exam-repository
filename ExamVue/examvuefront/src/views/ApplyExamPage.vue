<template>
  <div>
    <h1>{{tittle}}</h1>
    <div class="block">
      <div>
        <el-button  type="primary" @click="checkHandler()">查看报考信息</el-button>
      </div>
      <div class="block">
      <span class="demonstration">   科目:</span>
    <el-select  v-model="examFrom.examVo.sid" @change="so">
      <el-option :value="null" :label="'全部'" ></el-option>
      <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
    </el-select>
      <span class="demonstration"> 考官:</span>
      <el-select  v-model="examFrom.examVo.aid" @change="so" >
      <el-option :value="null" :label="'全部'" ></el-option>
      <el-option  :value="examiner.aid" :label="examiner.aacc" v-for="(examiner,index) in innerAdmins"></el-option>
    </el-select>

    </div>
      <div class="block" >
        <el-date-picker @change="so"
            v-model="value1"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
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
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate==1" type="primary" @click="applyHandler(rowdata.row)">报名考试</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate!=1" type="primary">考试已过期</el-button>
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
  </div>
</template>

<script>
import {mapActions} from "vuex";

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
      tittle:"考试报名管理",
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
      isuporadd:1,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      isEdit:false,
      value1:new Date(),
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
    ...mapActions(['asynSaveTabCol']),
    ...mapActions(['asynSaveTabData']),
    ...mapActions(['asynSaveTabTotal']),
    checkHandler(){
      this.$axios(
          {
            url:"/exam/initallow",
            method:'POST',
            data:this.$store.getters.getExaminee
          }
      ).then(res=>
          {
            if (res.data.id==1)
            {
              console.log(res.data.map.datas)
              this.asynSaveTabCol(res.data.map.datas.fieldDesc)
              this.asynSaveTabData(res.data.map.datas.dataList)
              this.asynSaveTabTotal(res.data.map.datas.dataTotal)
              this.$router.push("/menu/initallow");
            }
          }
      ).catch(err=>console.log(err))},
    applyHandler(r){

      const allow={
        eeid:this.$store.getters.getExaminee.eeid,
        eid:r.eid
      }
      this.$axios(
          {
            url:"/exam/applyexam",
            method:'POST',
            data:allow
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
          console.log(r.eid);
          let p = Object.getOwnPropertyNames(r);
          let ind = -1;
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
        }
      }).catch(err=>console.log(err));
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
      const starttime= this.formatDate(this.value1, "yyyy-MM-dd hh:mm:ss")
      // const starttime= this.formatDate2(this.value1)
      this.examFrom.examVo.estime=starttime
      this.examFrom.examVo.eeid=this.$store.getters.getExaminee.eeid
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
      console.log(this.tableData)
    }
  },
  created() {
    console.log("applyexam")
    this.tabCol=this.$store.getters.getTabCol
    if (this.tabCol!=null){
      this.tabCol.forEach(r=>this.AObject[r.prop]="");
    }
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.nowTotal=this.innerTotal

    this.initSubjects();
    this.initExaminers();
    this.initTable(1,10);
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>