<template>
  <el-form :model="form" :rules="rules" labelWidth="80">
    <el-form-item label="角色名称" required>
      <el-input v-model="form.roleName" />
    </el-form-item>
    <el-form-item label="角色编号" required>
      <el-input v-model="form.roleCode" />
    </el-form-item>
    <el-form-item label="描述">
      <el-input v-model="form.description"></el-input>
    </el-form-item>
    <el-form-item label="状态">
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
import { onBeforeMount, reactive, ref, watch } from "vue";
import { getRoleDetail, addRole, updateRole } from "@/apis/role";
import type { Role } from "@/types/role";
//
interface Props {
  roleId: string;
}
// 组件属性
const props = defineProps<Props>();
// 组件事件
const emits = defineEmits<{
  callback: [event: string];
}>();
// 表单
const form = reactive<Role>({
  roleName: "",
  roleCode: "",
  description: "",
  status: "1",
});
//
const rules = reactive({
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
  roleCode: [{ required: true, message: "请输入角色编号", trigger: "blur" }],
});
//
function cancel() {
  emits("callback", "cancel");
}
//
async function submit() {
  if (props.roleId) {
    // 更新用户
    const role = { ...form };
    await updateRole(props.roleId, role);
  } else {
    // 新增用户
    const user = { ...form };
    await addRole(user);
  }
  emits("callback", "submitOk");
}
//
onBeforeMount(async () => {});
//
watch(
  () => {
    return props.roleId;
  },
  async () => {
    if (props.roleId) {
      // 获取用户信息，更新用户
      const user = await getRoleDetail(props.roleId);
      console.log("用户信息", user);
      form.roleName = user.data.roleName;
      form.roleCode = user.data.roleCode;
      form.description = user.data.description;
      form.status = user.data.status;
    } else {
      // 新增用户
      form.roleName = "";
      form.roleCode = "";
      form.description = "";
      form.status = "1";
    }
  },
);
</script>
<style lang="scss" scoped></style>
