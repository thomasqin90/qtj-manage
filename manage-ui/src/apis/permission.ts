import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";
import type { Permission, PermissionFilter } from "@/types/permission";

/**
 * 获取权限树
 * @returns
 */
export function getPermissionTree(params?: PermissionFilter) {
  const url = buildApiUrl("/permission/tree");
  return request.get(url, {
    params: {
      ...params,
    },
  });
}

export function getPermissionDetail(id: string) {
  const url = buildApiUrl(`/permission/${id}`);
  return request.get(url);
}

export function insertPermission(p: Permission) {
  const url = buildApiUrl("/permission");
  return request.post(url, p);
}

export function updatePermission(id: string, p: Permission) {
  const url = buildApiUrl(`/permission/${id}`);
  return request.put(url, p);
}

export function deletePermission(id: string) {
  const url = buildApiUrl(`/permission/${id}`);
  return request.delete(url);
}
