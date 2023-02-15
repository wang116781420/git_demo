<template>
	<div>
		<el-row>
			<el-col :span="4">
				<el-input v-model="dname" placeholder="请输入部门名" @input="getDeptTree" style="width: 200px;"></el-input>
				<br>
				<br>
				<el-tree :data="deptTree" :props="defaultProps" :default-expand-all="true" @node-click="clickDeptNode">
				</el-tree>
			</el-col>
			<el-col :span="20" >
				
				<el-form :inline="true" :model="queryForm" class="demo-form-inline"  >
					<el-form-item label="用户名">
						<el-input v-model="queryForm.uname" placeholder="用户名" style="width: 100px"></el-input>
					</el-form-item>
					<el-form-item label="手机号">
						<el-input v-model="queryForm.uphone" placeholder="手机号" style="width: 200px"></el-input>
					</el-form-item>
					<el-form-item label="状态">
						<el-select v-model="queryForm.flag" placeholder="选择状态" style="width: 150px">
							<el-option v-for="item in dataStatus" :key="item.itemValue" :label="item.itemLabel"
								:value="item.itemValue">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="创建时间" style="width: 500px">
						<el-date-picker v-model="beginAndEnd" type="datetimerange" range-separator="至"
							start-placeholder="开始日期" end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss">
						</el-date-picker>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="doQuery">查询</el-button>
					</el-form-item>
				</el-form>

				<div>
					<el-button type="primary" plain @click="showAndDialog">添加</el-button>
					<el-button type="primary" plain @click="doDel">删除</el-button>
					<el-button type="success" plain>导入</el-button>
					<el-button type="success" plain>导出</el-button>
				</div>

				<div>
					<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
						@selection-change="handleSelectionChange">
						<el-table-column type="selection" width="55"></el-table-column>
						<el-table-column prop="uname" label="用户名" width="120"></el-table-column>
						<el-table-column prop="uphone" label="手机号" width="120"></el-table-column>
						<el-table-column prop="uwechat" label="微信" width="120"></el-table-column>
						<el-table-column prop="usex" label="性别" width="120" :formatter="formatSex"></el-table-column>
						<el-table-column prop="uemail" label="邮箱" width="120"></el-table-column>
						<el-table-column prop="userDept.dname" label="部门"></el-table-column>
						<el-table-column label="操作">
							<template slot-scope="scope">
								<el-button size="mini" @click="showEditDialog(scope.$index, scope.row)">编辑</el-button>
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page="queryForm.pageNum" :page-sizes="[2, 4, 10, 20]" :page-size="queryForm.pageSize"
						layout="total, sizes, prev, pager, next, jumper" :total="total">
					</el-pagination>
				</div>
			</el-col>
		</el-row>

		<!-- 添加,修改dialog -->
		<el-dialog :title="addOrEditTitle" :visible.sync="addOrEditDialogVisible" width="50%"
			:before-close="handleClose">

			<el-form ref="dataForm" :model="dataForm" label-width="80px">
				<el-row>
					<el-col :span="12">
						<el-form-item label="用户名">
							<el-input v-model="dataForm.uname"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="部门">
							<treeselect v-model="dataForm.deptId" :multiple="false" :options="deptTree"
								:normalizer="normalizer" placeholder="选择部门" />
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="密码">
							<el-input v-model="dataForm.upwd"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="手机号">
							<el-input v-model="dataForm.uphone"></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="微信">
							<el-input v-model="dataForm.uwechat"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="邮箱">
							<el-input v-model="dataForm.uemail"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="性别">
							<el-select v-model="dataForm.usex" placeholder="选择状态">
								<el-option v-for="item in usersex" :key="item.itemValue" :label="item.itemLabel"
									:value="item.itemValue">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="状态">
							<template>
								<el-radio v-for="item in dataStatus" :key="parseInt(item.itemValue)"
									:label="parseInt(item.itemValue)" v-model="dataForm.flag">{{item.itemLabel}}
								</el-radio>
							</template>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="岗位">
							<el-select v-model="dataForm.posId" placeholder="选择状态">
								<el-option v-for="item in posList" :key="item.id" :label="item.posName"
									:value="item.id">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="角色">
							<el-select v-model="dataForm.roleId" placeholder="选择角色">
								<el-option v-for="item in roleList" :key="item.id" :label="item.roleName"
									:value="item.id">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>

				<el-form-item>
					<el-button type="primary" @click="doSubmit">确定</el-button>
					<el-button @click="addOrEditDialogVisible = false">取消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

	</div>
</template>

