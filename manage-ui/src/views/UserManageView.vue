<template>
  <div>用户管理</div>
  <!-- 筛选栏 -->
  <el-form :inline="true" :model="queryParams">
    <!-- 用户名 -->
    <el-form-item label="用户名">
      <el-input v-model="queryParams.username"></el-input>
    </el-form-item>
    <!-- 昵称 -->
    <el-form-item label="昵称">
      <el-input v-model="queryParams.nickname"></el-input>
    </el-form-item>
    <!-- 角色 -->
    <el-form-item label="角色">
      <el-select v-model="queryParams.role" style="width: 100px">
        <el-option label="管理员" value="admin"></el-option>
        <el-option label="普通用户" value="user"></el-option>
      </el-select>
    </el-form-item>
    <!-- 按钮 -->
    <el-form-item>
      <el-button type="primary">查询</el-button>
      <el-button>重置</el-button>
    </el-form-item>
  </el-form>
  <!-- 操作按钮 -->
  <div>
    <!-- 添加 -->
    <el-button type="primary" icon="Plus">添加</el-button>
    <!-- 编辑 -->
    <el-button type="primary" icon="EditPen">编辑</el-button>
    <!-- 删除 -->
    <el-button type="danger" icon="Delete">删除</el-button>
    <!-- 导出 -->
    <el-button type="primary" icon="Document">导出</el-button>
  </div>
  <!-- 表格 -->
  <div>
    <el-table :data="tableData">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <!-- 手机 -->
      <el-table-column prop="phone" label="手机"></el-table-column>
      <!-- 邮箱 -->
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <!-- 状态 -->
      <el-table-column prop="status" label="状态"></el-table-column>
    </el-table>
    <el-pagination :total="50"></el-pagination>
  </div>
</template>
<script lang="ts" setup>
import { getUserList } from "@/apis/user";
import { reactive, ref } from "vue";
import type { Page } from "@/types/page";

const queryParams = reactive({
  username: "",
  nickname: "",
  role: "",
});

const tableData = ref([]);

const page: Page = reactive({
  pageNum: 1,
  pageSize: 10,
});

getUserList(page).then((res) => {
  console.log("用户列表", res);
});
</script>
<style lang="scss" scoped></style>
