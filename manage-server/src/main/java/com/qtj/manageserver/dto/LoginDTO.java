package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -679472574107732982L;

    private String username;

    private String password;
}
