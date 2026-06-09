package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysRoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4417969327462038484L;

    private Long id;

    private String roleName;

    private String roleCode;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<SysPermissionDTO> permissionList;
}
