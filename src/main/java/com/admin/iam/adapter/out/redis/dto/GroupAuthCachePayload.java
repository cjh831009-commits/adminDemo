package com.admin.iam.adapter.out.redis.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record GroupAuthCachePayload(
        Long groupNo,
        Map<String, Set<String>> perm,
        List<MenuNode> menuTree
) {}
