package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "menu_action_info",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_menu_action_info_menu_no_action_key",
                columnNames = {"menu_no", "action_key"}
        )
)
public class MenuActionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_no")
    private Long actionNo;

    @Column(name = "action_key", nullable = false, length = 20)
    private String actionKey;

    @Column(name = "action_name", nullable = false, length = 20)
    private String actionName;

    @Column(name = "api_url", length = 2000)
    private String apiUrl;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_no", nullable = false)
    private MenuInfo menu;

    protected MenuActionInfo() {}

    public Long getActionNo() { return actionNo; }
    public String getActionKey() { return actionKey; }
    public String getActionName() { return actionName; }
    public String getApiUrl() { return apiUrl; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }
    public MenuInfo getMenu() { return menu; }

    public void setActionKey(String actionKey) { this.actionKey = actionKey; }
    public void setActionName(String actionName) { this.actionName = actionName; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setMenu(MenuInfo menu) { this.menu = menu; }
}
