package com.admin.iam.adapter.out.persistence.query;

public record GroupAuthRow(
        Long menuNo,
        Long upperMenuNo,
        String menuId,
        String menuName,
        String menuPath,
        String actionKey
) {}
