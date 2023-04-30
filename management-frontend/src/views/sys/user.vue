<template>
  <div>
    <!-- 搜索框 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.username"
            placeholder="用户名"
            clearable
          ></el-input>
          <el-input
            v-model="searchModel.phone"
            placeholder="电话"
            clearable
          ></el-input>
          <el-button
            type="primary"
            @click="getUserList"
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
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="id" label="用户ID" width="180">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="180">
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="180">
        </el-table-column>
        <el-table-column prop="status" label="用户状态" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1">正常</el-tag>
            <el-tag type="danger" v-if="scope.row.status == 0">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="电子邮箱"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="openEditUI(scope.row.id)" circle></el-button>
            <el-button type="danger" icon="el-icon-delete" @click="deleteUser(scope.row.id)" circle></el-button>
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
      <el-form :model="userForm" :rules="rules" ref="userFormRef">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="userForm.id == null || userForm.id == undefined" label="登录密码" :label-width="formLabelWidth" prop="password">
          <el-input type="password" v-model="userForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" :label-width="formLabelWidth" prop="email">
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import userApi from '@/api/userManage'
export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!reg.test(value)) {
        return callback(new Error('邮箱格式错误'))
      }
      return callback()
    }
    return {
      formLabelWidth: '120px',
      userForm: {},
      dialogFormVisible: false,
      title: '',
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      userList: [],
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入电子邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    saveUser() {
      this.$refs.userFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          userApi.saveUser(this.userForm).then(res => {
            // 成功提示
            this.$message({
              message: res.msg,
              type: 'success'
            })
            // 关闭对话框
            this.dialogFormVisible = false
            // 刷新表格
            this.getUserList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    deleteUser(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userApi.deleteUserById(id).then(res => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getUserList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    clearForm() {
      this.userForm = {}
      this.$refs.userFormRef.clearValidate()
    },
    openEditUI(id) {
      if (id == null) {
        this.title = '新增信息'
      } else {
        this.title = '更新信息'
        userApi.getUserById(id).then((res) => {
          this.userForm = res.data
          console.log(this.userForm.username)
        })
      }
      this.dialogFormVisible = true
    },
    handleSizeChange() {
      this.searchModel.pageSize = pageSize
      this.getUserList()
    },
    handleCurrentChange() {
      this.searchModel.pageNo = pageNo
      this.getUserList()
    },
    getUserList() {
      userApi.getUserList(this.searchModel).then((res) => {
        this.userList = res.data.rows
        this.total = res.data.total
      })
    }
  },
  created() {
    this.getUserList()
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
