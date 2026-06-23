export interface User {
  id?: string;
  username: string;
  password: string;
  nickname: string;
  email: string;
  phone: string;
  status: number | string;
  roleIdList: string[];
  token?: string;
}

export interface UserFilter {
  username?: string;
  nickname?: string;
  roleId?: string;
};
