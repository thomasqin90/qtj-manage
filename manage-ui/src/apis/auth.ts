import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";

export function login(data: { username: string; password: string }) {
  const url = buildApiUrl("/auth/login");
  return request.post(url, data);
}

export function logout() {
  const url = buildApiUrl("/auth/logout");
  return request.post(url);
}

/**
 * 修改密码
 * @param data
 * @returns
 */
export function changePassword(data: { currentPsd: string; newPsd: string }) {
  const url = buildApiUrl("/auth/changePassword");
  return request.post(url, data);
}
