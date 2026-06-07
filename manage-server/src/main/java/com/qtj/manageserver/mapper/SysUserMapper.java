package com.qtj.manageserver.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.entity.SysUser;
import com.qtj.manageserver.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户集合，包含关联的角色数据
     * @param page 分页
     * @param qw 查询条件
     * @return
     */
    IPage<SysUserVO> selectUserWithRole(Page<SysUserVO> page, @Param("ew") QueryWrapper<SysUser> qw);

    SysUserVO getUserDetail(Long id);
}
