<template>
  <div>
    <h1>{{tittle}}</h1>
    <div>
    <el-upload
        class="upload-demo"
        ref="upload"
        action="/api/exam/uploadtipics"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-change="handleChange"
        :on-success="handleSuccess"
        :http-request="handleRequest"
        :file-list="fileList"
        :auto-upload="false">
      <el-button slot="trigger" size="small" type="primary" >选取execl题库文件</el-button>
      <!--                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
      <div slot="tip" class="el-upload__tip" >只能上传Execl文件，且不超过500kb</div>
    </el-upload>
    <el-button type="primary" @click="addsHandler">批量导入</el-button>
      <el-button type="primary" @click="addHandler">新建题目</el-button>

    </div>
    <div>
     科目: <el-select  v-model="topicFrom.subject.sid" @change="so">
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
          <el-button v-if="rowdata.row!=null" type="primary" @click="showHandler(rowdata.row)">查看题目</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改题目信息</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="delHandler(rowdata.row)">删除题目</el-button>
          <el-button v-if="rowdata.row==null" type="primary" @click="addHandler">新建</el-button>
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
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='tid'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'||isuporadd!=3&&item.prop!='isAsr'&& item.prop!='sid'" v-model="AObject[item.prop]"></el-input>
          <el-select :disabled="isEdit" v-if="item.type==='select' && item.prop=='sid' " v-model="AObject[item.prop]">
            <el-option  :value="subject.sid" :label="subject.sname" v-for="(subject,index) in innerSubjects"></el-option>
          </el-select>
          <el-checkbox-group
              v-model="checkedOptions"
              v-if="item.prop=='isAsr'"
              >
            <el-checkbox :disabled="isEdit" v-for="anwer in anwers" :label="anwer" :key="anwer">{{anwer}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
                <span>
                    <el-button type="primary" @click="saveHandler">{{option}}</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
const Options = ['A', 'B', 'C', 'D','E'];
export default {
  name: "topicManger",
  data(){
    return{
      tittle:"题目管理",
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
      topicFrom:{
        topic:{},
        subject:{},
      },
      fileItems:[],
      fileList: [],
      checkedOptions: ['A', 'B'],
      anwers: Options,
      nowTotal:''


    };
  },
  methods:{
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
             url:"/exam/topicSo",
             method:'POST',
             data:this.topicFrom
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

    initChecked(r){
      console.log(r)
      let str=r['isAsr']
      this.checkedOptions=str.split(',')
    },
    initStr(){
      let str=""
      for (let i = 0; i <this.checkedOptions.length; i++) {
           if (this.checkedOptions[i]!=null){
              if (i==0){
                str=this.checkedOptions[i]+""
              }else {
                str+=","+this.checkedOptions[i]+""
              }
           }
      }
      return str;
    },
    handleRequest:function (file) {
      this.fileItems.push(file.file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);

    },
    handlePreview(file) {
      console.log(file);
    },
    handleChange:function (file,fileList) {
      console.log("change")
    },
    handleSuccess:function (file,fileList) {
      console.log("success")
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
    addsHandler() {
      this.$refs.upload.submit();
      let formData=new FormData();
      formData.append("txt","tttt");
      for (let i = 0; i <this.fileItems.length ; i++) {
        formData.append("file",this.fileItems[i]);
      }
      this.$axios.post("/exam/uploadtipics",
          formData,
          {headers:{"Content-Type":"multipart/from-data;boundary=~~web"
                  +new Date().getTime()
            }})
          .then(res=>{
            console.log(res.data);
            if (res.data.id==1){
              this.so()
              this.$message(
                  {
                    showClose:true,
                    center:true,
                    message:res.data.message,
                    type:'success'
                  }
              )
            }
          });
    },
    addHandler: function () {
      // this.tabCol.forEach(r=>this.AObject[r.prop]="");
      this.isuporadd=1;
      this.isEdit=false;
      this.option="添加";
      this.AObject = {};
      this.checkedOptions=new Array()
      this.isDialogShow = true;
    },
    showHandler:function (r) {
      this.isuporadd=2;
      this.isEdit=true
      this.option="确认"
      const topic={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        topic[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      console.log(topic)
      this.AObject=topic
      this.initChecked(r)
      this.isDialogShow = true;
    },
    alterHandler:function (r) {
      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
          this.oldSid=this.OldAObject['sid']
      const topic={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        topic[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      console.log(topic)
      this.AObject=topic
      this.initChecked(r)
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
      this.topicFrom.topic.tid=r.tid
      console.log(this.topicFrom)
      this.$axios.post("/exam/deltopic",this.topicFrom)
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
      this.AObject['isAsr']=this.initStr()
      console.log( "anwer"+this.AObject['isAsr'])
      this.topicFrom.topic=this.AObject
      if (this.isuporadd==1){
        this.$axios({
          url:"/exam/addtopic",
          method:'POST',
          data:this.topicFrom
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.tid=res.data.map.topic.tid
            const topic={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              topic[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(topic);
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
        this.topicFrom.topic.tmtime=this.getCurrentTime();
        this.AObject.isAsr=this.initStr()
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.tid == this.tableData[i].tid) {
            for (let j = 0; j <this.tabCol.length ; j++) {
              this.tableData[i][this.tabCol[j].prop]=this.AObject[this.tabCol[j].prop]
            }
            console.log(this.tableData[i])
            break;
          }
        }

        console.log(this.topicFrom)
        this.$axios({
          url:"/exam/altertopic",
          method:'POST',
          data:this.topicFrom
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
      //     if (this.AObject.tid == this.tableData[i].tid) {
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
    console.log("topic")
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