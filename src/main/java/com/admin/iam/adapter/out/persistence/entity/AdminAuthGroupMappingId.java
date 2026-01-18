package com.admin.iam.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

public class AdminAuthGroupMappingId implements Serializable {

    private Long groupNo;
    private Long authGroupNo;

    public AdminAuthGroupMappingId() {}

    public AdminAuthGroupMappingId(Long groupNo, Long authGroupNo) {
        this.groupNo = groupNo;
        this.authGroupNo = authGroupNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminAuthGroupMappingId that)) return false;
        return Objects.equals(groupNo, that.groupNo)
                && Objects.equals(authGroupNo, that.authGroupNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNo, authGroupNo);
    }
}
