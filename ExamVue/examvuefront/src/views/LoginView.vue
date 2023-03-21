<template>
  <div class>
	<el-card class="box-card login">
		<div slot="header">
			<span>考试管理</span>
		</div>
		<div>

			<el-form :model="loginForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-tooltip class="item" effect="dark" content="(身份证号)" placement="top-start">
				<el-form-item label="账户名" prop="name">
					<el-input v-model="loginForm.examinee.ic" id="acc"></el-input>
				</el-form-item>
      </el-tooltip>
				<el-form-item label="密码" prop="password">
					<el-input type="password" id="pwd" v-model="loginForm.examinee.eepwd" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="验证码" prop="codenum">
					<el-input v-model="loginForm.code" id="code" ></el-input>
				</el-form-item>
				<el-form-item label="" prop="codeImg">
         <img  style="float: right" title="看不清？点击切换。"  id="changeServletImg" src="/api/exam/codeF" @click="changecode">
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="login()">登录</el-button>
          <el-button type="primary" @click="register()">注册</el-button>
				</el-form-item>
			</el-form>

		</div>
	</el-card>
    <div>
      <el-dialog ref="infoShow" :title="title" :visible.sync="isDialogShow">
        <el-form>

          <el-form-item :rules="rules" ref="ruleFrom">
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
            <el-form-item label="姓名" prop="password">
              <el-input  v-model="reFrom.examinee.eename"></el-input>
            </el-form-item>
            <el-tooltip class="item" effect="dark" content="(身份证号)" placement="top-start">
            <el-form-item label="账号" prop="password">
              <el-input  v-model="reFrom.examinee.ic"></el-input>
            </el-form-item>
            </el-tooltip>
            <el-form-item label="密码" prop="password">
              <el-input  v-model="reFrom.examinee.eepwd"></el-input>
            </el-form-item>
            <el-form-item label="重输密码" prop="password2">
              <el-input v-model="reFrom.eepwd2"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="codenum2">
              <el-input v-model="reFrom.code" id="code" ></el-input>
            </el-form-item>
            <el-form-item label="" prop="codeImg">
              <img  style="float: right" title="看不清？点击切换。"  id="changeServletImg2" src="/api/exam/codeR" @click="changecode2">
            </el-form-item>
          </el-form-item>
          <span>
                    <el-button type="primary" @click="saveHandler">确认</el-button>
                    <el-button type="primary" @click="cancelHandler">取消</el-button>
                </span>
        </el-form>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {

 
    data(){
          return{
              loginForm: {
                          examinee:{ ic:'350504200012121037' , eepwd:'7777' },
                          code:'',
              },
              reFrom:{
                examinee:{
                  eeid:"",
                  eename:"",
                  eepwd:"",

                },
                code:'',
                eepwd2:''
              },
			  url:'',
			  rules: {
				 examinee:{
           ic: [
					  { required: true, message: '请输入账户名(身份证号)', trigger: 'blur' },
					  { min: 3, max: 20, message: '长度在 18个字符', trigger: 'blur' }
				  ],
				  eepwd: [
					  { required: true, message: '请输入密码', trigger: 'blur' },
					  { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
				  ]},
				  code: [
					  { required: true, message: '请输入验证码', trigger: 'blur' },
				  ],
			  },
			  loginStatus:this.$store.state.loginFlag,
			  userInfos: this.$store.state.userInfos,
			  userInfo:"",
            title:"考生注册",
            fileList: [],
            fileItems:[],
            isDialogShow: false,
          }
        },
		
        methods:{
          ...mapActions(['asynSaveImgUrl']),
			    ...mapActions(['asynLoginFlag']),
          ...mapActions(['asynExaminee']),
          ...mapActions(['asynRole']),
          ...mapActions(['asynSaveMenuh']),
          ...mapActions(['asynSaveMenus']),
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
            console.log("1"+this.fileItems.length)
          },
          //保存
          saveHandler:function () {
              this.$refs.upload.submit();
              let formData=new FormData();
              var examineeVo=JSON.stringify(this.reFrom);
              formData.append("examineeVo",examineeVo);
              if (this.fileItems.length!=0) {
                for (let i = 0; i < this.fileItems.length; i++) {
                  formData.append("file", this.fileItems[i]);
                }
                this.$axios.post("/exam/register",
                    formData,
                    {
                      headers: {
                        "Content-Type": "multipart/from-data;boundary=~~web"
                            + new Date().getTime()
                      }
                    })
                    .then(res => {
                          console.log(res.data)
                          if (res.data.id == 1) {
                            this.$message({
                              showClose: true,
                              center: true,
                              message: res.data.message,
                              type: 'success'
                            })
                          } else {
                            this.$message({
                              showClose: true,
                              center: true,
                              message: res.data.message,
                              type: 'error'
                            })
                          }

                          this.isDialogShow = false;
                        }
                    );
              }else {
                this.$message({
                  showClose: true,
                  center: true,
                  message:'未选择头像',
                  type: 'error'
                })
              }

            // this.$emit("event-notify","add",this.AObject);
          },
          //取消
          cancelHandler:function () {
            this.isDialogShow = false;

          },
          changecode:function(){
            document.getElementById("changeServletImg").setAttribute("src","/api/exam/codeF?abc="+Math.random());
          },
          changecode2:function(){
            document.getElementById("changeServletImg2").setAttribute("src","/api/exam/codeR?abc="+Math.random());
          },
          register(){
            this.fileItems= undefined;
            this.fileItems=new Array()
            this.fileList=undefined;
            this.fileList=new Array()
            this.reFrom.examinee={}
            this.reFrom.code=""
            this.reFrom.eepwd2=""
            this.isDialogShow=true


          },
            login(){
				this.$axios({
					url: '/exam/login',
					method:'POST',
					data: this.loginForm,
				
				}).then(res=>{
					console.log(res.data);
					if (this.loginForm.ic === "" && this.loginForm.eepwd === "" && this.loginForm.code === "") {
						this.$message({
							showClose: true,
							center: true,
							message: '账号密码验证码不能为空！',
							type: 'error'
						});
					}else if(res.data.id == 1){
            this.asynLoginFlag(true)
            this.asynExaminee(res.data.map.examinee)
            this.asynRole(res.data.map.role)
            this.asynSaveMenuh(res.data.map.menuh)
            this.asynSaveMenus(res.data.map.menus)
            sessionStorage.setItem("token",res.data.token);
						this.$message({
							showClose: true,
							center: true,
							message: res.data.message,
							type: 'success'
						});
						// this.$store.dispatch('saveFlagAction', true);
						// sessionStorage.setItem("username", this.loginForm.adminName);

						// console.log("token:"+res.data.token);
						this.$router.push("/menu");
					} else if (res.data.id === -1) {
						this.$message({
							showClose: true,
							center: true,
							message: res.data.message,
							type: 'error'
						});
					} else if (res.data.id === 0) {
						this.$message({
							showClose: true,
							center: true,
							message: res.data.message,
							type: 'error'
						});
					}
				}).catch(error => console.log(error)).finally(
        );
			
			},
        },
		created(){
		}
}

</script>

<style>
html,
body {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
}

/* body {
	background-color: aqua;
	background-image: url(../assets/backimg.jpg);
	background-repeat: no-repeat;
	background-size: 100% 100%;
	background-attachment: fixed;
	background-position: center;
	overflow: hidden;
} */

.login {
	width: 400px;
	height: 400px;
	margin: 150px auto;
	/* border: 1px solid yellowgreen; */
	border-radius: 15px;
}

.place {
	margin-top: 5px;
}

.img {
	width: 100%;
	height: 40px;
}
</style>