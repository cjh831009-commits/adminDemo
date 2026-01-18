package com.admin.iam.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "menu_info",
        uniqueConstraints = @UniqueConstraint(name = "uq_menu_info_menu_id", columnNames = "menu_id")
)
public class MenuInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_no")
    private Long menuNo;

    @Column(name = "upper_menu_no")
    private Long upperMenuNo;

    @Column(name = "menu_id", nullable = false, length = 20)
    private String menuId;

    @Column(name = "menu_name", nullable = false, length = 20)
    private String menuName;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "remark", length = 2000)
    private String remark;

    @Column(name = "menu_path", length = 255)
    private String menuPath;

    // self reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_menu_no", insertable = false, updatable = false)
    private MenuInfo upperMenu;

    @OneToMany(mappedBy = "upperMenu", fetch = FetchType.LAZY)
    private List<MenuInfo> children = new ArrayList<>();

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuActionInfo> actions = new ArrayList<>();

    protected MenuInfo() {}

    public Long getMenuNo() { return menuNo; }
    public String getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public String getUseYn() { return useYn; }
    public String getRemark() { return remark; }
    public String getMenuPath() { return menuPath; }
    public MenuInfo getUpperMenu() { return upperMenu; }
    public Long getUpperMenuNo() { return upperMenuNo; }

    public void setMenuId(String menuId) { this.menuId = menuId; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setMenuPath(String menuPath) { this.menuPath = menuPath; }
    public void setUpperMenuNo(Long upperMenuNo) { this.upperMenuNo = upperMenuNo; }
}
