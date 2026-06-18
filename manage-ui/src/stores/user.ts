import { defineStore } from "pinia";
import type { User } from "@/types/user";
import { login } from "@/apis/auth";

const useUserStore = defineStore("user", {
  state: (): {
    userInfo: User | null;
    token: string;
  } => {
    const userInfoStr = localStorage.getItem("userInfo");
    const token = localStorage.getItem("token");
    return {
      userInfo: userInfoStr ? JSON.parse(userInfoStr) : null,
      token: token ?? "",
    };
  },
  actions: {
    setUserInfo(userInfo: User) {
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      this.userInfo = userInfo;
    },
    setToken(token: string) {
      localStorage.setItem("token", token);
      this.token = token;
    },
    async toLogin({
      username,
      password,
    }: {
      username: string;
      password: string;
    }) {
      // TODO: 登录逻辑
      const loginRes = await login({ username, password });
      console.log("登录信息", loginRes);
    },
    toLogout() {},
  },
  getters: {
    getUserInfo: (state) => state.userInfo,
    getToken: (state) => state.token,
  },
});
