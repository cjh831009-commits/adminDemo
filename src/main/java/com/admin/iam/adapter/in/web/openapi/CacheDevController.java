package com.admin.iam.adapter.in.web.openapi;

import com.admin.iam.adapter.out.redis.GroupAuthCacheService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/cache")
public class CacheDevController {

    private final GroupAuthCacheService cacheService;

    public CacheDevController(GroupAuthCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/warmup")
    public String warmup(@RequestParam Long groupNo) {
        return cacheService.warmUp(groupNo); // redis key 반환
    }

    @GetMapping("/raw")
    public String raw(@RequestParam Long groupNo) {
        return cacheService.getRaw(groupNo); // 저장된 json 확인용
    }
}
