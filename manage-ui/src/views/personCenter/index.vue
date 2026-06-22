<template>
  <div class="person-center">
    <!-- 个人信息 -->
    <el-card class="infobox">
      <template #header>
        <span>个人信息</span>
      </template>
      <div>
        <div class="item">
          <div class="label">用户名：</div>
          <div class="value">admin</div>
        </div>
        <div class="item">
          <div class="label">昵称：</div>
          <div class="value">admin</div>
        </div>
        <div class="item">
          <div class="label">手机号：</div>
          <div class="value">admin</div>
        </div>
        <div class="item">
          <div class="label">邮箱：</div>
          <div class="value">admin</div>
        </div>
        <div class="item">
          <div class="label">所属角色：</div>
          <div class="value">admin</div>
        </div>
      </div>
    </el-card>
    <!-- 修改面板 -->
    <el-card class="changebox">
      <template #header>
        <span>资料修改</span>
      </template>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本资料" name="first">
          <el-form :model="infoModel">
            <el-form-item label="昵称">
              <el-input ></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="second">
          <reset-password></reset-password>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { ref, reactive } from "vue";
import ResetPassword from "./ResetPassword.vue";

const activeName = ref("first");
const handleClick = (tab: any, event: Event) => {
  activeName.value = tab.name;
};

const infoModel = reactive({

})
</script>
<style lang="scss" scoped>
.person-center {
  display: flex;
  align-items: flex-start;

  .infobox {
    width: 400px;
    margin-right: 10px;
  }

  .changebox {
    flex: 1;
  }

  .item {
    display: flex;
    justify-content: space-between;
    padding: 6px 0;
  }
}
</style>
