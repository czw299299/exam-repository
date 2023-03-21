<template>
  <div class>
	<el-card class="box-card login">
		<div slot="header">
			<span>考试管理</span>
		</div>
		<div>
			<el-form :model="loginForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-tooltip class="item" effect="dark" content="输入账号6到16位" placement="top-start">
				<el-form-item label="账户名" prop="name">
					<el-input v-model="loginForm.admin.aacc" id="acc"></el-input>
				</el-form-item>
        </el-tooltip>
				<el-form-item label="密码" prop="password">
					<el-input type="password" id="pwd" v-model="loginForm.admin.apwd" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="验证码" prop="codenum">
					<el-input v-model="loginForm.code" id="code" ></el-input>
				</el-form-item>
				<el-form-item label="" prop="codeImg">
         <img  style="float: right" title="看不清？点击切换。"  id="changeServletImg" src="/api/exam/code" @click="changecode">
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="login()">登录</el-button>
				</el-form-item>
			</el-form>
		</div>
	</el-card>
       
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {

 
    data(){
          return{
              loginForm: {
          admin:{ aacc: '7777', apwd: '7777'},
				  code:'',
				  uuid:'',
              },
			  url:'',
			  rules: {
				 admin:{
           aacc: [
					  { required: true, message: '请输入账户名', trigger: 'blur' },
					  { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
				  ],
				  apwd: [
					  { required: true, message: '请输入密码', trigger: 'blur' },
					  { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
				  ]},
				  code: [
					  { required: true, message: '请输入验证码', trigger: 'blur' },
				  ],
			  },
			  loginStaus:this.$store.state.loginFlag,
			  userInfos: this.$store.state.userInfos,
			  userInfo:"",
          }
        },
		
        methods:{
			    ...mapActions(['asynLoginFlag']),
          ...mapActions(['asynAdmin']),
          ...mapActions(['asynRole']),
          ...mapActions(['asynSaveMenuh']),
          ...mapActions(['asynSaveMenus']),
          changecode:function(){
            document.getElementById("changeServletImg").setAttribute("src","/api/exam/code?abc="+Math.random());
          },
            login(){
				this.$axios({
					url: '/exam/login',
					method:'POST',
					data: this.loginForm,
				
				}).then(res=>{
					console.log(res.data);
					if (this.loginForm.aacc === "" && this.loginForm.apwd === "" && this.loginForm.code === "") {
						this.$message({
							showClose: true,
							center: true,
							message: '账号密码验证码不能为空！',
							type: 'error'
						});
					}else if(res.data.id == 1){
            this.asynLoginFlag(true)
            this.asynAdmin(res.data.map.admin)
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