<script>
	import Treeselect from '@riophae/vue-treeselect'
	import '@riophae/vue-treeselect/dist/vue-treeselect.css'

	export default {
		components: {
			Treeselect
		},
		data() {
			return {
				dname: '',
				beginAndEnd: [],
				queryForm: {
					uname: '',
					uphone: '',
					flag: '',
					beginTime: '',
					endTime: '',
					deptId: '',
					pageNum: 1,
					pageSize: 2
				},
				dataForm: {
					id:'',
					uname: '',
					upwd: '',
					uphone: '',
					uwechat: '',
					uemail: '',
					usex: '',
					flag: 0,
					deptId: null,
					roleId: '',
					posId: '',
				},
				total: undefined,
				deptTree: [],
				tableData: [],
				multipleSelection: [],
				usersex: [],
				dataStatus: [],
				posList: [],
				roleList: [],
				addOrEditTitle: '',
				addOrEditDialogVisible: false,
				defaultProps: {
					children: 'childs',
					label: 'dname'
				},
				normalizer(node) {
					return {
						id: node.id,
						label: node.dname,
						children: node.childs,
					}
				},
			}
		},
		methods: {
			doQuery() {
				this.queryForm.deptId = null;
				this.getUsers();
			},
			clickDeptNode(data) {
				this.queryForm.deptId = data.id;
				this.getUsers();
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val;
				this.getUsers();
			},
			handleCurrentChange(val) {
				this.queryForm.pageNum = val;
				this.getUsers();
			},
			getDeptTree() {
				axios.get(`dept/tree?dname=${this.dname}`).then(resp => {
					this.deptTree = resp.data;
				})
			},
			getUsers() {
				if (this.beginAndEnd.length > 0) {
					this.queryForm.beginTime = this.beginAndEnd[0];
					this.queryForm.endTime = this.beginAndEnd[1];
				}

				axios.get('user', {
					params: this.queryForm
				}).then(resp => {
					this.total = resp.data.total;
					this.queryForm.pageNum = resp.data.pageNum;
					this.queryForm.pageSize = resp.data.pageSize;
					this.tableData = resp.data.list;
				})
			},
			formatSex(row, column, cellValue, index) {
				let item = this.getItemLabel(this.usersex, cellValue);
				if (item == null || item == undefined) return "--";
				return item.itemLabel;
			},
			showAndDialog() {
				this.resetDataForm();
				this.addOrEditTitle = "添加用户";
				this.addOrEditDialogVisible = true;
			},
			showEditDialog(index, row) {
				this.resetDataForm();
				this.dataForm = JSON.parse(JSON.stringify(row));
				this.addOrEditTitle = "编辑用户";
				this.addOrEditDialogVisible = true;
			},
			doSubmit() {
				if(this.dataForm.id==null ||this.dataForm.id==''){
					this.addUser();
				}else{
					this.editUser();
				}
			},
			editUser() {
				axios.put('user', this.dataForm).then(resp => {
					if (resp.code == 20000) {
						this.suc('修改成功');
						this.getUsers();
						this.addOrEditDialogVisible = false;
						this.resetDataForm();
					} else {
						this.error("修改失败");
					}
				})
			},
			addUser() {
				axios.post('user', this.dataForm).then(resp => {
					if (resp.code == 20000) {
						this.suc('添加成功');
						this.getUsers();
						this.addOrEditDialogVisible = false;
						this.resetDataForm();
					} else {
						this.error("添加失败");
					}
				})
			},
			doDel() {
				if (this.multipleSelection.length == 0) {
					this.error("请选中一行进行删除");
					return;
				}
				let ids = [];
				this.multipleSelection.forEach((d, i) => {
					ids.push(d.id);
				})

				axios.delete('user/' + ids.join(',')).then(resp => {
					if (resp.code == 20000) {
						this.suc("删除成功");
						this.getUsers();
					}
				})
			},
			resetDataForm() {
				this.dataForm = {
					id:'',
					uname: '',
					upwd: '',
					uphone: '',
					uwechat: '',
					uemail: '',
					usex: '',
					flag: 0,
					deptId: '',
					roleId: '',
					posId: '',
				};
			},
			getPoslist() {
				axios.get('pos').then(resp => {
					this.posList = resp.data;
				})
			},
			getroleList() {
				axios.get('role').then(resp => {
					this.roleList = resp.data;
				})
			}
		},
		created() {
			this.getDictItems("user_sex").then(resp => {
				console.log("**********");
				this.usersex = resp.data;
			});
			this.getDictItems("data_status").then(resp => {
				this.dataStatus = resp.data;
			})
			this.getDeptTree();
			this.getUsers();
			this.getPoslist();
			this.getroleList();
		},

	}
</script>

<style>
</style>
