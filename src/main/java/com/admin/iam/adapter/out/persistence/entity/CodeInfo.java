package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "code_info",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_code_info_group_code_no_code_id",
                columnNames = {"group_code_no", "code_id"}
        )
)
public class CodeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_no")
    private Long codeNo;

    @Column(name = "code_id", nullable = false, length = 20)
    private String codeId;

    @Column(name = "code_name", nullable = false, length = 20)
    private String codeName;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_code_no", nullable = false)
    private GroupCodeInfo groupCode;

    protected CodeInfo() {}

    public Long getCodeNo() { return codeNo; }
    public String getCodeId() { return codeId; }
    public String getCodeName() { return codeName; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }
    public GroupCodeInfo getGroupCode() { return groupCode; }

    public void setCodeId(String codeId) { this.codeId = codeId; }
    public void setCodeName(String codeName) { this.codeName = codeName; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setGroupCode(GroupCodeInfo groupCode) { this.groupCode = groupCode; }
}
