<template>
  <el-form :model="form" :rules="rules" labelWidth="80">
    <el-form-item label="父级权限">
      <el-tree-select
        v-model="form.parentId"
        :data="permissionTree"
        :render-after-expand="false"
        node-key="id"
        :props="{
            label: 'permissionName',
        }"
      />
    </el-form-item>
    <el-form-item label="权限类型" required>
      <el-radio-group v-model="form.permissionType">
        <el-radio
          v-for="type in permissionTypes"
          :value="type.value"
          :label="type.label"
        ></el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="权限名称" required>
      <el-input v-model="form.permissionName" />
    </el-form-item>
    <el-form-item label="权限编码" required>
      <el-input v-model="form.permissionCode" />
    </el-form-item>
    <el-form-item label="描述">
      <el-input v-model="form.description"></el-input>
    </el-form-item>
    <!-- 路由 -->
    <el-form-item label="路由">
      <el-input v-model="form.path"></el-input>
    </el-form-item>
    <!-- 组件路径 -->
    <el-form-item label="组件">
      <el-input v-model="form.component"></el-input>
    </el-form-item>
    <el-form-item>
      <icon-select v-model="form.icon" />
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
import { getPermissionDetail, insertPermission, updatePermission } from "@/apis/permission";
import type { Permission } from "@/types/permission";
import IconSelect from "@/components/IconSelect.vue";
//
interface Props {
  parentId: string;
  permissionTree: Permission[];
}
// 组件属性
const props = defineProps<Props>();
// 组件事件
const emits = defineEmits<{
  callback: [event: string];
}>();
// 表单
const form = reactive<Permission>({
  parentId: "",
  permissionName: "",
  permissionCode: "",
  description: "",
  status: 1,
  permissionType: "1",
  icon: "",
  path: "",
  component: "",
});
//
const rules = reactive({
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
  roleCode: [{ required: true, message: "请输入角色编号", trigger: "blur" }],
});
const permissionTypes = ref([
  { label: "目录", value: "dir" },
  { label: "菜单", value: "menu" },
  { label: "按钮", value: "button" },
]);
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
