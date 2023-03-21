<template>
  <div>
    <h1>{{tittle}}</h1>
    <div>
      <div style="width: 300px; height: 80px">
        <span class="demonstration">   考试状态:</span>
        <el-select  v-model="allowFrom.allowVo.estate" @change="so">
          <el-option :value="null" :label="'全部'" ></el-option>
          <el-option :value="1" :label="'未开始'"></el-option>
          <el-option :value="2" :label="'已开始'"></el-option>
          <el-option :value="3" :label="'暂停'"></el-option>
          <el-option :value="4" :label="'中断'"></el-option>
          <el-option :value="5" :label="'结束'"></el-option>
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
                       v-if="item.prop!=='awstate'&&item.prop!=='eid'&&item.prop!=='pid'&&item.prop!=='eeid'&&item.prop!=='estate'"
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
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate==1&&rowdata.row.awstate!=6" type="primary" @click="cancleallowHandler(rowdata.row)">取消报考</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate==2&&[1,2,7].includes(rowdata.row.awstate)" type="primary" @click="joinHandler(rowdata.row)">进入考试</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate==5&&rowdata.row.awstate!=6" type="primary" @click="lookHandler(rowdata.row)">查看考试成绩</el-button>
          <el-button v-if="rowdata.row!=null&&rowdata.row.estate==4&&rowdata.row.awstate!=6" type="primary" >考试中断</el-button>
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
      <el-descriptions class="margin-top" title="个人信息确认" :column="3" :size="size" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            准考证
          </template>
          {{AObject.awid}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            头像
          </template>
          <el-image
              style="width:100px; height: 100px"
              :src="imgUrl+AObject.eeimg">
            <div slot="placeholder" class="image-slot">
              加载中<span class="dot">...</span>
            </div>
          </el-image>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{AObject.eename}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            居住地
          </template>
          福州市
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            考场
          </template>
          {{AObject.eid}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            开考时间
          </template>
          <el-tag size="small">{{AObject.estime}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            结束时间
          </template>
          <el-tag size="small">{{AObject.eetime}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            联系地址
          </template>
          福州至诚学院
        </el-descriptions-item>
      </el-descriptions>
        <span>
                    <el-button type="primary" @click="allowHandler">确定</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
    </el-dialog >
  </div>
</template>

<script>
import {includes} from "core-js/internals/array-includes";
import {mapActions} from "vuex";
export default {
  name: "examManger",
  data(){

    return{
      size: '',
      isDialogShow:false,
      tittle:"考生报考管理",
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      currentPage:1,
      option:'保存',
      limit:10,
      AObject:{},
      tableData:[],
      nowTotal:'',
      imgUrl:'',
      allowFrom:{
        allowVo:
            {

            },
      },
    };
  },
  methods:{
    ...mapActions(['asynAllowQo']),
    allowHandler(){
      const allowVo={}
      allowVo.awid=this.AObject.awid
      allowVo.awmtime=this.getCurrentTime()
      if (this.AObject.awstate==7){
        allowVo.awstate=3
      }else {
        allowVo.awstate=2
      }

      console.log(allowVo)
      this.$axios(
          {
            url:"/exam/alterallow",
            method:'POST',
            data:allowVo
          }
      ).then(res=>{
        if (res.data.id==1){
          this.asynAllowQo(this.AObject);
          console.log(this.AObject.pid)
          this.$router.push("/menu/joinexam")
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
    cancelHandler(){
      this.isDialogShow=false
    },
    joinHandler(r){
      this.AObject.eeimg=this.$store.getters.getExaminee.eeimg
      this.AObject.awid=r.awid
      this.AObject.eid=r.eid
      this.AObject.pid=r.pid
      this.AObject.eename=this.$store.getters.getExaminee.eename
      this.AObject.ename=r.ename
      this.AObject.estate=r.estate
      this.AObject.awstate=r.awstate
      this.AObject.estime=r.estime
      this.AObject.eetime=r.eetime
      this.isDialogShow=true
    },
    lookHandler(r){},
    cancleallowHandler(r){
      const allowVo={}
      allowVo.awid=r.awid
      this.$axios(
          {
            url:"/exam/cancleallow",
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
        this.allowFrom.allowVo.eeid=this.$store.getters.getExaminee.eeid
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