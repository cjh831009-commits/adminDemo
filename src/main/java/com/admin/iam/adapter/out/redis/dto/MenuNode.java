package com.admin.iam.adapter.out.redis.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuNode {
    public Long menuNo;
    public Long upperMenuNo;
    public String menuId;
    public String menuName;
    public String menuPath;
    public List<MenuNode> children = new ArrayList<>();

    public MenuNode(Long menuNo, Long upperMenuNo, String menuId, String menuName, String menuPath) {
        this.menuNo = menuNo;
        this.upperMenuNo = upperMenuNo;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPath = menuPath;
    }
}
