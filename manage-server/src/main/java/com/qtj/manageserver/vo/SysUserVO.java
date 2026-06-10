package com.qtj.manageserver.vo;

import com.qtj.manageserver.entity.SysRole;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3329067027884444830L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<SysRoleVO> roleList = new ArrayList<>();
}
