package com.admin.iam.adapter.out.redis;

import com.admin.iam.adapter.out.persistence.query.GroupAuthRow;
import com.admin.iam.adapter.out.persistence.query.GroupAuthRowQueryRepository;
import com.admin.iam.adapter.out.redis.dto.GroupAuthCachePayload;
import com.admin.iam.adapter.out.redis.dto.MenuNode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.*;

@Service
public class GroupAuthCacheService {

    private final GroupAuthRowQueryRepository repo;
    private final StringRedisTemplate redis;
    private final ObjectMapper om;

    public GroupAuthCacheService(GroupAuthRowQueryRepository repo, StringRedisTemplate redis, ObjectMapper om) {
        this.repo = repo;
        this.redis = redis;
        this.om = om;
    }

    public GroupAuthCachePayload getOrLoadPayload(Long groupNo) {
        String key = "iam:grp:" + groupNo;
        String raw = redis.opsForValue().get(key);

        if (raw == null || raw.isBlank()) {
            warmUp(groupNo); // 없으면 DB 조회해서 채움
            raw = redis.opsForValue().get(key);
        }

        if (raw == null || raw.isBlank()) {
            throw new IllegalStateException("Redis cache missing even after warmUp: " + key);
        }

        try {
            return om.readValue(raw, GroupAuthCachePayload.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse cache payload: " + key, e);
        }
    }

    public String warmUp(Long groupNo) {
        List<GroupAuthRow> rows = repo.findRowsByGroupNo(groupNo);

        // 1) perm map (중복 제거)
        Map<String, Set<String>> perm = new HashMap<>();
        for (GroupAuthRow r : rows) {
            perm.computeIfAbsent(r.menuId(), k -> new HashSet<>()).add(r.actionKey());
        }

        // 2) menu nodes (menuNo 기준 중복 제거)
        Map<Long, MenuNode> nodeMap = new HashMap<>();
        for (GroupAuthRow r : rows) {
            nodeMap.computeIfAbsent(r.menuNo(), k ->
                    new MenuNode(r.menuNo(), r.upperMenuNo(), r.menuId(), r.menuName(), r.menuPath())
            );
        }

        // 3) tree build
        List<MenuNode> roots = new ArrayList<>();
        for (MenuNode n : nodeMap.values()) {
            if (n.upperMenuNo == null) {
                roots.add(n);
            } else {
                MenuNode parent = nodeMap.get(n.upperMenuNo);
                if (parent != null) parent.children.add(n);
                else roots.add(n); // 부모가 없으면 루트로(데모 안전장치)
            }
        }

        // 4) 정렬(데모용: menuId 기준)
        sortTreeByMenuId(roots);

        // 5) payload → json → redis 저장
        GroupAuthCachePayload payload = new GroupAuthCachePayload(groupNo, perm, roots);

        try {
            String key = redisKey(groupNo);
            String json = om.writeValueAsString(payload);
            redis.opsForValue().set(key, json, Duration.ofMinutes(30)); // 데모 TTL
            return key;
        } catch (Exception e) {
            throw new RuntimeException("Redis warmUp failed", e);
        }
    }

    public String getRaw(Long groupNo) {
        return redis.opsForValue().get(redisKey(groupNo));
    }

    private String redisKey(Long groupNo) {
        return "iam:grp:" + groupNo;
    }

    private void sortTreeByMenuId(List<MenuNode> nodes) {
        nodes.sort(Comparator.comparing(n -> n.menuId == null ? "" : n.menuId));
        for (MenuNode n : nodes) {
            if (n.children != null && !n.children.isEmpty()) {
                sortTreeByMenuId(n.children);
            }
        }
    }
}
