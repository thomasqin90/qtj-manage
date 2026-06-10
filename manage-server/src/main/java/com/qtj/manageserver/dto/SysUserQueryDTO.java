package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

// 用户筛选参数
@Data
public class SysUserQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID  = -3660197530786030848L;

    private String username;

    private String nickname;

    private String roleId;
}
