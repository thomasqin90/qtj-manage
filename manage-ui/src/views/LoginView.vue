<template>
  <div class="login">
    <el-form ref="formRef" :model="model" :rules="rules" label-width="80">
      <h3>用户登录</h3>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="model.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="model.password" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useUserStore } from "@/stores/user";
import router from "@/router";

const formRef = ref();

const model = reactive({
  username: "",
  password: "",
});

const rules = reactive({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
  ],
});

const userStore = useUserStore();
//
function handleLogin() {
  console.log("登录", model);
  formRef.value.validate(async (valid: boolean) => {
    if (valid) {
      console.log("验证通过");
      // 调用登录接口
      await userStore.toLogin({
        username: model.username,
        password: model.password,
      });
      router.push("/");
    } else {
      console.log("验证失败");
    }
  });
}
</script>
<style lang="scss" scoped>
.login {
  width: 100%;
  height: 100%;
  background: #ccc;
  display: flex;
  justify-content: center;
  align-items: center;

  .el-form {
    width: 400px;
    padding: 20px 20px;
    background: #fff;
    border-radius: 10px;
    box-shadow:
      0 2px 4px rgba(0, 0, 0, 0.2),
      0 8px 16px rgba(0, 0, 0, 0.1);
  }
}
</style>
