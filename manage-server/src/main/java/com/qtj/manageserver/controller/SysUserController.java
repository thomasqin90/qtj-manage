package com.qtj.manageserver.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.PageDTO;
import com.qtj.manageserver.dto.SysUserQueryDTO;
import com.qtj.manageserver.dto.SysUserSaveDTO;
import com.qtj.manageserver.entity.SysUser;
import com.qtj.manageserver.service.SysUserService;
import com.qtj.manageserver.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/list")
    public Result<IPage<SysUserVO>> list(PageDTO pageDto, SysUserQueryDTO userQueryDto) {
        // 查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(userQueryDto.getUsername()), "username", userQueryDto.getUsername());
        queryWrapper.like(StrUtil.isNotBlank(userQueryDto.getNickname()), "nickname", userQueryDto.getNickname());
        // 分页，直接调用MyBatisPlus的IPage
        Page<SysUserVO> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        IPage<SysUserVO> pageRes = sysUserService.getUserWithRolePage(page, queryWrapper);
        return Result.success(pageRes);
    }

    @GetMapping("/{id}")
    public Result<SysUserVO> detail(@PathVariable Long id) {
        SysUserVO user = sysUserService.getUserWithRole(id);
        return Result.success(user);
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody SysUserSaveDTO user) {
        String psdMD5 = DigestUtil.md5Hex(user.getPassword().getBytes());
        user.setPassword(psdMD5);
        boolean res =  sysUserService.insertUserWithRole(user);
        return Result.success(res);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SysUserSaveDTO user) {
        user.setId(id);
        if(StrUtil.isNotBlank(user.getPassword())) {
            String psdMD5 = DigestUtil.md5Hex(user.getPassword().getBytes());
            user.setPassword(psdMD5);
        }
        boolean res = sysUserService.updateUserWithRole(user);
        return Result.success(res);
    }

    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysUserService.deleteUserWithRole(ids);
        return Result.success(res);
    }
}
