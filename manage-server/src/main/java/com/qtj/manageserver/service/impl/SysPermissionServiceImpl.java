package com.qtj.manageserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtj.manageserver.entity.SysPermission;
import com.qtj.manageserver.mapper.SysPermissionMapper;
import com.qtj.manageserver.service.SysPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {
}
