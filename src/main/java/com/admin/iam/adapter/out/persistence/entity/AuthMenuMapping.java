package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_menu_mapping")
@IdClass(AuthMenuMappingId.class)
public class AuthMenuMapping {

    @Id
    @Column(name = "auth_no", nullable = false)
    private Long authNo;

    @Id
    @Column(name = "menu_no", nullable = false)
    private Long menuNo;

    @Id
    @Column(name = "action_no", nullable = false)
    private Long actionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_no", referencedColumnName = "auth_no",
            insertable = false, updatable = false)
    private AdminAuth auth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_no", referencedColumnName = "menu_no",
            insertable = false, updatable = false)
    private MenuInfo menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_no", referencedColumnName = "action_no",
            insertable = false, updatable = false)
    private MenuActionInfo action;

    protected AuthMenuMapping() {}

    public AuthMenuMapping(Long authNo, Long menuNo, Long actionNo) {
        this.authNo = authNo;
        this.menuNo = menuNo;
        this.actionNo = actionNo;
    }

    public Long getAuthNo() { return authNo; }
    public Long getMenuNo() { return menuNo; }
    public Long getActionNo() { return actionNo; }
    public AdminAuth getAuth() { return auth; }
    public MenuInfo getMenu() { return menu; }
    public MenuActionInfo getAction() { return action; }
}
