package com.qtj.manageserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qtj.manageserver.dto.SysPermissionDTO;
import com.qtj.manageserver.entity.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 查询所有权限，树形结构
     * @return
     */
    List<SysPermissionDTO> selectTree(String keyword);

    /**
     * 查询指定用户的权限树
     * @param userId
     * @return
     */
    List<SysPermissionDTO> selectTreeByUserId(Long userId);
}
