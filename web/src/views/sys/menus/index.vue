
<template>
	<div>
		<el-form :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="菜单名称">
				<el-input v-model="queryForm.menuName" placeholder="菜单名称"></el-input>
			</el-form-item>
			<el-form-item label="菜单状态">
				<el-select v-model="queryForm.flag" placeholder="选择状态" style="width: 150px">
					<el-option v-for="item in dataStatus" :key="item.itemValue" :label="item.itemLabel"
						:value="item.itemValue">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="doQurey">查询</el-button>
			</el-form-item>

			<el-form-item>
				<el-button @click="resetForm">重置</el-button>
			</el-form-item>
		</el-form>

		<div>
			<el-button type="primary" plain @click="showAndDialog">添加</el-button>
		</div>
		<el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id" border
			:default-expand-all=expandStatus :tree-props="{children: 'childMenus', 
		hasChildren: 'hasChildren'}" highlight-current-row>
			</el-table-column>
			<el-table-column prop="menuName" label="菜单名称" width="180">
				<template slot-scope="scope">
					<i :class="scope.row.menuIcon"></i>
					{{scope.row.menuName}}
				</template>
			</el-table-column>
			<el-table-column prop="orders" label="排序" sortable width="180">
			</el-table-column>
			<el-table-column prop="menuUrl" label="请求地址" width="180">
			</el-table-column>
			<el-table-column prop="flag" label="可见" width="120" :formatter="formatStatus">
			</el-table-column>
			<el-table-column prop="type" label="类型" width="180" :formatter="formatType">
			</el-table-column>
			<el-table-column label="操作" width="300">
				<template slot-scope="scope">
					<el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>


		<!-- 查询禁用数据 -->
		<el-dialog title="禁用菜单" :visible.sync="centerDialogVisible" width="60%" center>
			<span>
				<el-table :data="gridData" style="width: 100%;margin-bottom: 20px;" row-key="id" border
					highlight-current-row>
					<el-table-column prop="menuName" label="菜单名称" width="180">
					</el-table-column>
					<el-table-column prop="" label="排序" sortable width="180">
					</el-table-column>
					<el-table-column prop="menuUrl" label="请求地址" width="180">
					</el-table-column>
					<el-table-column prop="flag" label="可见" width="120" :formatter="formatStatus">
					</el-table-column>
					<el-table-column prop="type" label="类型" width="180" :formatter="formatType">
					</el-table-column>
					<el-table-column label="操作" width="300">
						<template slot-scope="scope">
							<el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑
							</el-button>
							<el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>

			</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="centerDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
			</span>
		</el-dialog>




		<!-- 添加,修改dialog -->
		<el-dialog :title="addOrEditTitle" :visible.sync="addOrEditDialogVisible" width="50%"
			:before-close="handleClose">

			<el-form ref="dataForm" :model="dataForm" label-width="80px">

				<el-form-item label="上级菜单">
					<treeselect v-model="dataForm.pid" :multiple="false" :options="menusTree" :normalizer="normalizer"
						placeholder="选择菜单" />
				</el-form-item>

				<el-form-item label="菜单类型">
					<el-select v-model="dataForm.type" placeholder="菜单类型">
						<el-option v-for="item in menuType" :key="item.itemValue" :label="item.itemLabel"
							:value="item.itemValue">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="菜单名称">
					<el-input v-model="dataForm.menuName"></el-input>
				</el-form-item>
				<el-form-item label="排序">
					<el-input v-model="dataForm.orders"></el-input>
				</el-form-item>
				<el-form-item label="菜单图标">
					<e-icon-picker v-model="dataForm.menuIcon" :options="options"/>
					<e-icon :icon-name="icon" />
				</el-form-item>

				<el-form-item label="请求地址">
					<el-input v-model="dataForm.menuUrl"></el-input>
				</el-form-item>

				<el-form-item label="菜单状态">
					<template>
						<el-radio v-for="item in dataStatus" :key="parseInt(item.itemValue)"
							:label="parseInt(item.itemValue)" v-model="dataForm.flag">{{item.itemLabel}}
						</el-radio>
					</template>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" @click="doSubmit">确定</el-button>
					<el-button type="danger" @click="noSubmit">取消</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>

	</div>

</template>

