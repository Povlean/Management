<template>
  <div>
    <!-- 搜索框 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.roleName"
            placeholder="角色名称"
            clearable
          ></el-input>
          <el-button
            type="primary"
            @click="getRoleList"
            round
            icon="el-icon-search"
            >查询</el-button
          >
        </el-col>
        <el-col :span="4" align="right">
          <el-button type="primary" icon="el-icon-plus" circle @click="openEditUI(null)"></el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 管理列表 -->
    <el-card>
      <el-table :data="roleList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="roleId" label="角色ID" width="180">
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" width="180">
        </el-table-column>
        <el-table-column prop="roleDesc" label="角色描述"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="openEditUI(scope.row.roleId)" circle></el-button>
            <el-button type="danger" icon="el-icon-delete" @click="deleteRole(scope.row.roleId)" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-change="searchModel.pageNo"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="searchModel.pageSize"
      layout="total,sizes,prev,pager,next,jumper"
      :total="50"
    />

    <!-- 新增对话框 -->
    <el-dialog @close="clearForm()" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="roleForm" :rules="rules" ref="roleFormRef">
        <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
          <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" :label-width="formLabelWidth" prop="roleDesc">
          <el-input v-model="roleForm.roleDesc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限设置" :label-width="formLabelWidth" prop="menuIdList">
          <el-tree :data="menuList" :props="menuProps" show-checkbox style="width:70%" default-expand-all node-key="menuId" ref="menuRef"></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import roleApi from '@/api/roleManage'
import menuApi from '@/api/menuManage'
export default {
  data() {
    return {
      menuList: [],
      menuProps: {
        label: 'title',
        children: 'children'
      },
      formLabelWidth: '120px',
      roleForm: {},
      dialogFormVisible: false,
      title: '',
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      roleList: [],
      rules: {
        rolename: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ],
        roleDesc: [
          { required: true, message: '请输入角色描述', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    getAllMenu() {
      menuApi.getAllMenu().then(res => {
        this.menuList = res.data
      })
    },
    saveRole() {
      this.$refs.roleFormRef.validate((valid) => {
        if (valid) {
          var checkedKeys = this.$refs.menuRef.getCheckedKeys()
          var halfCheckedKeys = this.$refs.menuRef.getHalfCheckedKeys()
          this.roleForm.menuIdList = checkedKeys.concat(halfCheckedKeys)
          // 提交请求给后台
          roleApi.saveRole(this.roleForm).then(res => {
            // 成功提示
            this.$message({
              message: res.msg,
              type: 'success'
            })
            // 关闭对话框
            this.dialogFormVisible = false
            // 刷新表格
            this.getRoleList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    deleteRole(roleId) {
      this.$confirm(`此操作将删除角色, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        roleApi.deleteRoleById(roleId).then(res => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getRoleList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    clearForm() {
      this.roleForm = {}
      this.$refs.roleFormRef.clearValidate()
    },
    openEditUI(roleId) {
      if (roleId == null) {
        this.title = '新增角色'
      } else {
        this.title = '修改角色'
        roleApi.getRoleById(roleId).then((res) => {
          this.roleForm = res.data
          console.log(this.roleForm.rolename)
        })
      }
      this.dialogFormVisible = true
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getRoleList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getRoleList()
    },
    getRoleList() {
      roleApi.getRoleList(this.searchModel).then((res) => {
        this.roleList = res.data.rows
        this.total = res.data.total
      })
    }
  },
  created() {
    this.getRoleList()
    this.getAllMenu()
  }
}
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 15px;
}
.el-dialog .el-input {
  width: 75%;
}
</style>
