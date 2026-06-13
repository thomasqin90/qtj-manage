package com.qtj.manageserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class SysRoleAssignDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8268443143083897322L;

    @NotNull
    private Long roleId;

    private List<Long> permissionIds;
}
