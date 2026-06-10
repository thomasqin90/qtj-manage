package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5525767691501536826L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<SysRoleDTO> roleList = new ArrayList<>();
}
