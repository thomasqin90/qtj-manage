package com.qtj.manageserver.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.dto.SysUserDTO;
import com.qtj.manageserver.dto.SysUserQueryDTO;
import com.qtj.manageserver.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户集合，包含关联的角色数据
     * @param page 分页
     * @param
     * @return
     */
    IPage<SysUserDTO> selectUserWithRole(Page<SysUserDTO> page, @Param("query") SysUserQueryDTO query);

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    SysUserDTO getUserDetail(Long id);
}
