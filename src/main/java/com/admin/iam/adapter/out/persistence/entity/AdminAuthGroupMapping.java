package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_auth_group_mapping")
@IdClass(AdminAuthGroupMappingId.class)
public class AdminAuthGroupMapping {

    @Id
    @Column(name = "group_no", nullable = false)
    private Long groupNo;

    @Id
    @Column(name = "auth_group_no", nullable = false)
    private Long authGroupNo;

    // group_no FK -> admin_group.group_no
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_no", referencedColumnName = "group_no",
            insertable = false, updatable = false)
    private AdminGroup adminGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_group_no", referencedColumnName = "auth_group_no",
            insertable = false, updatable = false)
    private AdminAuthGroup authGroup;

    /**
     * auth_group_no FK -> admin_auth_group.auth_group_no
     * 아직 AdminAuthGroup 엔티티를 안 만들었으니
     * 지금은 Long으로 두고, 엔티티 만든 뒤에 @ManyToOne으로 연결하면 됨.
     */

    protected AdminAuthGroupMapping() {}

    public AdminAuthGroupMapping(Long groupNo, Long authGroupNo) {
        this.groupNo = groupNo;
        this.authGroupNo = authGroupNo;
    }

    public Long getGroupNo() { return groupNo; }
    public Long getAuthGroupNo() { return authGroupNo; }
    public AdminGroup getAdminGroup() { return adminGroup; }
}
