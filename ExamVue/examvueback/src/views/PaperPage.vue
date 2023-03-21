<template>
  <div>
    <h1>{{tittle}}</h1>
    <div>
      <el-button type="primary" @click="addHandler">新建试卷</el-button>
    </div>
    <div>
     科目: <el-select  v-model="paperFrom.paper.sid" @change="so">
      <el-option :value="null" :label="'全部'" ></el-option>
      <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
    </el-select>
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
                       v-if="item.prop!=='sid'"
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

      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null" type="primary" @click="showHandler(rowdata.row)">查看试卷信息</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改试卷信息</el-button>
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
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='pid'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'||isuporadd!=1&& item.prop!='sid'" v-model="AObject[item.prop]"></el-input>
          <el-select @change="sidTopic(AObject[item.prop])" :disabled="isEdit" v-if="item.type==='select' && item.prop=='sid'&&isuporadd!=3" v-model="AObject[item.prop]">
            <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
          </el-select>
          <el-select :disabled="true" v-if="item.type==='select' && item.prop=='sid'&&isuporadd==3" v-model="AObject[item.prop]">
          <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
        </el-select>
        </el-form-item>
        <div style="text-align: center" >
          <el-transfer
              style="text-align: left; display: inline-block"
              v-model="value"
              filterable
              :render-content="renderFunc"
              :titles="['未拥有题目', '已拥有题目']"
              :button-texts="['到左边', '到右边']"
              :format="{
        noChecked: '${total}',
        hasChecked: '${checked}/${total}'
      }"
              @change="handleChange"
              :data="data">
            <!--            <el-button class="transfer-footer" slot="left-footer" size="small">操作</el-button>-->
            <!--            <el-button class="transfer-footer" slot="right-footer" size="small">操作</el-button>-->
          </el-transfer>
        </div>
                <span>
                    <el-button type="primary" @click="saveHandler">{{option}}</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "paperManger",
  data(){
    const generateData = _ => {
      const data = [];
      for (let i = 1; i <= 15; i++) {
        data.push({
          key:i,
          label: `备选项 ${ i }`,
          disabled: i % 4 === 0,
          id:i
        });
      }
      return data;
    };
    return{
      tittle:"试卷管理",
      AObject:[],
      OldAObject:[],
      oldSid:'',
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      isDialogShow: false,
      innerSubjects:[],
      isuporadd:1,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      isEdit:false,
      paperFrom:{
        paper:{},
        subject:{},
      },
      nowTotal:'',
      value: [],
      data: generateData(),
      renderFunc(h, option) {
        return <span>{ option.key } - { option.label }</span>;
      }

    };
  },
  methods:{
    sleep(ms) { //sleep延迟方法2
      var unixtime_ms = new Date().getTime();
      while(new Date().getTime() < unixtime_ms + ms) {}
    },
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
      console.log(this.data)
    },
    sidTopic(sid){
      this.paperFrom.subject.sid=sid
      this.$axios(
          {
            url:'/exam/sidtopic',
            method:'POST',
            data:this.paperFrom
          }
      ).then(res=>{
       this.data=this.topics(res.data.map.topics)

      }).catch(error=>console.log(error))
    },
    rTopic(r){
      this.paperFrom.subject.sid=r.sid
      this.$axios(
          {
            url:'/exam/sidtopic',
            method:'POST',
            data:this.paperFrom
          }
      ).then(res=>{
        this.data=this.topics(res.data.map.topics)
        this.ownTopics(r)
        if (this.isuporadd==2){
          for (let i = 0; i <this.data.length ; i++) {
            this.data[i].disabled=true
          }
        }else {
          for (let i = 0; i <this.data.length ; i++) {
            this.data[i].disabled=false
          }
        }
      }).catch(error=>console.log(error))
    },

    ownTopics(r){
      this.paperFrom.paper.pid=r.pid
      this.$axios({
        url:'/exam/owntopics',
        method:'POST',
        data:this.paperFrom
      }).then(res=>{
        const topics=new Array();
        for (let i = 0; i <this.data.length ; i++) {
          for (let j = 0; j <res.data.map.topics.length; j++) {
            if (this.data[i].tid==res.data.map.topics[j].tid){
              topics.push(this.data[i].key)
            }
          }
        }
        this.value=topics
      }).catch(error=>console.log(error))

    },
    topics(datas){
      const  data=[]
      for (let i =1; i <=datas.length ; i++) {
        data.push(
            {
              key:i,
              tid:datas[i-1].tid,
              label:datas[i-1].tcontent,
              disabled:false
            }
        )
      }
      return data;
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
    so(){
       this.$axios(
           {
             url:"/exam/paperSo",
             method:'POST',
             data:this.paperFrom
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
      this.isDialogShow = true;
      this.data=new Array();
      this.value=new Array();
    },
    showHandler:function (r) {
      this.isuporadd=2;
      this.isEdit=true
      this.option="确认"
      const paper={}
      for (let i = 0; i <this.tabCol.length ; i++) {
           paper[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      this.AObject=paper
      this.rTopic(r)
      this.isDialogShow = true;
    },
    alterHandler:function (r) {
      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
          this.oldSid=this.OldAObject['sid']
      const paper={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        paper[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      this.AObject=paper
      console.log(this.AObject)
      this.rTopic(r)
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
      this.paperFrom.paper.pid=r.pid
      console.log(this.paperFrom)
      this.$axios.post("/exam/delpaper",this.paperFrom)
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
      this.paperFrom.paper=this.AObject
      if (this.isuporadd==1){
        const  datas=[]
        console.log("value"+this.value)
        for (let i = 0; i <this.value.length; i++) {
          console.log()
          if (this.data[this.value[i]-1]!=null) {
            datas.push({tid: this.data[this.value[i] - 1].tid})
          }
        }
        this.paperFrom.topics=datas
        this.$axios({
          url:"/exam/addpaper",
          method:'POST',
          data:this.paperFrom
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.pid=res.data.map.paper.pid
            const paper={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              paper[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(paper);
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
        this.paperFrom.paper.pmtime=this.getCurrentTime();     for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.pid == this.tableData[i].pid) {
            for (let j = 0; j <this.tabCol.length ; j++) {
              this.tableData[i][this.tabCol[j].prop]=this.AObject[this.tabCol[j].prop]
            }
            console.log(this.tableData[i])
            break;
          }
        }
        const  datas=[]
        console.log("value"+this.value)
        for (let i = 0; i <this.value.length; i++) {
          console.log()
          if (this.data[this.value[i]-1]!=null) {
            datas.push({tid: this.data[this.value[i] - 1].tid})
          }
        }
        this.paperFrom.topics=datas
        console.log(this.paperFrom)
        this.$axios({
          url:"/exam/alterpaper",
          method:'POST',
          data:this.paperFrom
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
    console.log("paper")
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.nowTotal=this.innerTotal
    this.tabCol.forEach(r=>this.AObject[r.prop]="");
    this.initSubjects();
    this.initTable(1,10);
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>