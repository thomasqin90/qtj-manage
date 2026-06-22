package com.qtj.manageserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtj.manageserver.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<Long> selectPermissionIdsByUserId(Long userId);
}
