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
                       v-if="item.prop!='rstate'">
      </el-table-column >
      <el-table-column v-for="(item,index) in tabCol"
                       fixed
                       :prop="item.prop"
                       :label="item.desc"
                       :key="index"
                       :formatter="rstateRule"
                       width="150" v-if="item.prop=='rstate'">
      </el-table-column >

      <el-table-column
          fixed="right"
          label="操作"
          width="120"
      >
        <template slot-scope="rowdata" >
          <el-button v-if="rowdata.row!=null" type="primary" @click="showHandler(rowdata.row)">查看角色权限</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改角色权限</el-button>
          <el-button v-if="rowdata.row!=null" type="primary" @click="delHandler(rowdata.row)">删除角色</el-button>
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
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='rid'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'||isuporadd!=3&&item.prop!='rstate'" v-model="AObject[item.prop]"></el-input>
          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='rstate' " v-model="AObject[item.prop]" >
            <el-option  :value="1" label="启用" ></el-option>
            <el-option  :value="2" label="禁用" ></el-option>
          </el-select>
        </el-form-item>
        <div style="text-align: center" >
          <el-transfer

              style="text-align: left; display: inline-block"
              v-model="value"
              filterable
              :render-content="renderFunc"
              :titles="['未拥有权限', '已拥有权限']"
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
  name: "roleManger",
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
      tittle:"角色管理",
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
      roleFrom:{
        role:{},
        menus:{}
      },
     menuFrom:{
        rid:'',
        mpid:''
      },

      data: generateData(),
      value: [],
      renderFunc(h, option) {
        return <span>{ option.key } - { option.label }</span>;
      }
    };
  },
  methods:{
    ownPowers(r){
      this.menuFrom.rid=r.rid
      this.menuFrom.mpid=1
      this.$axios({
        url:'/exam/ownpowers',
        method:'POST',
        data:this.menuFrom
      }).then(res=>{
        const powers=new Array();
        for (let i = 0; i <this.data.length ; i++) {
          for (let j = 0; j <res.data.map.ownpowers.length; j++) {
                 if (this.data[i].mid==res.data.map.ownpowers[j].mid){
                    powers.push(this.data[i].key)
                 }
          }
        }
        this.value=powers
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
    initPower(){
      this.$axios({
        url:'/exam/allpowers',
        method:'POST',
      }).then(res=>{
        console.log(res.data)
        this.data=this.power(res.data.map.powers);
      }).catch(error=>console.log(error))
    },
    power(datas){
      const  data=[]
      for (let i =1; i <=datas.length ; i++) {
          data.push(
              {
                key:i,
                mid:datas[i-1].mid,
                label:datas[i-1].mname,
                disabled:false
              }
          )
      }
      return data;
    },
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
      console.log(this.data)
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

    rstateRule(row,index){
      if (row!=null) {
        if (row.rstate == 1) {
          return '启动'
        }
        if (row.rstate == 2) {
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
      this.value=[];
      this.isEdit=false;
      this.option="添加";
      this.AObject = {};
      this.isDialogShow = true;
      for (let i = 0; i <this.data.length ; i++) {
        this.data[i].disabled=false
      }
    },
    showHandler:function (r) {
      this.isuporadd=2;
      this.isEdit=true
      this.option="确认"
      const role={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        role[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      console.log(role)
      this.AObject=role
      this.isDialogShow = true;
      this.ownPowers(r)
    },
    alterHandler:function (r) {
      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
      const role={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        role[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      console.log(role)
      this.AObject=role
      console.log(this.AObject)
      this.isDialogShow = true;
      this.ownPowers(r)
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
      this.roleFrom.role.rid=r.rid
      console.log(this.roleFrom)
      this.$axios.post("/exam/delRole",this.roleFrom.role)
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
      this.roleFrom.role=this.AObject
      if (this.isuporadd==1){
        const  datas=[]
        console.log("value"+this.value)
        for (let i = 0; i <this.value.length; i++) {
          console.log()
          if (this.data[this.value[i]-1]!=null) {
            datas.push({mid: this.data[this.value[i] - 1].mid})
          }
        }
        this.roleFrom.menus=datas
        this.$axios({
          url:"/exam/addRole",
          method:'POST',
          data:this.roleFrom
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.rid=res.data.map.role.rid
            const role={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              role[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(role);
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
        this.roleFrom.role.rmtime=this.getCurrentTime();
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.rid == this.tableData[i].rid) {
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
              datas.push({mid: this.data[this.value[i] - 1].mid})
            }
        }
        this.roleFrom.menus=datas
        console.log(this.roleFrom)
        this.$axios({
          url:"/exam/alterRole",
          method:'POST',
          data:this.roleFrom
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
      //     if (this.AObject.rid == this.tableData[i].rid) {
      //       for (let j = 0; j <this.tabCol.length ; j++) {
      //         this.tableData[i][this.tabCol[j].prop]=this.OldAObject[this.tabCol[j].prop]
      //       }
      //       console.log(this.tableData[i])
      //       break;
      //     }
      //   }
      // }
      this.isDialogShow = false;
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
    console.log("role")

    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal
    this.tabCol.forEach(r=>this.AObject[r.prop]="");
    this.initTable(1,10);
    this.initPower();
  },
  mounted() {
    this.getCurrentTime();
  }
}
</script>

<style scoped>

</style>