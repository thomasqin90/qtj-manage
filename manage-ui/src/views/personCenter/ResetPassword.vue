<template>
  <el-form ref="formRef" :model="model" :rules="rules">
    <el-form-item label="旧密码" prop="currentPsd">
      <el-input type="password" v-model="model.currentPsd"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPsd">
      <el-input type="password" v-model="model.newPsd"></el-input>
    </el-form-item>
    <el-form-item label="新密码确认" prop="confirmPsd">
      <el-input type="password" v-model="model.confirmPsd"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSubmit">修改</el-button>
      <el-button @click="handleCancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { reactive, ref } from "vue";

const formRef = ref();
const model = reactive({
  currentPsd: "",
  newPsd: "",
  confirmPsd: "",
});
const rules = reactive({
  currentPsd: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
  newPsd: [{ required: true, message: "请输入新密码", trigger: "blur" }],
  confirmPsd: [
    { required: true, message: "请确认新密码", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        console.log("自定义校验", rule, value);
        if(value === model.newPsd) {
          callback();
        } else {
          callback(new Error("两次密码不一致"));
        }
      },
      trigger: "blur",
    },
  ],
});

function handleSubmit() {

}
function handleCancel() {

}
</script>
