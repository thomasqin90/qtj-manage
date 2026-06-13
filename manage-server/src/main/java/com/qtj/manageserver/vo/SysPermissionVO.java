package com.qtj.manageserver.vo;

import com.qtj.manageserver.dto.SysPermissionDTO;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysPermissionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -751749439325455892L;

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

    private List<SysPermissionVO> children = new ArrayList<>();
}
