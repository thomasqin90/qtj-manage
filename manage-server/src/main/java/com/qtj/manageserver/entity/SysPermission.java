package com.qtj.manageserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -4238302539393765763L;

//    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
//            `permission_name` varchar(50) NOT NULL COMMENT '鏉冮檺鍚',
//            `permission_code` varchar(50) NOT NULL COMMENT '鏉冮檺缂栫爜',
//            `description` varchar(255) DEFAULT NULL COMMENT '鎻忚堪',
//            `permission_type` tinyint NOT NULL COMMENT '鏉冮檺绫诲瀷',
//            `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '鐖舵潈闄怚D',
//            `path` varchar(255) DEFAULT NULL COMMENT '璺?敱璺?緞',
//            `component` varchar(255) DEFAULT NULL COMMENT '缁勪欢鐨勮矾寰',
//            `icon` varchar(255) DEFAULT NULL COMMENT '鍥炬爣',
//            `status` tinyint NOT NULL DEFAULT '1' COMMENT '鐘舵?锛?=绂佺敤銆?=鍚?敤锛',
//            `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '鏄?惁鍒犻櫎锛?=鏈?垹闄ゃ?1=宸插垹闄わ級',
//            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
//            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("permission_name")
    private String permissionName;

    @TableField("permission_code")
    private String permissionCode;

    @TableField
    private String description;

    @TableField("permission_type")
    private String permissionType;

    @TableField("parent_id")
    private Long parentId;

    @TableField
    private String path;

    @TableField
    private String component;

    @TableField
    private String icon;

    @TableField
    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;
}
