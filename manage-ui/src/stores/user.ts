import { defineStore } from "pinia";
import type { User } from "@/types/user";

const useUserStore = defineStore("user", {
  state: () : {
    userInfo: User | null,
    token: string
  } => ({
    userInfo: null,
    token: "",
  }),
  actions: {
    setUserInfo(userInfo: User) {
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      this.userInfo = userInfo;
    },
    setToken(token: string) {
      localStorage.setItem("token", token);
      this.token = token;
    },
    login(data) {
        
    }
  },
  getters: {
    getUserInfo: (state) => state.userInfo,
    getToken: (state) => state.token,
  },
});

export default useUserStore;
