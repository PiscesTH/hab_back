package com.th.hab.Repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;

import static com.th.hab.entity.QHistory.history;

public abstract class HistorySearchCondition {
//    protected BooleanExpression whereUnregisteredFl(long unregisteredFl) {
//        return userEntity.unregisterFl.eq(unregisteredFl);
//    }
//
//    protected BooleanExpression likeNm(String nm) {
//        return StringUtils.hasText(nm) ? userEntity.nm.contains(nm) : null;
//    }

//    protected BooleanExpression goeCreatedAt(LocalDateTime condition) {
//        return condition == null ? null : userEntity.createdAt.goe(condition);
//    }

    protected StringTemplate transformDate(Object object) {
        return Expressions.stringTemplate("DATE_FORMAT({0},'{1s}')", object, ConstantImpl.create("%Y-%m-%d"));
    }

    protected BooleanExpression whereIcategoryNeq(long icategory) {
        return history.category.icategory.ne(icategory);
    }

//    protected BooleanExpression whereDate
}
