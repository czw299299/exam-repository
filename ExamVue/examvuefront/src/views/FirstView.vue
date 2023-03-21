<template>
  <div>
    <el-descriptions class="margin-top" title="个人信息页面" :column="3" :size="size" border>
      <template slot="extra">
        <el-button type="primary" size="small" @click="alterData">修改个人信息</el-button>
        <el-button type="primary" size="small" @click="alterHeader">修改头像</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          头像
        </template>
        <el-image
            style="width:100px; height: 100px"
            :src="imgUrl+alterFrom.examinee.eeimg">
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
        {{alterFrom.examinee.eename}}
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
    <!--        弹窗-->
    <el-dialog ref="infoShow" :title="title" :visible.sync="isDialogShow">
      <el-form>
        <el-form-item :rules="rules" ref="ruleFrom">
          <el-form-item label="姓名" prop="password">
            <el-input  v-model="alterFrom.examinee.eename"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
          <el-input  v-model="alterFrom.examinee.eepwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="password2">
          <el-input v-model="alterFrom.eepwd2"></el-input>
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
    <el-dialog ref="infoShow" :title="title2" :visible.sync="isDialogShow2">
      <el-form>
      <el-form-item label="头像" prop="img">
        <el-upload
            class="upload-demo"
            ref="upload"
            action="/api/login/upload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-change="handleChange"
            :on-success="handleSuccess"
            :http-request="handleRequest"
            :file-list="fileList"
            :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary" >选取商品图片</el-button>
          <!--                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
          <div slot="tip" class="el-upload__tip" >只能上传jpg/png文件，且不超过500kb,图片名大于3个字符</div>
        </el-upload>
      </el-form-item>
      <span>
                    <el-button type="primary" @click="submitUpload">确认</el-button>
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
        examinee:{},
        eepwd2:'',
        code:''
      },
      role:{},
      AObject:[],
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
      title2:"修改头像",
      size: '',
      eename:'',
      isDialogShow: false,
      isDialogShow2: false,
      imgUrl:'',
      fileItems:[],
      fileList: [],
      rules:{
        examinee:{
          ic: [
            { required: true, message: '请输入账户名', trigger: 'blur' },
            { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
          ],
          eepwd: [
            { required: true, message: '请输入原密码', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          },

        code:[
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
        eepwd2: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    console.log("first")
   this.alterFrom.examinee=this.$store.getters.getExaminee
    this.role=this.$store.getters.getRole
    this.imgUrl=this.$store.getters.getImgUrl
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
    alterHeader(){
      this.fileItems= undefined;
      this.fileItems=new Array()
      this.fileList=undefined;
      this.fileList=new Array()
      this.isDialogShow2=true;
    },
    alterData(){
          this.isDialogShow=true;
          this.eename=this.alterFrom.examinee.eename
          this.alterFrom.examinee.eepwd='';
          this.alterFrom.eepwd2='';
          this.alterFrom.code='';
            this.changecode()
    },
    submitUpload() {
      this.$refs.upload.submit();
      let formData=new FormData();
      formData.append("eeid",this.alterFrom.examinee.eeid+"");
      for (let i = 0; i <this.fileItems.length ; i++) {
        if (this.fileItems[i]!=null){
          formData.append("file",this.fileItems[i]);
        }

      }
      this.$axios.post("/exam/upload",
          formData,
          {headers:{"Content-Type":"multipart/from-data;boundary=~~web"
                  +new Date().getTime()
            }})
          .then(res=>{
            console.log(res.data)
            if (res.data.id==1){
              this.alterFrom.examinee.eeimg=res.data.map.eeimg
              this.isDialogShow2=false
            }

          }).catch(err=>{
        this.$message({
          showClose: true,
          center: true,
          message: '所选图片错误！',
          type: 'error'
        })
      });
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
    handleRequest:function (file) {
      this.fileItems.push(file.file);
    },
    cancelHandler2(){

      this.isDialogShow2 = false;
    },
    saveHandler(){
      this.$axios({
        url: '/exam/alterExaminee',
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
          this.alterFrom.examinee.eeimg=res.data.map.eeimg
          this.asynExaminee(res.data.map.examinee)
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
      this.alterFrom.examinee.eename=this.eename
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