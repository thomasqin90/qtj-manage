package com.qtj.manageserver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

// 分页参数
@Data
public class PageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -337318596831831088L;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
