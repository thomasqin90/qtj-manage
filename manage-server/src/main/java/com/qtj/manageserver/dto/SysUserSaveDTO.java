package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

// 用户保存
@Data
public class SysUserSaveDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2532142153662512098L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;
    // 角色ID集合
    private List<Long> roleIdList;
}
