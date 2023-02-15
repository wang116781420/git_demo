<template>
	<div>
		<!-- 上部查询 -->
		<el-form ref="queryForm" :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="部门名称" prop="dname">
				<el-input v-model="queryForm.dname" placeholder="无"></el-input>
			</el-form-item>
			<el-form-item label="部门状态" prop="status">
				<el-select v-model="queryForm.status" placeholder="部门状态">
					<el-option label="无" :value="''"></el-option>
					<el-option v-for="item in dataStatus" :key="item.itemValue" :label="item.itemLabel"
						:value="item.itemValue">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="doQuery">查询</el-button>
				<el-button type="success" @click="openAdd">添加</el-button>
				<el-button type="warning" @click="openEdit">修改</el-button>
				<el-button type="danger" @click="doDelete">删除</el-button>
			</el-form-item>
		</el-form>

		 <el-table
		    :data="deptTree"
		    style="width: 100%;margin-bottom: 20px;"
		    row-key="id"
			highlight-current-row @current-change="handleCurrentRowChange"
		    default-expand-all
		    :tree-props="{children: 'childs', hasChildren: 'hasChildren'}">
		    <el-table-column
		      prop="dname"
		      label="部门姓名"
		      width="300">
		    </el-table-column>
		    <el-table-column
		      prop="daddress"
		      label="地址"
			  width="300">
		    </el-table-column>
			<el-table-column
			  prop="status"
			  label="状态"
			  width="300"
			  :formatter="formatStatus">
			</el-table-column>
			<el-table-column
			  prop="createTime"
			  label="创建日期"
			  width="350">
			</el-table-column>
			<el-table-column
		      prop="updateTime"
		      label="更新日期"
		      width="350">
		    </el-table-column>
		  </el-table>
		  
		 <!-- 增加菜单的对话框 -->
		 <el-dialog title="添加" :visible.sync="insertFormVisible">
		 	<el-form ref="insertform" :model="insertform" >
				<!-- <el-form-item label="上级部门" :label-width="'120px'" prop="pid">
		 			<el-select v-model="insertform.pid" placeholder="请选择上级部门">
		 				<el-option label="无" :value="-1"></el-option>
						<el-option v-for="(option,index) in optionList" :key="index" :label="option.dname"
								:value="option.id"></el-option>
								
		 			</el-select>
		 		</el-form-item> -->
				<el-form-item label="上级部门" :label-width="'120px'">
					<treeselect v-model="insertform.pid"  :multiple="false" :options="deptTree"
						:normalizer="normalizer" placeholder="选择部门" />
				</el-form-item>
		 		<el-form-item label="部门名称" :label-width="'120px'" prop="dname">
		 			<el-input v-model="insertform.dname" autocomplete="off"></el-input>
		 		</el-form-item>
				<el-form-item label="部门所在地" :label-width="'120px'" prop="daddress">
					<el-input v-model="insertform.daddress" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="状态" :label-width="'120px'" prop="status">
					<el-radio-group v-model="insertform.status" @change="changeStatus">
						<el-radio v-for="item in dataStatus" :key="item.itemValue" :label="item.itemValue">{{item.itemLabel}}</el-radio>
					</el-radio-group>
				</el-form-item>
		 	</el-form>
		 	<div slot="footer" class="dialog-footer">
		  	<el-button @click="insertFormVisible = false">取 消</el-button>
		 		<el-button type="primary" @click="addDept">确 定</el-button>
		 	</div>
		 </el-dialog> 
		 
		 
		 <!-- 修改菜单的对话框 -->
		 <el-dialog title="修改" :visible.sync="editFormVisible">
		 	<el-form ref="editform" :model="editform" >
				<el-form-item label="上级部门" :label-width="'120px'" prop="pid">
					<el-select v-model="editform.pid" placeholder="请选择上级部门">
						<el-option v-for="(option,index) in optionList" :key="index" :label="option.dname"
							:value="option.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="部门名称" :label-width="'120px'" prop="dname">
					<el-input v-model="editform.dname" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="部门所在地" :label-width="'120px'" prop="daddress">
					<el-input v-model="editform.daddress" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="负责人" :label-width="'120px'" prop="uid">
					<el-select v-model="editform.uid" placeholder="请选择部门负责人">
						<el-option label="无部门负责人" :value="''"></el-option>
						<el-option v-for="(user,index) in userList" :key="index" :label="user.uname"
								:value="user.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="状态" :label-width="'120px'" prop="status">
					<el-radio-group v-model="editform.status" @change="changeStatus">
						<el-radio v-for="item in dataStatus" :key="item.itemValue" :label="item.itemValue">{{item.itemLabel}}</el-radio>
					</el-radio-group>
				</el-form-item>
		 	</el-form>
		 	<div slot="footer" class="dialog-footer">
		  	<el-button @click="editFormVisible = false">取 消</el-button>
		 		<el-button type="primary" @click="doEdit">确 定</el-button>
		 	</div>
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
				dataStatus:[],
				optionList: [],
				insertFormVisible:false,
				editFormVisible:false,
				currentRow:null,
				queryForm: {
					dname: "",
					status:""
				},
				dataForm: {
					id:"",
					dname: '',
					daddress: '',
					status:"",
					createTime: '',
					updateTime: '',
					childs:[]
				},
				insertform:{
					dname: '',
					daddress:"",
					pid: "",
					uid:"",
					status:""
				},
				personList:[{
					id:"",
					uname:"无部门负责人"
				}],
				deptTree: [],
				dept:[],
				currentRow:null,
				editform:{
					dname: '',
					daddress:"",
					pid: "",
					uid:"",
					status:""
				},
				userList:[],
				normalizer(node) {
					if(node.childs==null||node.childs=='null'){
						delete node.childs;
					}
					return {
						id: node.id,
						label: node.dname,
						children: node.childs,
					}
				}
			}
			
		},
		methods: {
			changeStatus(){
				console.log(this.editform.status)
			},
			formatStatus(row, column, cellValue, index){
				let item = this.getItemLabel(this.dataStatus,cellValue);
				if(item == null || item == undefined) return "--";
				return item.itemLabel;
			},
			handleCurrentRowChange(val){
				this.currentRow = val;
			},
			doQuery() {
				this.getDeptTree();
			},
			openAdd(){
				this.insertFormVisible=true;
				this.$nextTick(function(){
					this.$refs.insertform.resetFields();
				})
				axios.get("dept/tree").then(resp => {
					this.dept = Object.values(resp.data);
				})
				axios.get("/dept").then(resp=>{
					this.optionList = resp.data.list;
				})
			},
			
			addDept(){
				this.insertFormVisible = false;
				axios.post("dept",this.insertform).then(resp=>{
					if (resp.code == 20000) {
						this.suc('修改成功');
						this.getDeptTree();
					} else {
						this.error("修改失败");
					}
				})
			},
			
			openEdit(){
				if (this.currentRow == null) {
					this.error("请选中一行进行删除");
					return;
				}
				this.personList = [{
					id:"",
					uname:"无部门负责人",
				}]
				this.editFormVisible = true;
				axios.get("/dept/" + this.currentRow.id).then(resp=>{
					this.editform = resp.data[0];
				})
				axios.get("/dept/getChargePerson?deptId=" + this.currentRow.id + "&posId=1").then(resp=>{
					if(resp.data.length > 0){
						this.personList = resp.data;
						this.editform.uid = this.personList[0].id;
					}
				})
				axios.get("/dept/getChargePerson?deptId=" + this.currentRow.id).then(resp=>{
					this.userList = resp.data;
				})
				console.log(this.editform)
			},
	
			doEdit(){
				if(this.editform.uid != this.personList[0].id){
					if(this.personList[0].id != ""){
						axios.put("dept/"+ this.personList[0].id+"?posId=2").then(resp=>{
							console.log("success1");
						})
					}
					if(this.editform.uid != ""){
						axios.put("dept/"+ this.editform.uid+"?posId=1").then(resp=>{
							console.log("success2");
						})
					}
				}
				axios.put("dept",this.editform).then(resp=>{
					if (resp.code == 20000) {
						this.suc("修改成功");
						this.getDeptTree();
					}else{
						this.error("修改失败");
					}
				})
				this.editFormVisible = false;
			},
	
			doDelete() {
				if (this.currentRow == null) {
					this.error("请选中一行进行删除");
					return;
				}
				let id=this.currentRow.id;
				this.$confirm(`此操作将永久删除编号为 ${id} 的记录, 是否继续?`, '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {
							axios.delete('dept/' + id).then(resp => {
									if (resp.code == 20000) {
										this.suc("删除成功");
										this.getDeptTree();
									}
								})
						}).catch(() => {
						  this.$message({
							type: 'info',
							message: '已取消删除'
						  });          
						});
			},
			
			getDeptTree() {
				this.currentRow=null;
				axios.get(`dept/tree?dname=${this.queryForm.dname}&status=${this.queryForm.status}`).then(resp => {
					this.deptTree = Object.values(resp.data);
				})
			},
			
	
		},
		created() {
			this.getDeptTree();
			this.getDictItems("data_status").then(resp => {
				this.dataStatus = resp.data;
			})
		},
		watch: {
			tableData(newValue, oldValue) {
				this.$nextTick(function(){
					this.insertFormVisible = true;
				})
			}
		},
	}
</script>

<style>
</style>
