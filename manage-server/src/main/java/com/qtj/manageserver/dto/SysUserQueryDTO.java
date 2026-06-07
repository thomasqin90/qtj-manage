package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serializable;

// 用户筛选参数
@Data
public class SysUserQueryDTO implements Serializable {

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;
}
