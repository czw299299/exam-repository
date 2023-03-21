<template>
  <div>
    <h1>{{tittle}}</h1>
      <div style="width: 400px">
        <span class="demonstration">   考生状态:</span>
        <el-select  v-model="examineeFrom.examineeVo.examinee.eestate" @change="so">
          <el-option :value="null" :label="'全部'" ></el-option>
          <el-option :value="1" :label="'禁止'"></el-option>
          <el-option :value="2" :label="'通过'"></el-option>
        </el-select>
      </div>
    <div class="demo-input-suffix" >
      <el-input
          style="width: 400px"
          placeholder="请输入考生名"
          v-model="examineeFrom.examineeVo.examinee.eename">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
      <el-button  type="primary" @click="so">搜索</el-button>
    </div>
    <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        max-height="500">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
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
                       v-if="item.prop!=='eestate'&&item.prop!=='eeimg'&&item.prop!=='rid'"
                      >
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="ridRule"
                       v-if="item.prop=='rid'"
      >
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       :formatter="eestateRule"
                       v-if="item.prop=='eestate'"
      >
      </el-table-column >

      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null&&rowdata.row.eestate==1" type="primary" @click="alterHandler(rowdata.row)">批准</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.eestate==2" type="primary" @click="alterHandler(rowdata.row)">禁止</el-button>
        </template>

      </el-table-column>
    </el-table>
    <div class="block">
      <el-button  type="primary" @click="pass">选中通过</el-button>
      <el-button  type="primary" @click="ban">选中禁止</el-button>
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
export default {
  name: "examManger",
  data(){

    return{
      tittle:"考生管理管理",
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      nowTotal:'',
      imgUrl:'',
      multipleSelection: [],
      examineeFrom:{
        examineeVo:
            {
              examinee:{
                eename:"",
                eestate:null,
              }
            },
        examinee: {
          eestate:"",
          eeid:""
        }
      },
    };
  },
  methods:{
    pass(){
      this.examineeFrom.examineeVo.examinees=undefined
      this.examineeFrom.examineeVo.examinees=new Array()
      for (let i = 0; i <this.multipleSelection.length ; i++) {
        if (this.multipleSelection[i]!=null){
          this.examineeFrom.examineeVo.examinees[i]=this.multipleSelection[i]
          this.examineeFrom.examineeVo.examinees[i].eestate=2
        }

      }
      this.$axios(
          {
            url:"/exam/alterexaminees",
            method:'POST',
            data:this.examineeFrom.examineeVo
          }
      ).then(res=>{
        if (res.data.id==1){
          for (let i = 0; i <this.multipleSelection ; i++) {
            for (let j = 0; j < this.innerData; j++) {
              if(Object.is(this.innerData[j],this.multipleSelection[i])){
                this.innerData[j].eestate=1
              }
            }
          }
        }
      }).catch(err=>console.log(err))
    },
    ban(){
      this.examineeFrom.examineeVo.examinees=undefined
      this.examineeFrom.examineeVo.examinees=new Array()
      for (let i = 0; i <this.multipleSelection.length ; i++) {
        if (this.multipleSelection[i]!=null){
          this.examineeFrom.examineeVo.examinees[i]=this.multipleSelection[i]
          this.examineeFrom.examineeVo.examinees[i].eestate=1
        }

      }
      this.$axios(
          {
            url:"/exam/alterexaminees",
            method:'POST',
            data:this.examineeFrom.examineeVo
          }
      ).then(res=>{
        if (res.data.id==1){
          for (let i = 0; i <this.multipleSelection ; i++) {
            for (let j = 0; j < this.tableData[j]; j++) {
              if(Object.is(this.tableData[j],this.multipleSelection[i])){
                this.tableData[j].eestate=1
              }
            }
          }
        }
      }).catch(err=>console.log(err))
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },
   eestateRule(row,index){
      if (row!=null){
          if (row.eestate==1){
            return '禁止'
          }
          else if (row.eestate==2) {
            return '通过'
          }
        }

    },
    ridRule(row,index){
      if (row!=null){
        return "考生"
      }

    },
    alterHandler:function (r) {
      this.examineeFrom.examinee.eeid=r.eeid
      if (r.eestate==1){
        this.examineeFrom.examinee.eestate=2
      }else {
        this.examineeFrom.examinee.eestate=1
      }
      this.$axios(
          {
            url:"/exam/alterexaminee",
            method:'POST',
            data:this.examineeFrom.examinee
          }
      ).then(res=>{
             if (res.data.id==1){
               for (let i = 0; i < this.tableData.length; i++) {
                 if (this.examineeFrom.examinee.eeid == this.tableData[i].eeid) {
                     this.tableData[i]['eestate']=this.examineeFrom.examinee.eestate
                   console.log(this.tableData[i])
                   break;
                 }
               }
             }
      }).catch(error=>console.log(error));
    },
    so(){
       this.$axios(
           {
             url:"/exam/examineeSo",
             method:'POST',
             data:this.examineeFrom.examineeVo
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
    console.log("paper")
    this.imgUrl=this.$store.getters.getImgUrl
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.nowTotal=this.innerTotal

    this.initTable(1,10);
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>