package com.qtj.manageserver.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qtj.manageserver.dto.SysUserDTO;
import com.qtj.manageserver.dto.SysUserQueryDTO;
import com.qtj.manageserver.dto.SysUserSaveDTO;
import com.qtj.manageserver.entity.SysUser;
import com.qtj.manageserver.vo.SysUserVO;

public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表，携带角色信息
     * @param page
     * @param
     * @return
     */
    IPage<SysUserDTO> getUserWithRolePage(Page<SysUserDTO> page, SysUserQueryDTO query);

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    SysUserDTO getUserWithRole(Long id);

    /**
     * 插入用户，并建立角色关联
     * @param user
     * @return
     */
    boolean insertUserWithRole(SysUserSaveDTO user);

    /**
     * 更新用户，并修改角色关联
     * @param user
     * @return
     */
    boolean updateUserWithRole(SysUserSaveDTO user);

    /**
     * 删除用户及其角色关联
     * @param idList
     * @return
     */
    boolean deleteUserWithRole(Long[] idList);
}
