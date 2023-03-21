<template>
  <div>
    <el-descriptions class="margin-top" title="个人信息页面" :column="3" :size="size" border>
      <template slot="extra">
        <el-button type="primary" size="small" @click="alterData">修改个人信息</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{alterFrom.admin.aacc}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        18859400283
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
          <i class="el-icon-tickets"></i>
          身份
        </template>
        <el-tag size="small">{{role.rname}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          联系地址
        </template>
       福州至诚学院
      </el-descriptions-item>
    </el-descriptions>
    <div v-if="alterFrom.admin.rid!=2">
      <h1>{{title3}}</h1>
      <el-tooltip class="item" effect="dark" content="考官默认密码:666666" placement="top-start">
      <el-button type="primary" @click="addHandler">新建</el-button>
      </el-tooltip>
      <el-table
          :data="tableData"
          style="width: 100%"
          max-height="500">
        <el-table-column v-for="(item,index) in tabCol"
                         fixed
                         :prop="item.prop"
                         :label="item.desc"
                         :key="index"
                         width="150"  v-if="item.prop!='astate' && item.prop!='rid' && item.prop!='apwd'">
        </el-table-column >
        <el-table-column v-for="(item,index) in tabCol"
                         fixed
                         :prop="item.prop"
                         :label="item.desc"
                         :key="index"
                         :formatter="ridRule"
                         width="150" v-if="item.prop=='rid'">
        </el-table-column>
        <el-table-column v-for="(item,index) in tabCol"
                         fixed
                         :prop="item.prop"
                         :label="item.desc"
                         :key="index"
                         :formatter="astateRule"
                         width="150" v-if="item.prop=='astate'">
        </el-table-column >

        <el-table-column
            fixed="right"
            label="操作"
            width="120"
        >
          <template slot-scope="rowdata" >
            <el-button v-if="rowdata.row!=null" type="primary" @click="showHandler(rowdata.row)">查看个人信息</el-button>
            <el-button v-if="rowdata.row!=null" type="primary" @click="alterHandler(rowdata.row)">更改个人信息</el-button>
            <el-button v-if="rowdata.row!=null" type="primary" @click="delHandler(rowdata.row)">删除考官</el-button>
            <el-button v-if="rowdata.row==null" type="primary" @click="addHandler">新建考官</el-button>
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
    </div>

    <!--        弹窗-->
    <el-dialog ref="infoShow" :title="title" :visible.sync="isDialogShow">
      <el-form>
        <el-form-item :rules="rules" ref="ruleFrom">
          <el-form-item label="密码" prop="password">
          <el-input  v-model="alterFrom.admin.apwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="password2">
          <el-input v-model="alterFrom.apwd2"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="codenum">
            <el-input v-model="alterFrom.code" id="code" ></el-input>
          </el-form-item>
          <el-form-item label="" prop="codeImg">
            <img  style="float: right" title="看不清？点击切换。"  id="changeServletImg" src="/api/exam/code" @click="changecode">
          </el-form-item>
        </el-form-item>
              <span>
                    <el-button type="primary" @click="saveHandler">确认</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
      </el-form>
    </el-dialog>
    <el-dialog ref="infoShow" :visible.sync="isDialogShow2">
      <el-form>
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"  v-if="item.prop!=='aid'&&item.prop!='apwd'">
          <el-input  :disabled="isEdit" v-if="item.type==='input'&& item.prop!=='apwd'||item.prop!='apwd'&&isuporadd!=3&&item.prop!='rid'&&item.prop!='astate' " v-model="AObject[item.prop]"></el-input>

          <el-select  :disabled="isEdit" v-if="item.type==='select' && item.prop=='astate' " v-model="AObject[item.prop]" >
            <el-option  :value="1" label="启用" ></el-option>
            <el-option  :value="2" label="禁用" ></el-option>
          </el-select>
          <el-select  v-if="item.type==='select' && item.prop=='rid' " v-model="AObject[item.prop]">
            <el-option  :value="rid" :label="rname"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item   v-for="(item,index) in tabCol" :label="item.desc" :key="index"v-if="item.prop=='apwd'&& isuporadd==3">
          <el-button type="primary" @click="reclick()" >重置</el-button>
        </el-form-item>

        <span>
                    <el-button type="primary" @click="saveHandler2">{{option}}</el-button>
                    <el-button type="primary" @click="cancelHandler2">取消</el-button>
                </span>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {mapActions} from "vuex";
export default {

  data() {
    return {
      alterFrom:{
        admin:{},
        apwd2:'',
        code:''
      },
      role:{},
      AObject:[],
      OldAObject:[],
      tabCol:this.$store.getters.getTabCol,
      innerData:[],
      innerTotal:this.$store.getters.getTabTotal,
      innerRoles:[],
      isuporadd:1,
      currentPage:1,
      option:'保护',
      limit:10,
      tableData:[],
      isEdit:false,
      title:"个人信息修改",
      title2:"新建考官",
      title3:"考官列表",
      size: '',
      isDialogShow: false,
      isDialogShow2:false,
      adminFrom:{
        admin:{}
      },
      rid:2,
      rname:"考官",
      rules:{
        admin:{
          aacc: [
            { required: true, message: '请输入账户名', trigger: 'blur' },
            { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
          ],
          apwd: [
            { required: true, message: '请输入原密码', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          },

        code:[
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
        apwd2: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    console.log("first")
   this.alterFrom.admin=this.$store.getters.getAdmin
    this.role=this.$store.getters.getRole
    this.tabCol=this.$store.getters.getTabCol
    this.innerData = this.$store.getters.getTabData
    console.log(this.innerData)
    this.innerTotal=this.$store.getters.getTabTotal

    this.tabCol.forEach(r=>this.AObject[r.prop]="");
    this.initRoles();
    this.initTable(1,10);
  },

  mounted() {
    this.getCurrentTime();
  },

  methods: {

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
    reclick(){
      this.adminFrom.admin=this.AObject
      let aid=this.adminFrom.admin.aid
      console.log("admin"+this.adminFrom.admin)
      this.adminFrom.admin={}
      this.adminFrom.admin.aid=aid
      this.adminFrom.admin.amtime=this.getCurrentTime();
      this.adminFrom.admin.apwd=666666
      this.$axios({
            url: '/exam/alterAdmins',
            method:'POST',
            data:this.adminFrom
          }
      ).then(res=>{
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
    ,
    initRoles()
    {
      this.$axios({
        url:"/exam/roles",
        method:'POST',
      }).then(res=>{
        console.log(res.data);
        if (res.data.id==1){

          this.innerRoles=res.data.map.roles
        }
      }).catch(error=>console.log(error));

    },
    astateRule(row,index){
      if (row!=null) {
        if (row.astate == 1) {
          return '启动'
        }
        if (row.astate == 2) {
          return '禁用'
        }

      }
    },
    ridRule(row,index){
      if (row!=null) {
          if ("2"== row.rid) {
            return "考官"
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
      this.isEdit=false
      this.option="添加";
      this.AObject = {};
      this.AObject.rid=2
      this.isDialogShow2 = true;
    },
    showHandler:function (r) {
      this.isuporadd=2;
      this.isEdit=true
      this.option="确认"
      const admin={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        admin[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      console.log(admin)
      this.AObject=admin
      this.isDialogShow2 = true;
    },
    alterHandler:function (r) {
      this.isuporadd=3;
      this.isEdit=false
      this.option="确认",
          this.tabCol.forEach(item=>this.OldAObject[item.prop]=r[item.prop]);
      const admin={}
      for (let i = 0; i <this.tabCol.length ; i++) {
        admin[this.tabCol[i].prop]=r[this.tabCol[i].prop]
      }
      this.AObject=admin
      console.log(this.AObject)
      this.isDialogShow2 = true;
    },

    delHandler:function (r) {
      console.log(r.aid);
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
      this.adminFrom.admin.aid=r.aid
      console.log(this.adminFrom)
      this.$axios.post("/exam/delAdmin",this.adminFrom)
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
    saveHandler2:function () {

      console.log(this.AObject);
      this.adminFrom.admin=this.AObject
      if (this.isuporadd==1){
        this.adminFrom.admin.apwd=666666
        this.$axios({
          url:"/exam/addAdmin",
          method:'POST',
          data:this.adminFrom
        }).then(res=>{
          console.log(res.data);
          if (res.data.id==1){
            this.AObject.aid=res.data.map.admin.aid
            const admin={}
            for (let i = 0; i <this.tabCol.length ; i++) {
              admin[this.tabCol[i].prop]=this.AObject[this.tabCol[i].prop]
            }
            this.innerData.push(admin);
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
        this.adminFrom.admin.amtime=this.getCurrentTime();
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.AObject.aid == this.tableData[i].aid) {
            for (let j = 0; j <this.tabCol.length ; j++) {
              this.tableData[i][this.tabCol[j].prop]=this.AObject[this.tabCol[j].prop]
            }
            console.log(this.tableData[i])
            break;
          }
        }
        this.adminFrom.admin.apwd=null
        this.$axios({
          url:"/exam/alterAdmins",
          method:'POST',
          data:this.adminFrom
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
      this.isDialogShow2= false;
    },
    //取消
    cancelHandler2:function () {
      this.isDialogShow2 = false;
    },
    initTable(page,limit){
      this.tableData= new Array();
      let now=page*limit-limit;
      for (let i =now; i <now+limit; i++) {
        this.tableData.push(this.innerData[i])
      }
      console.log(this.tableData)
    },
    ...mapActions(['asynAdmin']),
    alterData(){
          this.isDialogShow=true;
          this.alterFrom.admin.apwd='';
          this.alterFrom.apwd2='';
          this.alterFrom.code='';
            this.changecode()
    },
    saveHandler(){
      this.$axios({
        url: '/exam/alterAdmin',
        method:'POST',
        data:this.alterFrom
          }
      ).then(res=>{
        console.log(res.data);
        if (res.data.id==1){
          this.$message({
            showClose:true,
            center:true,
            message:res.data.message,
            type:'success'
          })
          this.alterFrom.admin=res.data.map.admin
          this.asynAdmin(res.data.map.admin)
          this.alterFrom.apwd2=''
          this.isDialogShow = false;
        }else if (res.data.id==0){
          this.$message({
            showClose:true,
            center:true,
            message:res.data.message,
            type:'error'
          });
        }
      })
    },
    cancelHandler(){
      this.isDialogShow = false;
    },
    changecode:function(){
      document.getElementById("changeServletImg").setAttribute("src","/api/exam/code?abc="+Math.random());
    },

  },
}
</script>
<style>

</style>