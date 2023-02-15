<template>
<div>

<!--  岗位管理-->
  <el-row>
    <el-col :span="20">
      <el-form :inline="true" :model="post" class="demo-form-inline">
        <el-form-item label="岗位编码：">
          <el-input v-model="post.id" placeholder="岗位编码"></el-input>
        </el-form-item>
        <el-form-item label="岗位名称：">
          <el-input v-model="post.posName" placeholder="岗位名称"></el-input>
        </el-form-item>
        <el-form-item label="岗位状态：">
          <el-input v-model="post.posDesc" placeholder="岗位状态"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="inquire">查询</el-button>
          <el-button type="primary">重置</el-button>
          <el-button type="primary" @click="showAndDialog">添加</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>





        <el-table ref="multipleTable" :data="pos" tooltip-effect="dark" style="width: 100%">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="岗位编码" width="120"></el-table-column>
          <el-table-column prop="posName" label="岗位名称" width="120"></el-table-column>
          <el-table-column prop="posDesc" label="状态" width="120"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>

          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" @click="update(scope.$index, scope.row)">修改</el-button>
              <el-button type="danger" @click="deleteById(scope.$index,scope.row)">删除</el-button>

              <el-dialog title="修改品牌信息" :visible.sync="centerVisible" width="30%">

                <el-form ref="form" :model="post" label-width="100px">

                  <el-form-item label="岗位名称">
                    <el-input v-model="post.posName"></el-input>
                  </el-form-item>

                  <el-form-item label="状态">
                    <el-input v-model="post.posDesc"></el-input>
                  </el-form-item>

                  <el-form-item>
                    <el-button type="primary" @click="edit">提交</el-button>
                    <el-button @click="centerVisible = false">取消</el-button>
                  </el-form-item>

                </el-form>

              </el-dialog>
            </template>
          </el-table-column>

        </el-table>


  <!-- 添加,修改dialog -->
  <el-dialog  :visible.sync="addOrEditDialogVisible" width="50%">

    <el-form ref="dataForm" :model="posa" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="岗位编码">
            <el-input v-model="posa.id"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岗位名称">
            <el-input v-model="posa.posName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-input v-model="posa.posDesc"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间">
            <el-input v-model="posa.createTime"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="doSubmit">确定</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <!--    分页工具条-->
  <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="p.pageNum"
    :page-sizes="[2, 4, 6, 8]"
    :page-size="p.pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="totalCount">
  </el-pagination>

</div>
</template>

<script>
export default {
name: "index",
  data() {
    return {
      addOrEditDialogVisible: false,
      centerVisible:false,
      dialogVisible:false,
      totalCount:50,
      p: {pageNum: 1, pageSize: 2},
      // 每页显示的条数
      //pageSize:5,
      // 当前页码
      //currentPage: 1,
      pos:[],
      post:{},
      posa:{},
	  editRow : {}
    }
  },
  methods:{

     getPos(){
       axios.get('pos', {
         params:this.p
       }).then(resp => {
         this.totalCount = resp.data.total;
         this.p.pageNum = resp.data.pageNum;
         this.p.pageSize = resp.data.pageSize;
         this.pos = resp.data.list;
       })
     },
    //查询
    inquire(){
      axios.get('pos', {
        params: this.post
      }).then(resp => {
        this.pos = resp.data.list;
      })
    },
    showAndDialog(){
      this.addOrEditDialogVisible = true;
    },
    //添加
    doSubmit(){
       axios.post('pos',this.posa).then(resp => {
         if (resp.code == 20000) {
           this.suc('添加成功');
           this.getPos();
           this.addOrEditDialogVisible = false;
         } else {
           this.error("添加失败");
         }
       })
    },


    //修改
    update(index,row){
      this.centerVisible = true;
      this.editRow.id=row.id;
    },
    //提交修改
    edit(){
      axios.put('pos', this.post).then(resp => {
        if (resp.code == 20000){
          this.centerVisible = false;
          this.getPos();
          this.$message({
            message: '恭喜你，修改数据成功',
            type: 'success'
          });
        }else {
          this.$message.error('修改数据失败');
        }
      })
    },

    deleteById(index,row){
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('pos/' + row.id).then(resp=>{
          if(resp.code == 20000){
            this.getPos();
            this.$message({
              message: '恭喜你，删除成功',
              type: 'success'
            });
          }

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },


    //分页
    handleSizeChange(val) {
      //console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.p.pageSize  = val;
      this.getPos();
    },

    handleCurrentChange(val) {
      //console.log(`当前页: ${val}`);
      // 重新设置当前页码
      this.p.pageNum  = val;
      this.getPos();
    },

  },
  created() {

       this.getPos();

  }


}
</script>

<style scoped>

</style>
