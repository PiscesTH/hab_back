package com.th.hab.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.th.hab.history.model.HistoryTotalDto;
import com.th.hab.entity.History;
import com.th.hab.entity.User;
import com.th.hab.history.model.HistoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static com.th.hab.entity.QCategory.category1;
import static com.th.hab.entity.QHistory.history;
import static com.th.hab.entity.QUser.user;


@Slf4j
@RequiredArgsConstructor
public class HistoryQdslRepositoryImpl extends HistorySearchCondition implements HistoryQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HistoryVo> findAllByUserOrderByIhistoryDescDateDesc(User loginedUser) {
        JPAQuery<HistoryVo> query = jpaQueryFactory.select(Projections.fields(HistoryVo.class,
                        history.ihistory, history.date.as("originDate"), history.amount, category1.icategory, category1.category, history.purpose
                ))
                .from(history)
                .join(history.category, category1)
                .where(whereUserEq(loginedUser))
                .orderBy(history.ihistory.desc(), history.date.desc());
        return query.fetch();
    }

    //    한달 동안의 카테고리별 지출
    @Override
    public List<HistoryTotalDto> selHistoryMonthlyTotal(User user) {
        JPAQuery<HistoryTotalDto> query = jpaQueryFactory.select(Projections.fields(HistoryTotalDto.class,
                        history.category.category.as("name"), history.amount.sum().as("total")
                ))
                .from(history)
                .where(whereMonthEq(LocalDate.now().getMonthValue()))
                .where(whereUserEq(user))
                .groupBy(history.category);
        return query.fetch();
    }

    //    일주일동안의 일 별 지출
    @Override
    public List<History> selHistoryForAWeek(User user) {
        JPAQuery<History> query = jpaQueryFactory.select(Projections.fields(History.class,
                        history.date, history.amount.sum().as("amount")
                ))
                .from(history)
                .where(whereDateGt(7))
                .where(whereIcategoryNeq(5))
                .where(whereUserEq(user))
                .groupBy(history.date)
                .orderBy(history.date.asc());

        return query.fetch();
    }
}



/*
    @Override
    public List<UserEntity> selUserAll(AdminSelAllUserDto dto, Pageable pageable) {
        JPAQuery<UserEntity> query = jpaQueryFactory.select(Projections.fields(UserEntity.class,
                        userEntity.iuser, userEntity.nm, userEntity.email,
                        userEntity.phoneNumber, userEntity.createdAt, userEntity.updatedAt
                ))
                .from(userEntity)
                .where(whereUnregisteredFl(dto.getUnregisteredFl()))
                .orderBy(userEntity.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        //검색어로 검색 - keywordType = 0 : 검색 x, 1 : 이메일 검색, 2 : 이름 검색
        query.where(dto.getKeywordType() == 0 ? null :
                dto.getKeywordType() == 1 ?
                        likeEmail(dto.getKeyword()) : likeNm(dto.getKeyword()));
        //기간으로 검색
        if (!ObjectUtils.isEmpty(dto.getBefore())) {
            query.where(ObjectUtils.isEmpty(dto.getAfter())
                    ? betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN))
                    : betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN),
                    LocalDateTime.of(dto.getAfter(), LocalTime.MAX).withNano(0)));
        }
        //전화번호 검색
        if (StringUtils.hasText(dto.getPhoneNumber())) {
            query.where(likePhoneNumber(dto.getPhoneNumber()));
        }
        return query.fetch();
    }

    @Override
    public List<UserEntity> selUserSignupStatistics(AdminSelUserSignupDto dto) {
        JPAQuery<UserEntity> query = jpaQueryFactory.select(Projections.fields(UserEntity.class,
                        userEntity.createdAt, userEntity.iuser.count().as("iuser")
                ))
                .from(userEntity)
                .orderBy(userEntity.createdAt.asc());
        if (dto.getMonth() != 0) {
            query.groupBy(transformDate(userEntity.createdAt));
            query.having(whereYear(dto.getYear()), whereMonth(dto.getMonth()));
            return query.fetch();
        }
        query.groupBy(userEntity.createdAt.year(), userEntity.createdAt.month());
        query.having(whereYear(dto.getYear()));
        return query.fetch();
    }

    @Override
    public AdminSelAllUserVo selUserAllCount(AdminSelAllUserDto dto) {
        JPAQuery<AdminSelAllUserVo> query = jpaQueryFactory.select(Projections.fields(AdminSelAllUserVo.class,
                        userEntity.iuser.count().as("totalCnt")
                ))
                .from(userEntity)
                .where(whereUnregisteredFl(dto.getUnregisteredFl()));

        query.where(dto.getKeywordType() == 0 ? null :
                dto.getKeywordType() == 1 ?
                        likeEmail(dto.getKeyword()) : likeNm(dto.getKeyword()));

        if (dto.getBefore() != null) {
            query.where(dto.getAfter() == null ? betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN))
                    : betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN), LocalDateTime.of(dto.getAfter(), LocalTime.MAX).withNano(0)));
        }
        if (StringUtils.hasText(dto.getPhoneNumber())) {
            query.where(likePhoneNumber(dto.getPhoneNumber()));
        }
        return query.fetchOne();
    }
}
*/
