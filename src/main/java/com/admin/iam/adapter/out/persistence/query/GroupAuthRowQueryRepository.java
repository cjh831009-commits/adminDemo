package com.admin.iam.adapter.out.persistence.query;

import java.util.List;

public interface GroupAuthRowQueryRepository {
    List<GroupAuthRow> findRowsByGroupNo(Long groupNo);
}
