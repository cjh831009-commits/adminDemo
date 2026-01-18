package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_group_mapping")
@IdClass(AuthGroupMappingId.class)
public class AuthGroupMapping {

    @Id
    @Column(name = "auth_group_no", nullable = false)
    private Long authGroupNo;

    @Id
    @Column(name = "auth_no", nullable = false)
    private Long authNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_group_no", referencedColumnName = "auth_group_no",
            insertable = false, updatable = false)
    private AdminAuthGroup authGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_no", referencedColumnName = "auth_no",
            insertable = false, updatable = false)
    private AdminAuth auth;

    protected AuthGroupMapping() {}

    public AuthGroupMapping(Long authGroupNo, Long authNo) {
        this.authGroupNo = authGroupNo;
        this.authNo = authNo;
    }

    public Long getAuthGroupNo() { return authGroupNo; }
    public Long getAuthNo() { return authNo; }
    public AdminAuthGroup getAuthGroup() { return authGroup; }
    public AdminAuth getAuth() { return auth; }
}
