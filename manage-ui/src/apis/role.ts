import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";
import type { Role, RoleFilter } from "@/types/role";
import type { Page } from "@/types/page";
/**
 * 获取全部角色列表
 * @returns
 */
export function getRoleList() {
  const url = buildApiUrl("/role/list");
  return request.get(url);
}
/**
 * 分页获取角色列表
 * @param page
 * @param query
 * @returns
 */
export function getRolePage(page: Page, query: RoleFilter) {
  const url = buildApiUrl("/role/page");
  return request.get(url, { params: { ...page, ...query } });
}

export function getRoleDetail(id: string) {
  const url = buildApiUrl(`/role/${id}`);
  return request.get(url);
}

export function addRole(role: Role) {
  const url = buildApiUrl("/role");
  return request.post(url, role);
}

export function updateRole(id: string, role: Role) {
  const url = buildApiUrl(`/role/${id}`);
  return request.put(url, role);
}

export function deleteRoles(idList: string[]) {
  const ids = idList.join(",");
  const url = buildApiUrl(`/role/${ids}`);
  return request.delete(url);
}

export function assignPermissions(roleId: string, permissionIds: string[]) {
  const url = buildApiUrl(`/role/assign`);
  return request.post(url, {
    roleId,
    permissionIds,
  });
}

export function getRolePermissions(roleId: string) {
  const url = buildApiUrl(`/role/permissionids`);
  return request.get(url, { params: { roleId } });
}
