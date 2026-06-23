package com.qtj.manageserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.SysPermissionDTO;
import com.qtj.manageserver.entity.SysPermission;
import com.qtj.manageserver.service.SysPermissionService;
import com.qtj.manageserver.vo.SysPermissionVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }
    // 查询所有权限，构建成树形结构
    @GetMapping("/tree")
    public Result<List<SysPermissionVO>> tree(String permissionName) {
        List<SysPermissionDTO> list = sysPermissionService.selectTree(permissionName);
        List<SysPermissionVO> res = list.stream().map(this::dto2vo).toList();
        return Result.success(res);
    }

    private SysPermissionVO dto2vo(SysPermissionDTO dto) {
        SysPermissionVO vo = new SysPermissionVO();
        BeanUtil.copyProperties(dto, vo);
        vo.setChildren(new ArrayList<>());
        if(!dto.getChildren().isEmpty()) {
            for(SysPermissionDTO childDTO: dto.getChildren()) {
                SysPermissionVO childVO = dto2vo(childDTO);
                vo.getChildren().add(childVO);
            }
        }
        return vo;
    }

    @GetMapping("/{id}")
    public Result<SysPermissionVO> detail(@PathVariable long id) {
        SysPermission res = sysPermissionService.getById(id);
        SysPermissionVO vo = new SysPermissionVO();
        BeanUtil.copyProperties(res, vo);
        return Result.success(vo);
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody SysPermissionDTO dto) {
        SysPermission entity = new SysPermission();
        BeanUtil.copyProperties(dto, entity);
        boolean res = sysPermissionService.save(entity);
        return Result.success(res);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SysPermissionDTO dto) {
        SysPermission entity = new SysPermission();
        entity.setId(id);
        BeanUtil.copyProperties(dto, entity);
        boolean res = sysPermissionService.updateById(entity);
        return Result.success(res);
    }

    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysPermissionService.removeByIds(Arrays.asList(ids));
        return Result.success(res);
    }

    /**
     * 获取用户的权限信息
     * @param
     * @return
     */
    @GetMapping("routes")
    public Result<List<SysPermissionVO>> routes(HttpServletRequest request) {
        // 从request域取出Long类型用户ID
        Long userId = (Long) request.getAttribute("loginUserId");
        List<SysPermissionDTO> dto = sysPermissionService.selectTreeByUserId(userId);
        List<SysPermissionVO> vo = dto.stream().map(d -> {
            SysPermissionVO v = new SysPermissionVO();
            BeanUtil.copyProperties(d, v);
            return v;
        }).toList();
        return Result.success(vo);
    }
}
