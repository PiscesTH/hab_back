package com.th.hab.Repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.th.hab.History.model.HistoryTotalVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.th.hab.entity.QHistory.history;

@Slf4j
@RequiredArgsConstructor
public class HistoryQdslRepositoryImpl extends HistorySearchCondition implements HistoryQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HistoryTotalVo> selHistoryGroupTotal() {
        JPAQuery<HistoryTotalVo> query = jpaQueryFactory.select(Projections.fields(HistoryTotalVo.class,
                        history.category.category.as("name"), history.amount.sum().as("total")
                ))
                .from(history)
                .groupBy(history.category)
                .where(whereIcategoryNeq(5));
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
