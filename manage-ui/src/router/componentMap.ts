// 批量读取views下所有.vue文件
const modules = import.meta.glob("@/views/**/*.vue");

const componentMap: Record<string, () => Promise<any>> = {
  Layout: () => import("@/layouts/MainLayout.vue"),
};

// 自动转换 key 为后端格式：system/user/index
Object.keys(modules).forEach((filePath) => {
  // 截取 @/views/ 后面，去掉 .vue
  let key = filePath.replace("/src/views/", "");
  // 去掉后缀 .vue
  key = key.replace(".vue", "");
  if (modules[filePath]) {
    componentMap[key] = modules[filePath];
  }
});

export default componentMap;
