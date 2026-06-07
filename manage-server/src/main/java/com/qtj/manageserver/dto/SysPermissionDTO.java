package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysPermissionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -525335197757708439L;

    private Long id;

    private String permissionName;

    private String permissionCode;

    private String description;

    private String permissionType;

    private Long parentId;

    private String path;

    private String component;

    private String icon;

    private Integer status;
}
