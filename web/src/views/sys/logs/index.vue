<template>
	<div>
		<!-- 上部查询 -->
		<el-form ref="queryForm" :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="用户名称" prop="opsUser">
				<el-input v-model="queryForm.opsUser" placeholder="无"></el-input>
			</el-form-item>
			<el-form-item label="登录状态" prop="opsResult">
				<el-select v-model="queryForm.opsResult" placeholder="部门状态">
					<el-option label="无" :value="''"></el-option>
					<!-- <el-option v-for="item in dataStatus" :key="item.itemValue" :label="item.itemLabel"
						:value="item.itemValue">
					</el-option> -->
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="doQuery">查询</el-button>
				<el-button type="danger" @click="doDelete">删除</el-button>
			</el-form-item>
		</el-form>
		
		<el-table ref="multipleTable" :data="tableData" 
			highlight-current-row @current-change="handleCurrentRowChange">
			<el-table-column prop="id" label="编号"></el-table-column>
			<el-table-column prop="opsUser" label="登录用户" ></el-table-column>
			<el-table-column prop="opsResult" label="登录状态"></el-table-column>
			<el-table-column prop="opsTime" label="登陆时间" ></el-table-column>
		</el-table>
		
		<br>
		
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" style="text-align: center;"
			:current-page="queryForm.pageNum" :page-sizes="[2, 4, 10, 20]" :page-size="queryForm.pageSize"
			layout="total, sizes, prev, pager, next, jumper" :total="total">
		</el-pagination>
		
	</div>
</template>

<script>
	export default {
		data() {
			return {
				tableData: [],
				// dataStatus:[],
				// 	optionList: [],
				currentRow:null,
				queryForm: {
					opsUser: "",
					opsResult:"",
					opsType: "登录",
					pageNum: 1 ,
					pageSize: 10
				},
				total:undefined
			}
		},
		methods:{
			handleSizeChange(val) {
				this.queryForm.pageSize = val;
				this.getLoginInformation();
			},
			handleCurrentChange(val) {
				this.queryForm.pageNum = val;
				this.getLoginInformation();
			},
	// 		changeStatus(){
	// 			console.log(this.editform.status)
	// 		},
	// 		formatStatus(row, column, cellValue, index){
	// 			let item = this.getItemLabel(this.dataStatus,cellValue);
	// 			if(item == null || item == undefined) return "--";
	// 			return item.itemLabel;
	// 		},
			handleCurrentRowChange(val){
				this.currentRow = val;
			},
			
			doQuery() {
				this.getLoginInformation();
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
							axios.delete('logs/' + id).then(resp => {
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
					this.getLoginInformation();	
			},

			getLoginInformation() {
				this.currentRow=null;
				axios.get(`logs`,{params : this.queryForm}).then(resp => {
					this.total = resp.data.total;
					this.queryForm.pageNum = resp.data.pageNum;
					this.queryForm.pageSize = resp.data.pageSize;
					this.tableData = resp.data.list;
				})
			}
	
		},
		created() {
			this.getLoginInformation();
		}
	}
</script>

<style>
</style>