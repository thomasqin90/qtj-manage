package com.qtj.manageserver.bo;

import com.qtj.manageserver.entity.SysRole;
import com.qtj.manageserver.entity.SysUser;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUserBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 5487747555065625640L;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<SysRole> roleList;
}
