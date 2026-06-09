<template>
  <el-form :model="form" :rules="rules" labelWidth="80">
    <el-form-item label="用户名">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="昵称">
      <el-input v-model="form.nickname" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="确认密码">
      <el-input v-model="confirmPsd"></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="form.email"></el-input>
    </el-form-item>
    <el-form-item label="手机号">
      <el-input v-model="form.phone"></el-input>
    </el-form-item>
    <el-form-item label="角色">
      <el-select v-model="form.roles" multiple placeholder="请选择角色">
        <el-option
          v-for="item in roleOptions"
          :key="item.id"
          :label="item.roleName"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="用户状态">
      <el-switch
        v-model="form.status"
        size="large"
        active-text="启用"
        inactive-text="禁用"
        active-value="1"
        inactive-value="0"
      />
    </el-form-item>
    <el-form-item>
      <el-button @click="cancel">取消</el-button>
      <el-button type="primary" @click="submit">提交</el-button>
    </el-form-item>
  </el-form>
</template>
<script lang="ts" setup>
import { onBeforeMount, reactive, ref, watch, watchEffect } from "vue";
import { getRoleList } from "@/apis/role";
import { getUserDetail, addUser, updateUser } from "@/apis/user";
import { type Role } from "@/types/role";
import { type User } from "@/types/user";

interface Props {
  userId: string;
}
// 组件属性
const props = defineProps<Props>();
// 组件事件
const emits = defineEmits<{
  callback: [event: string];
}>();

const form = reactive<User>({
  username: "",
  nickname: "",
  email: "",
  phone: "",
  password: "",
  status: 1,
  roles: [] as string[],
});
const rules = reactive({});
const confirmPsd = ref("");
// 角色列表
const roleOptions = ref<Role[]>([]);
function cancel() {
  emits("callback", "cancel");
}
async function submit() {
  if (props.userId) {
    // 更新用户
    if (form.password && form.password !== confirmPsd.value) {
      return;
    }
    const user = { ...form };
    await updateUser(props.userId, user);
  } else {
    // 新增用户
    if (form.password !== confirmPsd.value) {
      return;
    }
    const user = { ...form };
    await addUser(user);
  }
  emits("callback", "submitOk");
}
onBeforeMount(async () => {
  const res = await getRoleList();
  console.log("角色列表", res);
  roleOptions.value.push(...res.data);
});
watchEffect(async () => {
  if (props.userId) {
    // 获取用户信息，更新用户
    const user = await getUserDetail(props.userId);
    console.log("用户信息", user);
    form.username = user.data.username;
    form.nickname = user.data.nickname;
    form.email = user.data.email;
    form.phone = user.data.phone;
    form.status = String(user.data.status);
    // form.roles = user.data.roleList;
    form.password = "";
    confirmPsd.value = "";
  } else {
    // 新增用户
    form.username = "";
    form.nickname = "";
    form.email = "";
    form.phone = "";
    form.password = "";
    form.status = "1";
    form.roles = [];
    confirmPsd.value = "";
  }
});
</script>
<style lang="scss" scoped></style>
