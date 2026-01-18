package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin_group")
public class AdminGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_no")
    private Long groupNo;

    @Column(name = "group_name", nullable = false, length = 20)
    private String groupName;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    // admin_user(N) -> admin_group(1)
    @OneToMany(mappedBy = "adminGroup", fetch = FetchType.LAZY)
    private List<AdminUser> users = new ArrayList<>();

    protected AdminGroup() {}

    // ===== getter/setter =====
    public Long getGroupNo() { return groupNo; }
    public String getGroupName() { return groupName; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }
    public List<AdminUser> getUsers() { return users; }

    public void setGroupName(String groupName) { this.groupName = groupName; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
}
