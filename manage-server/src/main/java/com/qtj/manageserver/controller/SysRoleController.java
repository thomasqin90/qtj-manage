package com.qtj.manageserver.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.PageDTO;
import com.qtj.manageserver.entity.SysRole;
import com.qtj.manageserver.service.SysRoleService;
import com.qtj.manageserver.vo.SysRoleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/page")
    public Result<IPage<SysRole>> page(PageDTO pageDto, SysRole role) {
        Page<SysRole> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(role.getRoleName()), "role_name", role.getRoleName());
        IPage<SysRole> res = sysRoleService.page(page, queryWrapper);
        return Result.success(res);
    }

    @GetMapping("/list")
    public Result<List<SysRoleVO>> list() {
        List<SysRole> roleList = sysRoleService.list();
        List<SysRoleVO> res = roleList.stream().map((sysRole)->{
            SysRoleVO roleVO = new SysRoleVO();
            BeanUtils.copyProperties(sysRole, roleVO);
            return roleVO;
        }).toList();
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
// 角色关联权限
    @PostMapping("/assign")
    public Result<Boolean> assign(Long roleID, Long[] permissions) {
        boolean res = sysRoleService.assignPermissions(roleID, Arrays.asList(permissions));
        return Result.success(res);
    }
}
