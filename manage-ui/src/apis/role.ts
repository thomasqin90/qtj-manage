import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";
import { type Role } from "@/types/role";

// 获取角色列表
export function getRoleList() {
  const url = buildApiUrl("/role/list");
  return request.get(url);
}
