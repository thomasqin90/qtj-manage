package com.qtj.manageserver.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysRoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7702066925433316729L;

    private Long id;

    private String roleName;

    private String roleCode;

    private String description;

    private Integer status;

    private List<SysPermissionVO> permissionList = new ArrayList<>();
}
