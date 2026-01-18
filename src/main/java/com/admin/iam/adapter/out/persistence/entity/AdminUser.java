package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "admin_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_admin_user_admin_id", columnNames = "admin_id")
    }
)
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no")
    private Long adminNo;

    @Column(name = "admin_id", nullable = false, length = 20)
    private String adminId;

    @Column(name="group_no", nullable=false)
    private Long groupNo;

    @Column(name = "admin_name", nullable = false, length = 20)
    private String adminName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "status", nullable = false, length = 3)
    private String status;

    @Column(name = "remark", length = 2000)
    private String remark;

    // admin_user(N) -> admin_group(1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_no", insertable = false, updatable = false)
    private AdminGroup adminGroup;

    protected AdminUser() {}

    // ===== getter/setter =====
    public Long getAdminNo() { return adminNo; }
    public String getAdminId() { return adminId; }
    public String getAdminName() { return adminName; }
    public String getPassword() { return password; }
    public String getStatus() { return status; }
    public String getRemark() { return remark; }
    public Long getGroupNo() { return groupNo; }
    public AdminGroup getAdminGroup() { return adminGroup; }

    public void setAdminId(String adminId) { this.adminId = adminId; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
    public void setPassword(String password) { this.password = password; }
    public void setStatus(String status) { this.status = status; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setAdminGroup(AdminGroup adminGroup) { this.adminGroup = adminGroup; }
}
