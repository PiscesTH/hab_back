package com.th.hab.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.th.hab.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    protected BooleanExpression whereUserEq(User user) {
        return history.user.eq(user);
    }

    protected BooleanExpression whereIcategoryNeq(long icategory) {
        return history.category.icategory.ne(icategory);
    }

    protected BooleanExpression whereDateGt(long day) {
        LocalDate today = LocalDate.now();
        LocalDate tmpTarget = today.minusDays(7);
        LocalDateTime target = tmpTarget.atStartOfDay();
        return history.date.gt(target);
    }

    protected BooleanExpression whereMonthEq(int month) {
        return history.date.month().eq(month);
    }
}
