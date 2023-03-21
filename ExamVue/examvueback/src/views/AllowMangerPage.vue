<template>
  <div>
    <h1>{{tittle}}</h1>
    <div>
      <div style="width: 300px; height: 80px">
        <span class="demonstration">   审核状态:</span>
        <el-select  v-model="allowFrom.allowVo.awstate" @change="so">
          <el-option :value="null" :label="'全部'" ></el-option>
          <el-option :value="1" :label="'通过'"></el-option>
          <el-option :value="2" :label="'已开考'"></el-option>
          <el-option :value="3" :label="'暂停'"></el-option>
          <el-option :value="4" :label="'中断'"></el-option>
          <el-option :value="5" :label="'结束'"></el-option>
          <el-option :value="6" :label="'禁止'"></el-option>
        </el-select>
      </div>
    </div>

    <div class="demo-input-suffix" >
      <el-input
          style="width: 400px"
          placeholder="请输入考试名称"
          v-model="allowFrom.allowVo.ename">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
      <el-button  type="primary" @click="so">搜索</el-button>
    </div>
    <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 100%"
        max-height="500">
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       width="150"
                       v-if="item.prop!=='awstate'&&item.prop!=='eid'&&item.prop!=='eeid'&&item.prop!=='estate'&&item.prop!=='eeimg'"
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
          <el-button v-if="rowdata.row!=null&&[1].includes(rowdata.row.awstate)&&[1].includes(rowdata.row.estate)" type="primary" @click="alterHandler(rowdata.row)">取消资格</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.awstate==6&&[1].includes(rowdata.row.estate)" type="primary" @click="alterHandler(rowdata.row)">通过</el-button>
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

export default {
  name: "examManger",
  data(){
    return{
      tittle:"考生报考管理",
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      nowTotal:'',
      imgUrl:'',
      allowFrom:{
        allowVo:
            {
              awstate:1
            },
      },
    };
  },
  methods:{
    alterHandler(r){
      const allowVo={}
      if (r.awstate!=6){
        allowVo.awstate=6
      }else {
        allowVo.awstate=1
      }
      allowVo.awid=r.awid
      allowVo.awmtime=this.getCurrentTime()
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
          console.log(r.eid);
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
   awstateRule(row,index){
      if (row!=null){
          if (row.awstate==1){
            return '通过'
        }
        if (row.awstate==2){
          return '考试中'
        }
        if (row.awstate==3){
          return '暂停'
        }
        if (row.awstate==4){
          return '中断'
        }
        if (row.awstate==5){
          return '结束'
        }
        if (row.awstate==1){
          return '作废'
        }

    }
      },
    estateRule(row,index){
      if (row!=null){
        if (row.estate==1){
          return '未开始'
        }
        else if (row.estate==2){
          return '已开始'
        }
        else if (row.estate==3){
          return '暂停'
        }
        else if (row.estate==4){
          return '中断'
        }
        else if (row.estate==2){
          return '结束'
        }
      }

    },
    ridRule(row,index){
      if (row!=null){
        return "考生"
      }

    },
    so(){
       this.$axios(
           {
             url:"/exam/allowSo",
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
      console.log(this.tableData)
    }
  },
  created() {
    console.log("allow")
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