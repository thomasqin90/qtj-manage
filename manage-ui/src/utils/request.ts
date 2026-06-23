import axios from "axios";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import router from "@/router";

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 5000,
});
// 请求拦截器：添加token、检查权限等
request.interceptors.request.use(
  (request) => {
    const userStore = useUserStore();
    // 添加token
    if (userStore.token) {
      request.headers["Authorization"] = `Bearer ${userStore.token}`;
    }
    return request;
  },
  (error) => {
    console.error("请求拦截器错误", error);
    return Promise.reject(error);
  },
);
// 响应拦截器：
request.interceptors.response.use(
  (reponse) => {
    return reponse.data;
  },
  (error) => {
    console.error("响应拦截器错误", error);
    if (error.reponse) {
      if (error.reponse.status === 401) {
        // 跳转登录
        ElMessage.error("请先登录");
        router.push("/login");
      } else if (error.reponse.status === 404) {
        // 接口不存在
        ElMessage.error("接口不存在");
      } else if (error.reponse.status === 500) {
        // 服务器错误
        ElMessage.error("服务器错误");
      } else {
        // 
        ElMessage.error(error.message);
      }
    } else {
      ElMessage.error(error.message);
    }
    return Promise.reject(error);
  },
);

export default request;
