import { defineStore } from "pinia";
import { ref, watch } from "vue";

export const useThemeStore = defineStore("theme", () => {
  // 当前主题是否为暗色
  const isDark = ref<boolean>(localStorage.getItem("dark-mode") === "true");

  watch(
    isDark,
    (val) => {
      document.documentElement.classList.toggle("dark", val);
      localStorage.setItem("dark-mode", val ? "true" : "false");
    },
    { immediate: true },
  );

  const transitioning = ref(false);
  // 切换主题
  const toggleTheme = async (e: MouseEvent) => {
    if (transitioning.value) return;
    transitioning.value = true;

    const x = e.clientX;
    const y = e.clientY;
    // 半径
    const radius = Math.hypot(window.innerWidth, window.innerHeight);
    const oldIsDark = isDark.value;
    // 截图
    const transition = document.startViewTransition(() => {
      // 切换主题
      isDark.value = !isDark.value;
    });
    await transition.ready;
    let keyframes;
    let pseudoElement;
    if (oldIsDark) {
      // 变亮，白色圆形（新视图）展开
      keyframes = [
        { clipPath: `circle(0px at ${x}px ${y}px)` },
        { clipPath: `circle(${radius}px at ${x}px ${y}px)` },
      ];
      pseudoElement = "::view-transition-new(root)";
      document.documentElement.style.setProperty("--old-z", "1");
      document.documentElement.style.setProperty("--new-z", "999");
    } else {
      // 变暗，白色圆形（旧视图）收缩
      keyframes = [
        { clipPath: `circle(${radius}px at ${x}px ${y}px)` },
        { clipPath: `circle(0px at ${x}px ${y}px)` },
      ];
      pseudoElement = "::view-transition-old(root)";
      document.documentElement.style.setProperty("--old-z", "999");
      document.documentElement.style.setProperty("--new-z", "1");
    }
    document.documentElement.animate(keyframes, {
      duration: 500,
      easing: "cubic-bezier(0.25, 0.8, 0.25, 1)",
      pseudoElement,
      fill: "forwards", // 🔥 关键：动画结束保持最后一帧
    });
    await transition.finished;
    document.documentElement.style.setProperty("--old-z", "1");
    document.documentElement.style.setProperty("--new-z", "999");
    transitioning.value = false;
  };

  return { isDark, toggleTheme };
});
