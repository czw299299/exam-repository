<template>
  <div>
    <h1>{{tittle}}</h1>
    <el-button type="primary" @click="addHandler">新建</el-button>
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
                       v-if="item.prop!='sstate'">
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       :formatter="sstateRule"
                       width="150" v-if="item.prop=='sstate'">
      </el-table-column >

      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改科目信息</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="delHandler(rowdata.row)">删除科目</el-button>
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
          layout="sizes, prev, pager, next"
          :total="innerTotal">
      </el-pagination>
    </div>
    <!--        弹窗-->
    <el-dialog ref="infoShow" :visible.sync="isDialogShow">
      <el-form>
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='sid'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'||isuporadd!=3&&item.prop!='sstate'" v-model="AObject[item.prop]"></el-input>
          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='sstate' " v-model="AObject[item.prop]" >
            <el-option  :value="1" label="启用" ></el-option>
            <el-option  :value="2" label="禁用" ></el-option>
          </el-select>
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

export default {
  name: "subjectManger",
  data(){
    return{
      tittle:"科目管理",
      AObject:[],
      OldAObject:[],
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      isDialogShow: false,
      isuporadd:1,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      isEdit:false,
      subjectFrom:{
        subject:{},
      },
    };
  },
  methods:{
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

    sstateRule(row,index){
      if (row!=null) {
        if (row.sstate == 1) {
          return '启动'
        }
        if (row.sstate == 2) {
          return '禁用'
        }

      }
    },
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
    },
    alterHandler:function (r) {
      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
      const subject={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        subject[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      this.AObject=subject
      console.log(this.AObject)
      this.isDialogShow = true;
    },

    delHandler:function (r) {
      console.log(r.rid);
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
      this.subjectFrom.subject.sid=r.sid
      console.log(this.subjectFrom)
      this.$axios.post("/exam/delSubject",this.subjectFrom)
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
      this.subjectFrom.subject=this.AObject
      if (this.isuporadd==1){
        this.$axios({
          url:"/exam/addSubject",
          method:'POST',
          data:this.subjectFrom
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.sid=res.data.map.subject.sid
            const subject={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              subject[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(subject);
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
        this.subjectFrom.subject.smtime=this.getCurrentTime();
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.sid == this.tableData[i].sid) {
            for (let j = 0; j <this.tabCol.length ; j++) {
                this.tableData[i][this.tabCol[j].prop]=this.AObject[this.tabCol[j].prop]
            }
            console.log(this.tableData[i])
            break;
          }
        }
        console.log(this.subjectFrom)
        this.$axios({
          url:"/exam/alterSubject",
          method:'POST',
          data:this.subjectFrom
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
      //     for (let i = 0; i < this.tableData.length; i++) {
      //       if (this.AObject.sid == this.tableData[i].sid) {
      //         for (let j = 0; j <this.tabCol.length ; j++) {
      //           this.tableData[i][this.tabCol[j].prop]=this.OldAObject[this.tabCol[j].prop]
      //         }
      //         break;
      //   }
      //     }
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
    console.log("subject")
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.tabCol.forEach(r=>this.AObject[r.prop]="");
    this.initTable(1,10);
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>