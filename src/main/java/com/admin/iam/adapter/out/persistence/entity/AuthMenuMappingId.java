package com.admin.iam.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

public class AuthMenuMappingId implements Serializable {
    private Long authNo;
    private Long menuNo;
    private Long actionNo;

    public AuthMenuMappingId() {}
    public AuthMenuMappingId(Long authNo, Long menuNo, Long actionNo) {
        this.authNo = authNo;
        this.menuNo = menuNo;
        this.actionNo = actionNo;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthMenuMappingId that)) return false;
        return Objects.equals(authNo, that.authNo)
                && Objects.equals(menuNo, that.menuNo)
                && Objects.equals(actionNo, that.actionNo);
    }

    @Override public int hashCode() { return Objects.hash(authNo, menuNo, actionNo); }
}
