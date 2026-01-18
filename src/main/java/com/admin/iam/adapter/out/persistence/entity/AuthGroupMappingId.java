package com.admin.iam.adapter.out.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

public class AuthGroupMappingId implements Serializable {

    private Long authGroupNo;
    private Long authNo;

    public AuthGroupMappingId() {}

    public AuthGroupMappingId(Long authGroupNo, Long authNo) {
        this.authGroupNo = authGroupNo;
        this.authNo = authNo;
    }

    public Long getAuthGroupNo() { return authGroupNo; }
    public void setAuthGroupNo(Long authGroupNo) { this.authGroupNo = authGroupNo; }

    public Long getAuthNo() { return authNo; }
    public void setAuthNo(Long authNo) { this.authNo = authNo; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthGroupMappingId that)) return false;
        return Objects.equals(authGroupNo, that.authGroupNo)
                && Objects.equals(authNo, that.authNo);
    }
    @Override public int hashCode() {
        return Objects.hash(authGroupNo, authNo);
    }
}
