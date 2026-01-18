package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin_auth_group")
public class AdminAuthGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_group_no")
    private Long authGroupNo;

    @Column(name = "auth_group_name", nullable = false, length = 20)
    private String authGroupName;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    // admin_auth_group (1) -> auth_group_mapping (N)
    @OneToMany(mappedBy = "authGroup", fetch = FetchType.LAZY)
    private List<AuthGroupMapping> authMappings = new ArrayList<>();

    protected AdminAuthGroup() {}

    public Long getAuthGroupNo() { return authGroupNo; }
    public String getAuthGroupName() { return authGroupName; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }

    public void setAuthGroupName(String authGroupName) { this.authGroupName = authGroupName; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
}
