import request from "@/utils/request";
import type { Page } from "@/types/page";
import { buildApiUrl } from "@/utils/pathUtil";
import type { User, UserFilter } from "@/types/user"

/**
 * 分页查询用户列表
 * @param page
 * @param query
 * @returns
 */
export function getUserList(page: Page, query?: UserFilter) {
  const url = buildApiUrl("/user/list");
  return request.get(url, {
    params: {
      ...page,
      ...query,
    },
  });
}

/**
 * 查询用户详情
 * @param id
 * @returns
 */
export function getUserDetail(id: string) {
  const url = buildApiUrl(`/user/${id}`);
  return request.get(url);
}

/**
 * 用户新增
 * @param user
 * @returns
 */
export function addUser(user: User) {
    const url = buildApiUrl("/user");
    return request.post(url, user);
}

/**
 * 用户更新 PUT
 * @param id
 * @param user
 * @returns
 */
export function updateUser(id: string, user: User) {
    const url = buildApiUrl(`/user/${id}`);
    return request.put(url, user);
}

/**
 * 用户删除 Delete
 * @param idList
 * @returns
 */
export function deleteUsers(idList: string[]) {
    const ids = idList.join(",");
    const url = buildApiUrl(`/user/${ids}`);
    return request.delete(url);
}

/**
 * 查询登录用户的菜单列表
 * @returns
 */
export function getRoutes() {
  const url = buildApiUrl("/user/routes");
  return request.get(url);
}

/**
 * 查询登录用户的权限
 * @returns
 */
export function getPermissions() {
  const url = buildApiUrl("/user/permissions");
  return request.get(url);
}