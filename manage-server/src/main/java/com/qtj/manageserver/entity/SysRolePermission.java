package com.qtj.manageserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -7732219110736240250L;

    @TableId(type= IdType.AUTO)
    private Long id;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;
}
