import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";

export function login(data: { username: string, password: string }) {
    const url = buildApiUrl("/auth/login");
    return request.post(url, data);
}

export function logout() {

}