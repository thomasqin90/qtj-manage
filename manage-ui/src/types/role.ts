export interface Role {
  id?: string;
  roleName: string;
  roleCode: string;
  description: string;
  // 0=禁用；1=启用
  status: string;
  permissionIdList?: string[];
}

export interface RoleFilter {
  roleName: string;
  roleCode: string;
}
