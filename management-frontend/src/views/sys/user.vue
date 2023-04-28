<template>
  <div>
    <!-- 搜索框 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.username" placeholder="用户名"></el-input>
          <el-input v-model="searchModel.phone" placeholder="电话"></el-input>
          <el-button type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <el-button type="primary" icon="el-icon-plus" circle></el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 管理列表 -->
    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column type="index" label="#" width="80">
        </el-table-column>
        <el-table-column prop="id" label="用户ID" width="180">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="180">
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="180">
        </el-table-column>
        <el-table-column prop="email" label="电子邮箱" >
        </el-table-column>
        <el-table-column label="操作" width="180">
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination 
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-change="searchModel.pageNo"
      :page-sizes="[5,10,15,20]"
      :page-size="searchModel.pageSize"
      layout="total,sizes,prev,pager,next,jumper"
      :total="50">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      userList: []
    }
  },
  method: {
    handleSizeChange() {

    },
    handleCurrentChange() {

    }
  }
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 15px;
}
</style>