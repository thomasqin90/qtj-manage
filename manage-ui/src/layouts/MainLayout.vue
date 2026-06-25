<template>
  <div class="main-layout">
    <el-container>
      <el-header>
        <div class="navbar">
          <div class="left">
            <img class="logo" src="@/assets/img/logo.png" alt="" />
            <b>QIN-MANAGE</b>
          </div>
          <!-- 当前页面路径 -->
          <div class="path">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item
                :to="{ path: item.path }"
                v-for="item in breadList"
                :key="item.path"
              >
                {{ item.meta.title }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="right">
            <el-switch
              class="theme-switch"
              inline-prompt
              active-icon="MoonNight"
              inactive-icon="Sunny"
              :model-value="themeStore.isDark"
              @click.prevent="themeStore.toggleTheme($event)"
            />
            <!-- 头像、用户名 -->
            <el-dropdown>
              <div class="account">
                <el-avatar :size="40" :src="avatarUrl" />
                <span>{{ accountName }}</span>
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </div>
              <template #dropdown>
                <el-dropdown-item @click="$router.push('/personCenter')">个人中心</el-dropdown-item>
                <el-dropdown-item @click="handleLoginout">退出登录</el-dropdown-item>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-container>
        <!-- 侧边栏：菜单 -->
        <el-aside
          :style="{
            width: asideWidth,
          }"
        >
          <el-scrollbar>
            <el-menu
              router
              :default-active="activeMenu"
              :collapse="isCollapse"
              @open="handleOpen"
              @close="handleClose"
            >
              <el-menu-item index="/home">
                <el-icon><House /></el-icon>
                <span>首页</span>
              </el-menu-item>
              <the-menu-item
                v-for="menu in menuList"
                :key="menu.id"
                :item="menu"
              >
              </the-menu-item>
            </el-menu>
          </el-scrollbar>
          <el-icon class="toggle-collapse" size="20" @click="toggleCollapse">
            <DArrowLeft v-if="!isCollapse" />
            <DArrowRight v-if="isCollapse" />
          </el-icon>
        </el-aside>
        <!-- 主页面 -->
        <el-main>
          <!-- 标签容器 -->
          <div class="tabbar">

          </div>
          <!-- 页面容器 -->
          <div class="router-box">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
import { ref, computed, onMounted, watch } from "vue";
import TheMenuItem from "@/components/TheMenuItem/index.vue";
import type { Menu } from "@/components/TheMenuItem/types";
import { useThemeStore } from "@/stores/theme";
import { useUserStore } from "@/stores/user";
import { usePermissionStore } from "@/stores/permission";
import { DArrowRight } from "@element-plus/icons-vue";
import {
  type RouteLocationMatched,
  type RouteRecordRaw,
  useRoute, useRouter
} from "vue-router";

const themeStore = useThemeStore();
const permissionStore = usePermissionStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
// 激活菜单标识
const activeMenu = ref(route.path);

// 菜单
const menuList = ref<Menu[]>([]);

onMounted(() => {
  buildMenus(permissionStore.menuRoutes);
  // 初始化执行
  getBreadcrumb();
});

function buildMenus(routes: RouteRecordRaw[]) {
  console.log("buildMenus", routes);
  routes.forEach((route) => {
    menuList.value.push(route2Menu(route));
  });
  console.log("menuList", menuList.value);
}

function route2Menu(route: RouteRecordRaw): Menu {
  const menu = {
    id: route.path,
    index: route.path,
    name: route.meta?.title as string,
    icon: route.meta?.icon as string,
    children: [] as Menu[],
  };
  if (route.children && route.children.length > 0) {
    route.children.forEach((child) => {
      menu.children.push(route2Menu(child));
    });
  }
  return menu;
}

const avatarUrl = ref("");
const accountName = ref("用户名");
//
const isCollapse = ref(false);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
function toggleCollapse() {
  isCollapse.value = !isCollapse.value;
}
const asideWidth = computed(() => {
  return isCollapse.value ? "66px" : "200px";
});

// 面包屑数组
const breadList = ref<RouteLocationMatched[]>([]);

// 生成面包屑函数
const getBreadcrumb = () => {
  // matched 包含所有层级路由
  breadList.value = route.matched.filter(
    (item) => item.meta && item.meta.title,
  );
};

// 监听路由变化，自动更新面包屑
watch(
  () => route.path,
  (path) => {
    getBreadcrumb();
    activeMenu.value = path;
  },
  { immediate: true },
);

function handleLoginout() {
  userStore.toLogout();
  router.replace("/login");
}
</script>
<style lang="scss" scoped>
.main-layout {
  height: 100%;

  .logo {
    width: 50px;
    height: 50px;
    margin-right: 10px;
  }

  .el-container {
    height: 100%;
  }
  .el-header {
    border-bottom: 1px solid #e6e6e6;
  }
  .navbar {
    height: 100%;
    box-sizing: border-box;
    display: flex;

    .left {
      display: flex;
      align-items: center;
    }
    .path {
      display: flex;
      align-items: center;
      margin-left: 20px;
    }
    .right {
      display: flex;
      align-items: center;
      margin-left: auto;
    }
  }
  .el-aside {
    border-right: 1px solid #e6e6e6;
    position: relative;
    transition: width 0.5s;

    .el-menu {
      border-right: none;
    }
  }
  .account {
    cursor: pointer;
    display: flex;
    align-items: center;
    margin-left: 10px;

    .el-avatar {
      margin-right: 5px;
    }
  }
  :deep(.el-tooltip__trigger:focus-visible) {
    outline: unset;
  }
  .toggle-collapse {
    position: absolute;
    right: 4px;
    bottom: 20px;
  }
  //
  .tabbar {
    height: 40px;
    border-bottom: 1px solid #e6e6e6;
  }
  .el-main {
    --el-main-padding: 10px;
  }
  .router-box {
  }
}
</style>
