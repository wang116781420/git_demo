<template>
	<el-container>
		<el-header height="100px">
			<div style="float: left;margin-left: -20px;">
				<img src="../assets/logo2.jpg" />
			</div>
			<el-row type="flex" justify="end">
				<el-col :span="18" style="font-weight: 900;font-size: 30px;">CRM系统</el-col>
				<el-col :span="6">
					<el-button-group>
						<el-button type="primary">{{username}}</el-button>
						<el-button type="primary" icon="el-icon-s-grid"></el-button>
						<el-button type="primary" icon="el-icon-switch-button" @click="logout()"></el-button>
					</el-button-group>
				</el-col>
			</el-row>
		</el-header>
		<el-container>
			<el-aside width="245px">
				<el-row>
					<el-col :span="24">
						<el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen"
							@close="handleClose" router background-color="#545c64" text-color="#fff"
							active-text-color="#ffd04b">
							<el-submenu v-for="(menu,index) in menuList" :key="index" :index="menu.id">
								<template slot="title">
									<span>{{menu.menuName}}</span>
								</template>

								<el-menu-item v-for="(subm,index) in menu.childMenus" :key="subm.id" :index="subm.menuUrl">
									{{subm.menuName}}</el-menu-item>
							</el-submenu>
						</el-menu>
					</el-col>
				</el-row>
			</el-aside>
			<el-main>
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>


</template>

<script>
	export default {
		data() {
			return {
				menuList:[],
				username: ""
			}
		},
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			logout() {
				axios.get("/logout")
					.then(resp => {
						console.log(resp);
						if (resp.code == 20000) {
							this.$router.push("/login");
							sessionStorage.pagekey = "/main";
						}
					})
			}
		},
		created(){
			axios.get("menus/tree")
				.then(resp => {
					if (resp.code == 20000) {
						this.menuList = resp.data.menus;
						this.username =resp.data.loginUserName;
					}
			})
		}
	}
</script>

<style>
	
	#app {
		height: 100%;
	}

	.el-header,.el-footer {
		background-color: #B3C0D1;
		color: #333;
		text-align: center;
		line-height: 100px;
	}

	.el-aside {
		background-color: #D3DCE6;
		color: #333;
		line-height: 200px;
	}

	.el-main {
		color: #333;
		padding: 0px;
	}

	.el-container {
		height: 100%;
	}

	body>.el-container {
		margin-bottom: 40px;
	}

	.el-container:nth-child(5) .el-aside,
	.el-container:nth-child(6) .el-aside {
		line-height: 260px;
	}

	.el-container:nth-child(7) .el-aside {
		line-height: 320px;
	}
</style>
