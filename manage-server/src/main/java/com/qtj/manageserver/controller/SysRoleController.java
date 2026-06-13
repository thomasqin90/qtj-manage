package com.qtj.manageserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.PageDTO;
import com.qtj.manageserver.dto.SysRoleAssignDTO;
import com.qtj.manageserver.dto.SysRoleQueryDTO;
import com.qtj.manageserver.entity.SysRole;
import com.qtj.manageserver.service.SysRoleService;
import com.qtj.manageserver.vo.SysRoleVO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    // 分页查询角色列表
    @GetMapping("/page")
    public Result<IPage<SysRoleVO>> page(PageDTO pageDto, SysRoleQueryDTO query) {
        // 分页
        Page<SysRole> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        // 筛选条件
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getRoleName()), "role_name", query.getRoleName());
        queryWrapper.like(StrUtil.isNotBlank(query.getRoleCode()), "role_code", query.getRoleCode());
        IPage<SysRole> res = sysRoleService.page(page, queryWrapper);
        IPage<SysRoleVO> voRes = res.convert((SysRole role) -> {
            SysRoleVO vo = new SysRoleVO();
            BeanUtil.copyProperties(role, vo);
            return vo;
        });
        return Result.success(voRes);
    }
    // 查询全部角色
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
    // 查询单个角色
    @GetMapping("/{id}")
    public Result<SysRole> detail(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.success(role);
    }
    // 新增角色
    @PostMapping
    public Result<Boolean> add(@RequestBody SysRole role) {
        boolean res = sysRoleService.save(role);
        return Result.success(res);
    }
    // 更新角色
    @PutMapping("/{id}")
    public Result<Boolean> update(Long id, SysRole role) {
        role.setId(id);
        boolean res = sysRoleService.updateById(role);
        return Result.success(res);
    }
    // 删除
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysRoleService.removeByIds(Arrays.asList(ids));
        return Result.success(res);
    }
    // 角色关联权限
    @PostMapping("/assign")
    public Result<Boolean> assign(@RequestBody SysRoleAssignDTO dto) {
        boolean res = sysRoleService.assignPermissions(dto.getRoleId(), dto.getPermissionIds());
        return Result.success(res);
    }

    @GetMapping("/permissionids")
    public Result<List<Long>> listRolePermissionIds(@NotNull Long roleId) {
        return Result.success(sysRoleService.selectRolePermissionIds(roleId));
    }
}
