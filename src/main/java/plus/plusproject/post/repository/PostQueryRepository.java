package plus.plusproject.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import plus.plusproject.post.dto.PostQueryResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import plus.plusproject.post.entity.QPost;
import plus.plusproject.post.entity.QPostLike;
import plus.plusproject.user.entity.QUser;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final EntityManager em;

    public List<PostQueryResponse> readPost(Long userId, Long postId) {
        QPostLike postLike = new QPostLike("postLike");

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(postLike.post.id.eq(postId));

        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
        .select(Projections.constructor(PostQueryResponse.class,
                    postLike,
                    ExpressionUtils.as(
                        JPAExpressions.selectOne()
                            .from(postLike.user)
                            .where(postLike.user.id.eq(userId)).exists(), "myLike")
        ))
        .from(postLike)
        .innerJoin(postLike.post, new QPost("post")).fetchJoin()
        .innerJoin(postLike.user, new QUser("user")).fetchJoin()
        .innerJoin(postLike.post.user, new QUser("postUser")).fetchJoin()
        .where(builder)
        .orderBy(postLike.createdAt.desc())
        .fetch();
    }
}
