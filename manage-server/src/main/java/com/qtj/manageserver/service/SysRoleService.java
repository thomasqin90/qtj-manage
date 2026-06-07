package com.qtj.manageserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtj.manageserver.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 给角色赋予一组权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);
}
