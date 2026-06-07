package com.qtj.manageserver.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.PageDTO;
import com.qtj.manageserver.entity.SysRole;
import com.qtj.manageserver.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/list")
    public Result<IPage<SysRole>> list(PageDTO pageDto, SysRole role) {
        Page<SysRole> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(role.getRoleName()), "role_name", role.getRoleName());
        IPage<SysRole> res = sysRoleService.page(page, queryWrapper);
        return Result.success(res);
    }

    @GetMapping("/{id}")
    public Result<SysRole> detail(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.success(role);
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody SysRole role) {
        boolean res = sysRoleService.save(role);
        return Result.success(res);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(Long id, SysRole role) {
        role.setId(id);
        boolean res = sysRoleService.updateById(role);
        return Result.success(res);
    }

    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysRoleService.removeByIds(Arrays.asList(ids));
        return Result.success(res);
    }

    @PostMapping("/assign")
    public Result<Boolean> assign(@RequestBody SysRole role) {
        boolean res = sysRoleService.assignPermissions(role.getId(), role.getPermissionIds());
        return Result.success(res);
    }
}
