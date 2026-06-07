import request from "@/utils/request";
import type { Page } from "@/types/page";
import { buildApiUrl } from "@/utils/pathUtil";
import { type User } from "@/types/user"

export function getUserList(page: Page) {
  const url = buildApiUrl("/user/list");
  return request.get(url, {
    params: {
      ...page,
    },
  });
}

export function addUser(user: User) {
    const url = buildApiUrl("/user");
    return request.post(url, user);
}

export function updateUser(id: string, user: User) {
    const url = buildApiUrl(`/user/${id}`);
    return request.put(url, user);
}

export function deleteUsers(idList: string[]) {
    const ids = idList.join(",");
    const url = buildApiUrl(`/user/${ids}`);
    return request.delete(url);
}