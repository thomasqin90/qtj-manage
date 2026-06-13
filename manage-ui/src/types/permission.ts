export interface Permission {
  id?: string;
  permissionName: string;
  permissionCode: string;
  description: string;
  permissionType: string;
  parentId: string;
  path: string;
  component: string;
  icon: string;
  status: number;
}

export interface PermissionFilter {
  permissionName: string;
}
