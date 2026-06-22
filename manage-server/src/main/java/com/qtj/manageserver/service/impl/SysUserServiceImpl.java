package com.qtj.manageserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtj.manageserver.common.CustomException;
import com.qtj.manageserver.dto.LoginDTO;
import com.qtj.manageserver.dto.SysUserDTO;
import com.qtj.manageserver.dto.SysUserQueryDTO;
import com.qtj.manageserver.dto.SysUserSaveDTO;
import com.qtj.manageserver.entity.SysUser;
import com.qtj.manageserver.entity.SysUserRole;
import com.qtj.manageserver.mapper.SysUserMapper;
import com.qtj.manageserver.mapper.SysUserRoleMapper;
import com.qtj.manageserver.service.SysUserService;
import com.qtj.manageserver.util.JwtUtil;
import com.qtj.manageserver.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleMapper userRoleMapper;

    private final JwtUtil jwtUtil;

    // 构造函数中传入依赖
    public SysUserServiceImpl(SysUserRoleMapper userRoleMapper, JwtUtil jwtUtil) {
        this.userRoleMapper = userRoleMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public IPage<SysUserDTO> getUserWithRolePage(Page<SysUserDTO> page, SysUserQueryDTO query) {
        return baseMapper.selectUserWithRole(page, query);
    }

    @Override
    public SysUserDTO getUserWithRole(Long id) {
        return baseMapper.getUserDetail(id);
    }
    // 新增用户，包含角色
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUserWithRole(SysUserSaveDTO sysUserSaveDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveDTO, sysUser);
        // 保存
        Long userId = (long) baseMapper.insert(sysUser);
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
    // 更新用户，包含角色
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserWithRole(SysUserSaveDTO sysUserSaveDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveDTO, sysUser);
        // 更新主表
        baseMapper.updateById(sysUser);
        // 先删除用户-角色关系
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserSaveDTO.getId());
        userRoleMapper.delete(queryWrapper);
        // 再插入用户-角色关系
        if(sysUserSaveDTO.getRoleIdList() != null && !sysUserSaveDTO.getRoleIdList().isEmpty()) {
            List<SysUserRole> userRoleList = new ArrayList<>();
            for (Long roleId : sysUserSaveDTO.getRoleIdList()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUserSaveDTO.getId());
                userRole.setRoleId(roleId);
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
        baseMapper.deleteByIds(Arrays.asList(idList));
        // 删除关联表
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", Arrays.asList(idList));
        userRoleMapper.delete(queryWrapper);
        return true;
    }

    @Override
    public SysUserDTO login(LoginDTO loginDTO) {
        // 根据用户名查询用户信息，是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginDTO.getUsername());
        SysUser user = baseMapper.selectOne(queryWrapper);
        if(user == null) {
            throw new CustomException("用户不存在");
        }
        // 密码是否一致
        String loginPsd = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if(!user.getPassword().equals(loginPsd)) {
            throw new CustomException("密码不正确");
        }
        // 状态是否可用
        if(user.getStatus() == 0) {
            throw new CustomException("账号已被禁用");
        }
        // 查询用户对应的角色
        SysUserDTO userDetail = baseMapper.getUserDetail(user.getId());
        //
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        userDetail.setToken(token);
        return userDetail;
    }

    @Override
    public boolean changePassword(Long userId, String password) {

        return false;
    }
}
