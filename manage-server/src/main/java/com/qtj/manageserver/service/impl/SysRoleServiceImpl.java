package com.qtj.manageserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtj.manageserver.entity.SysRole;
import com.qtj.manageserver.entity.SysRolePermission;
import com.qtj.manageserver.mapper.SysRoleMapper;
import com.qtj.manageserver.mapper.SysRolePermissionMapper;
import com.qtj.manageserver.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRolePermissionMapper sysRolePermissionMapper;

    public SysRoleServiceImpl(SysRolePermissionMapper sysRolePermissionMapper) {
        this.sysRolePermissionMapper = sysRolePermissionMapper;
    }

    @Override
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        // 1. 批量删除角色现有权限
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        sysRolePermissionMapper.delete(queryWrapper);
        // 2. 批量添加权限
        for (Long permissionId : permissionIds) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            sysRolePermissionMapper.insert(rolePermission);
        }
        return true;
    }
}
