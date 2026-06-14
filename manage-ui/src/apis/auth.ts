import request from "@/utils/request";
import { buildApiUrl } from "@/utils/pathUtil";

export function login(data) {
    const url = buildApiUrl("/auth/login");
    request.post(url, data);
}  