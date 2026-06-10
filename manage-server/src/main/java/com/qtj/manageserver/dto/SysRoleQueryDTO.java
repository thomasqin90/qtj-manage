package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysRoleQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8589308996668048406L;

    private String roleName;

    private String roleCode;
}
