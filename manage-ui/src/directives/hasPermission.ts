// directives/hasPermission.ts
import type { Directive, DirectiveBinding } from 'vue'
import { usePermissionStore } from '@/stores/permission.ts'

const vHasPermission: Directive<HTMLElement, string | string[]> = {
  mounted(el: HTMLElement, binding: DirectiveBinding<string | string[]>) {
    handlePermission(el, binding)
  },
  // 适配路由切换、权限动态更新场景
  updated(el: HTMLElement, binding: DirectiveBinding<string | string[]>) {
    handlePermission(el, binding)
  }
}

/**
 * 权限校验核心逻辑
 * @param el DOM元素
 * @param binding 指令绑定值
 */
function handlePermission(el: HTMLElement, binding: DirectiveBinding<string | string[]>) {
  const permissionStore = usePermissionStore()
  // 当前页面接口拥有的权限数组
  const userPermissions: string[] = permissionStore.permissions || []
  // 指令传入权限标识：支持单个权限 'sys:user:add' / 多个权限数组 ['sys:user:add','sys:user:edit']
  const targetPerm = binding.value

  // 容错：没传权限直接移除元素
  if (!targetPerm) {
    removeElement(el)
    return
  }

  let hasAuth = false
  // 单个权限判断
  if (typeof targetPerm === 'string') {
    hasAuth = userPermissions.includes(targetPerm)
  }
  // 多个权限：满足任意一个即显示（或改为every全部满足）
  if (Array.isArray(targetPerm)) {
    hasAuth = targetPerm.some(perm => userPermissions.includes(perm))
    // 全部权限都需要拥有：hasAuth = targetPerm.every(perm => userPermissions.includes(perm))
  }

  // 无权限则删除DOM，不要用display:none（防止前端篡改显示）
  if (!hasAuth) {
    removeElement(el)
  }
}

/** 彻底移除DOM元素 */
function removeElement(el: HTMLElement) {
  el.parentNode && el.parentNode.removeChild(el)
}

export default vHasPermission