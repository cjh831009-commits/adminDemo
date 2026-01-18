package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "group_code_info",
        uniqueConstraints = @UniqueConstraint(name = "uq_group_code_info_group_code_id", columnNames = "group_code_id")
)
public class GroupCodeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_code_no")
    private Long groupCodeNo;

    @Column(name = "group_code_id", nullable = false, length = 20)
    private String groupCodeId;

    @Column(name = "code_name", nullable = false, length = 20)
    private String codeName;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    @OneToMany(mappedBy = "groupCode", fetch = FetchType.LAZY)
    private List<CodeInfo> codes = new ArrayList<>();

    protected GroupCodeInfo() {}

    public Long getGroupCodeNo() { return groupCodeNo; }
    public String getGroupCodeId() { return groupCodeId; }
    public String getCodeName() { return codeName; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }

    public void setGroupCodeId(String groupCodeId) { this.groupCodeId = groupCodeId; }
    public void setCodeName(String codeName) { this.codeName = codeName; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
}
