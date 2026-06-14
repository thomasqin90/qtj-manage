package com.qtj.manageserver.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.*;
import com.qtj.manageserver.service.SysUserService;
import com.qtj.manageserver.vo.SysUserVO;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
    // 用户列表
    @GetMapping("/list")
    public Result<IPage<SysUserVO>> list(PageDTO pageDto, SysUserQueryDTO userQueryDto) {
        // 分页，直接调用MyBatisPlus的IPage
        Page<SysUserDTO> page = Page.of(pageDto.getPageNum(), pageDto.getPageSize());
        // 查询用户列表
        IPage<SysUserDTO> pageRes = sysUserService.getUserWithRolePage(page, userQueryDto);
        IPage<SysUserVO> res = pageRes.convert((SysUserDTO dto) -> {
            SysUserVO vo = new SysUserVO();
            BeanUtil.copyProperties(dto, vo);
            return vo;
        });
        return Result.success(res);
    }
    // 用户详情
    @GetMapping("/{id}")
    public Result<SysUserVO> detail(@PathVariable Long id) {
        SysUserDTO user = sysUserService.getUserWithRole(id);
        SysUserVO userVO = new SysUserVO();
        BeanUtil.copyProperties(user, userVO);
        return Result.success(userVO);
    }
    // 新增用户
    @PostMapping
    public Result<Boolean> add(@RequestBody SysUserSaveDTO user) {
        // 密码加密
        String psdMD5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(psdMD5);
        boolean res =  sysUserService.insertUserWithRole(user);
        return Result.success(res);
    }
    // 更新用户
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SysUserSaveDTO user) {
        user.setId(id);
        if(StrUtil.isNotBlank(user.getPassword())) {
            String psdMD5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(psdMD5);
        }
        // 更新用户表及其用户-角色表
        boolean res = sysUserService.updateUserWithRole(user);
        return Result.success(res);
    }
    // 批量删除
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean res = sysUserService.deleteUserWithRole(ids);
        return Result.success(res);
    }
}
