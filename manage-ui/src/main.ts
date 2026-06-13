import { createApp } from "vue";
import { createPinia } from "pinia";
import ElementPlus from "element-plus";
// 引入element-plus的样式
import "element-plus/dist/index.css";
// element-plus暗黑样式
import 'element-plus/theme-chalk/dark/css-vars.css'
// 图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
// 引入中文语言包
import zhCn from "element-plus/es/locale/lang/zh-cn";
import App from "./App.vue";
import router from "./router";

const app = createApp(App);
// 全局引入element-plus
app.use(ElementPlus, {
  locale: zhCn,
});
// 批量注册所有图标
const iconList:string[] = [];
// 全局引入element-plus的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
  iconList.push(key);
}
app.config.globalProperties.$iconList = iconList
// 数据共享
app.use(createPinia());
// 路由
app.use(router);
// 将App挂在到页面中
app.mount("#app");
