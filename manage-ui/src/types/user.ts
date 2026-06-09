export interface User {
  id?: string;
  username: string;
  password: string;
  nickname: string;
  email: string;
  phone: string;
  status: number | string;
  roles: string[];
}
