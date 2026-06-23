import { defineStore } from "pinia";
import type { User } from "@/types/user";
import { login } from "@/apis/auth";
import { usePermissionStore } from "@/stores/permission";

export const useUserStore = defineStore("user", {
  state: (): {
    userInfo: User | null;
  } => {
    const userInfoStr = localStorage.getItem("userInfo");
    return {
      userInfo: userInfoStr ? JSON.parse(userInfoStr) : null,
    };
  },
  actions: {
    setUserInfo(userInfo: User) {
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      this.userInfo = userInfo;
    },
    // 登录
    async toLogin({
      username,
      password,
    }: {
      username: string;
      password: string;
    }) {
      // TODO: 登录接口
      const loginRes = await login({ username, password });
      console.log("登录信息", loginRes);
      this.setUserInfo(loginRes.data);
      //
      const permissionStore = usePermissionStore();
      await permissionStore.generateRoutes();
    },
    // 退出登录
    toLogout() {
      localStorage.removeItem("userInfo");
      this.userInfo = null;
    },
  },
  getters: {
    token: (state) => state.userInfo?.token || "",
  }
});