<script>
	import Treeselect from '@riophae/vue-treeselect'
	import '@riophae/vue-treeselect/dist/vue-treeselect.css'
	
	import {EIconPicker} from 'e-icon-picker';
	
	export default {
		  name: 'app',
		components: {
			EIconPicker,

			Treeselect
		},
		data() {
			return {
				icon: '',
				options: {
				  FontAwesome: false,
				  ElementUI: false,
				  eIcon: true,//自带的图标，来自阿里妈妈
				  eIconSymbol: true,//是否开启彩色图标
				  addIconList: [],
				  removeIconList: []
				            },
				iconData: [],
				centerDialogVisible: false,
				menuType: [],
				menusTree: [],
				tableData: [],
				gridData: [],
				dataForm: {
					menuName: '',
					type: '',
					menuUrl: '',
					flag: '',
					pid: '',
					menuIcon:'',
					orders:''
				},

				addOrEditTitle: '',
				addOrEditDialogVisible: false,
				dialogFormVisible: false,
				dataStatus: [],
				expandStatus: false,

				queryForm: {
					menuName: '',
					flag: '',
				},
				normalizer(node) {
					return {
						id: node.id,
						label: node.menuName,
						children: node.childMenus,
					}
				},
				handleClose(done) {
					this.$confirm('确认关闭？')
						.then(_ => {
							done();
						})
						.catch(_ => {});
				},
			}
		},
		methods: {
			getMenusTree() {
				axios.get(menus / tree).then(resp => {
					this.menusTree = resp.data.menus;
					console.log(menusTree);
				})
			},
			doSubmit() {
				if (this.dataForm.id == null || this.dataForm.id == '') {
					this.addMenus();
				} else {
					this.editMenus();
				}
			},
			noSubmit() {
				this.addOrEditDialogVisible = false;

			},
			formatStatus(row, column, cellValue, index) {
				let item = this.getItemLabel(this.dataStatus, cellValue);
				if (item == null || item == undefined) return "--";
				return item.itemLabel;
			},

			formatType(row, column, cellValue, index) {
				let item = this.getItemLabel(this.menuType, cellValue);
				if (item == null || item == undefined) return "--";
				return item.itemLabel;
			},
			handleEdit(index, row) {
				this.resetDataForm();
				this.dataForm = JSON.parse(JSON.stringify(row));
				this.addOrEditTitle = "编辑用户";
				this.addOrEditDialogVisible = true;

			},
			handleDelete(index, row) {
				console.log(row);
				axios.delete("menus", {
						params: {
							"id": row.id
						}
					})
					.then(resp => {
						if (resp.code == 20000) {
							this.suc('删除成功');
							this.getMenus();
						} else {
							this.error("删除失败");
						}
					})
			},
			showAndDialog() {
				this.resetDataForm();
				this.addOrEditTitle = "添加菜单";
				this.addOrEditDialogVisible = true;
			},
			showEditDialog(index, row) {
				this.resetDataForm();
				this.dataForm = JSON.parse(JSON.stringify(row));
				this.addOrEditTitle = "编辑菜单";
				this.addOrEditDialogVisible = true;
			},
			doQurey() {
				if (this.queryForm.flag != 1) {
					axios.get("menus/tree", {
						params: this.queryForm
					}).then(resp => {
						this.tableData = resp.data.menus;
					})
				} else {
					this.centerDialogVisible = true,
						axios.get("menus", {
							params: this.queryForm
						}).then(resp => {
							this.gridData = resp.data;
						})
				}

			},
			resetForm() {
				axios.get("menus/tree").then(resp => {
					this.resetqueryForm();
					this.tableData = resp.data.menus;

				})
			},
			resetqueryForm() {
				this.queryForm = {
					menuName: '',
					flag: ''
				}
			},
			resetDataForm() {
				this.dataForm = {
					menuName: '',
					type: '',
					menuUrl: '',
					flag: 0,
					pid: ''
				}

			},
			editMenus() {
				axios.put('menus', this.dataForm).then(resp => {
					if (resp.code == 20000) {
						this.suc('修改成功');
						this.getMenus();
						this.addOrEditDialogVisible = false;
						this.resetDataForm();
					} else {
						this.error("修改失败");
					}
				})
			},
			addMenus() {
				if (this.dataForm.pid == '') this.dataForm.pid = -1;
		
				axios.post('menus', this.dataForm).then(resp => {
					if (resp.code == 20000) {
						this.suc('添加成功');
						this.getMenus();
						this.addOrEditDialogVisible = false;
						this.resetDataForm();
					} else {
						this.error("添加失败");
					}
				})
			},
			getMenus() {
				axios.get("menus/tree").then(resp => {
					this.tableData = resp.data.menus;
				})
			}
		},
		created() {
			this.getDictItems("data_status").then(resp => {
					this.dataStatus = resp.data;
				}),
				this.getDictItems("menu_type").then(resp => {
					this.menuType = resp.data;
				}),
				axios.get("menus/tree").then(resp => {
					this.tableData = resp.data.menus;
					this.menusTree = resp.data.menus;
				})
			
		}
	}
</script>


<style lang="css" scoped>
@import '~e-icon-picker/lib/index.css';
@import '~font-awesome/css/font-awesome.min.css';
@import '~element-ui/lib/theme-chalk/icon.css';
</style>