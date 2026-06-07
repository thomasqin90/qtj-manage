package com.qtj.manageserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtj.manageserver.dto.SysUserSaveDTO;
import com.qtj.manageserver.entity.SysUser;
import com.qtj.manageserver.entity.SysUserRole;
import com.qtj.manageserver.mapper.SysUserMapper;
import com.qtj.manageserver.mapper.SysUserRoleMapper;
import com.qtj.manageserver.service.SysUserService;
import com.qtj.manageserver.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    private final SysUserRoleMapper userRoleMapper;

    public SysUserServiceImpl(SysUserMapper userMapper, SysUserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public IPage<SysUserVO> getUserWithRolePage(Page<SysUserVO> page, QueryWrapper<SysUser> queryWrapper) {
        return userMapper.selectUserWithRole(page, queryWrapper);
    }

    @Override
    public SysUserVO getUserWithRole(Long id) {
        return userMapper.getUserDetail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUserWithRole(SysUserSaveDTO sysUserSaveDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveDTO, sysUser);
        // 保存
        Long userId = (long) userMapper.insert(sysUser);
        if(sysUserSaveDTO.getRoleIdList() != null && !sysUserSaveDTO.getRoleIdList().isEmpty()) {
            List<SysUserRole> sysUserRoleList = new ArrayList<>();
            sysUserSaveDTO.getRoleIdList().forEach((Long roleId) -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                sysUserRoleList.add(sysUserRole);
            });
            userRoleMapper.insert(sysUserRoleList);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserWithRole(SysUserSaveDTO sysUserSaveDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveDTO, sysUser);
        // 更新主表
        userMapper.updateById(sysUser);
        // 刷新关联表
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserSaveDTO.getId());
        userRoleMapper.delete(queryWrapper);
        if(sysUserSaveDTO.getRoleIdList() != null && !sysUserSaveDTO.getRoleIdList().isEmpty()) {
            List<SysUserRole> userRoleList = new ArrayList<>();
            for (Long roleId : sysUserSaveDTO.getRoleIdList()) {
                SysUserRole userRole = new SysUserRole();
                userRoleList.add(userRole);
            }
            userRoleMapper.insert(userRoleList);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserWithRole(Long[] idList) {
        // 删除主表
        userMapper.deleteByIds(Arrays.asList(idList));
        // 删除关联表
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", Arrays.asList(idList));
        userRoleMapper.delete(queryWrapper);
        return true;
    }

}
