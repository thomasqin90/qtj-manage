package com.qtj.manageserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.LoginDTO;
import com.qtj.manageserver.dto.SysUserDTO;
import com.qtj.manageserver.service.SysUserService;
import com.qtj.manageserver.vo.SysUserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SysUserService sysUserService;

    public AuthController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public Result<SysUserVO> login(@RequestBody LoginDTO loginDTO) {
        // 登录
        SysUserDTO user = sysUserService.login(loginDTO);
        SysUserVO vo = new SysUserVO();
        BeanUtil.copyProperties(user, vo);
        return Result.success(vo);
    }

    /**
     * 更改密码，登录后使用，从请求头中读取用户ID
     * @param currentPsd
     * @param newPsd
     * @return
     */
    @PostMapping("/password")
    public Result<Boolean> changePassword(String currentPsd, String newPsd) {

        return Result.success(null);
    }
}
