package com.qtj.manageserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.dto.SysPermissionDTO;
import com.qtj.manageserver.entity.SysPermission;
import com.qtj.manageserver.entity.SysRolePermission;
import com.qtj.manageserver.mapper.SysPermissionMapper;
import com.qtj.manageserver.mapper.SysRolePermissionMapper;
import com.qtj.manageserver.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysPermissionServiceImpl
        extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {

    @Override
    public List<SysPermissionDTO> selectTree(String keyword) {
        List<SysPermission> permissionList = baseMapper.selectList(null);
        if(keyword == null || keyword.isBlank()) {
            return buildPermissionTree(permissionList);
        }
        return filterPermissionTree(permissionList, keyword);
    }

    private List<SysPermissionDTO> filterPermissionTree(List<SysPermission> list, String keyword) {
        // 匹配到的id
        Set<Long> matchIds = list.stream()
                .filter(permission->permission.getPermissionName().contains(keyword))
                .map(SysPermission::getId)
                .collect(Collectors.toSet());
        // 查询对应的父id加入容器中
        Set<Long> needIds = new HashSet<>(matchIds);
        for(Long id: matchIds) {
            findParentIds(list, id, needIds);
        }
        // 根据需要的id筛选并构建树形结构
        List<SysPermission> filterList = list.stream()
                .filter(p->needIds.contains(p.getId()))
                .toList();
        return buildPermissionTree(filterList);
    }

    /**
     * 收集父级id
     * @param list
     * @param id
     * @param set
     */
    private void findParentIds(List<SysPermission> list, Long id, Set<Long> set) {
        // 递归拿到id的父级id
        list.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    Long parentId = p.getParentId();
                    if(parentId != null && parentId != 0) {
                        set.add(parentId);
                        findParentIds(list, parentId, set);
                    }
                });
    }

    private List<SysPermissionDTO> buildPermissionTree(List<SysPermission> list) {
        List<SysPermissionDTO> tree = new ArrayList<>();
        // 辅助查询父节点
        Map<Long, SysPermissionDTO> map = new HashMap<>();
        for(SysPermission permission: list) {
            SysPermissionDTO permissionDTO = new SysPermissionDTO();
            BeanUtil.copyProperties(permission, permissionDTO);
            map.put(permissionDTO.getId(), permissionDTO);
        }
        for(SysPermission permission: list) {
            SysPermissionDTO me = map.get(permission.getId());
            SysPermissionDTO parent = map.get(permission.getParentId());
            if(parent == null) {
                // 查询不到父节点说明我就是一级节点
                tree.add(me);
            } else {
                parent.getChildren().add(me);
            }
        }
        if(tree.isEmpty()) {
            tree = new ArrayList<>(map.values());
        }
        return tree;
    }
}
