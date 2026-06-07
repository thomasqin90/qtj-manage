package com.qtj.manageserver.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.PageDTO;
import com.qtj.manageserver.dto.SysPermissionDTO;
import com.qtj.manageserver.entity.SysPermission;
import com.qtj.manageserver.service.SysPermissionService;
import com.qtj.manageserver.vo.SysPermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @GetMapping("/list")
    public Result<IPage<SysPermissionVO>> list(PageDTO pageDto, SysPermissionDTO sysPermissionDto) {
        Page<SysPermission> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("permission_name", "permission_code", "permission_type");
        queryWrapper.like(StrUtil.isNotBlank(sysPermissionDto.getPermissionName()), "permission_name",
                sysPermissionDto.getPermissionName());
        queryWrapper.like(StrUtil.isNotBlank(sysPermissionDto.getPermissionCode()), "permission_code",
                sysPermissionDto.getPermissionCode());
        queryWrapper.like(StrUtil.isNotBlank(sysPermissionDto.getPermissionType()), "permission_type",
                sysPermissionDto.getPermissionType());
        IPage<SysPermission> res = sysPermissionService.page(page, queryWrapper);
        log.info(res.toString());
        IPage<SysPermissionVO> voRes = res.convert(entity -> {
            SysPermissionVO vo = new SysPermissionVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
        return Result.success(voRes);
    }

    @GetMapping("/{id}")
    public Result<SysPermissionVO> detail(@PathVariable long id) {
        SysPermission res = sysPermissionService.getById(id);
        SysPermissionVO vo = new SysPermissionVO();
        BeanUtils.copyProperties(res, vo);
        return Result.success(vo);
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody SysPermissionDTO dto) {
        SysPermission entity = new SysPermission();
        BeanUtils.copyProperties(dto, entity);
        boolean res = sysPermissionService.save(entity);
        return Result.success(res);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SysPermissionDTO dto) {
        SysPermission entity = new SysPermission();
        entity.setId(id);
        BeanUtils.copyProperties(dto, entity);
        boolean res = sysPermissionService.updateById(entity);
        return Result.success(res);
    }

    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysPermissionService.removeByIds(Arrays.asList(ids));
        return Result.success(res);
    }
}
