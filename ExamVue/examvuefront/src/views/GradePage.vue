<template>
  <div>
    <h1>{{title}}</h1>
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
                      >
      </el-table-column >
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
import {includes} from "core-js/internals/array-includes";
import {mapActions} from "vuex";
export default {
  name: "Grade",
  data(){

    return{
      size: '',
      isDialogShow:false,
      title:"个人成绩表",
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      currentPage:1,
      option:'保存',
      limit:10,
      tableData:[],
      nowTotal:'',
      imgUrl:'',
    };
  },
  methods:{
    ...mapActions(['asynAllowQo']),
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
    console.log("grade")
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