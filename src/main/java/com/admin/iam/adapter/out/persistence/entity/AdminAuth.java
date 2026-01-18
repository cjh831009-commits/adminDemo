package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin_auth")
public class AdminAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_no")
    private Long authNo;

    @Column(name = "auth_name", nullable = false, length = 20)
    private String authName;

    // admin_auth (1) -> auth_group_mapping (N)
    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
    private List<AuthGroupMapping> authGroupMappings = new ArrayList<>();

    // admin_auth (1) -> auth_menu_mapping (N)
    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
    private List<AuthMenuMapping> authMenuMappings = new ArrayList<>();

    protected AdminAuth() {}

    public Long getAuthNo() { return authNo; }
    public String getAuthName() { return authName; }

    public void setAuthName(String authName) { this.authName = authName; }
}
