package com.admin.iam.adapter.out.persistence.query;

import com.admin.iam.adapter.out.persistence.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupAuthRowQueryRepositoryImpl implements GroupAuthRowQueryRepository{

    private final JPAQueryFactory qf;

    public GroupAuthRowQueryRepositoryImpl(JPAQueryFactory qf) {
        this.qf = qf;
    }


    @Override
    public List<GroupAuthRow> findRowsByGroupNo(Long groupNo) {
        QAdminAuthGroupMapping aagm = QAdminAuthGroupMapping.adminAuthGroupMapping;
        QAuthGroupMapping agm = QAuthGroupMapping.authGroupMapping;
        QAuthMenuMapping amm = QAuthMenuMapping.authMenuMapping;
        QMenuInfo menu = QMenuInfo.menuInfo;
        QMenuActionInfo action = QMenuActionInfo.menuActionInfo;

        return qf
                .select(menu.menuNo, menu.upperMenuNo, menu.menuId, menu.menuName, menu.menuPath, action.actionKey)
                .from(aagm)
                .join(agm).on(agm.authGroupNo.eq(aagm.authGroupNo))
                .join(amm).on(amm.authNo.eq(agm.authNo))
                .join(menu).on(menu.menuNo.eq(amm.menuNo))
                .join(action).on(action.actionNo.eq(amm.actionNo))
                .where(aagm.groupNo.eq(groupNo))
                .fetch()
                .stream()
                .map(t -> new GroupAuthRow(
                        t.get(menu.menuNo),
                        t.get(menu.upperMenuNo),
                        t.get(menu.menuId),
                        t.get(menu.menuName),
                        t.get(menu.menuPath),
                        t.get(action.actionKey)
                ))
                .toList();
    }
}
