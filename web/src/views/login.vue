<template>
	<el-card class="box-card loginpanel">
		<div slot="header" class="clearfix">
			<span>
				登录</span>

		</div>
		<div>
			<el-form ref="loginform" hide-required-asterisk :rules="rules" :model="loginform" label-width="80px">
				<el-form-item label="用户名" prop="uname">
					<el-input v-model="loginform.uname" clearable suffix-icon="el-icon-user"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="upwd">
					<el-input v-model="loginform.upwd" show-password suffix-icon="el-icon-goods"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitForm()">登录</el-button>
					<el-button @click="resetForm()">重置</el-button>
				</el-form-item>
			</el-form>
		</div>
	</el-card>
</template>

<script>
	export default {
		data() {
			return {
				loginform: {
					uname: "",
					upwd: ""
				},
				rules: {
					upwd: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 8,
							message: '长度在 3 到 8 个字符',
							trigger: 'blur'
						}
					]
				}
			}
		},
		methods: {
			resetForm() {
				this.$refs.loginform.resetFields();
			},
			submitForm() {
				this.$refs.loginform.validate((valid) => {
					if (valid) {
						axios.post("/loginByPwd", this.loginform)
						.then(resp => {
							let code = resp.code;
							if(code==20000){
								let loginuser = resp.data;
								//保存客户端:cookie,localstorage,sessionstorage
								sessionStorage.setItem("loginuser",JSON.stringify(loginuser));
								this.$router.push("/main");
							}else{
								this.error(resp.msg);
							}
						})
					} else {
						return false;
					}
				});
			}
		}
	}
</script>

<style>
	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}

	.clearfix:after {
		clear: both
	}

	.box-card {
		width: 480px;
	}

	div.loginpanel {
		margin: 200px auto;
	}
</style>
