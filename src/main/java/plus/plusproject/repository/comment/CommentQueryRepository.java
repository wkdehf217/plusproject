package plus.plusproject.repository.comment;

import plus.plusproject.dto.comment.CommentQueryResponse;
import plus.plusproject.entity.comment.QComment;
import plus.plusproject.entity.comment.QCommentLike;
import plus.plusproject.entity.post.QPost;
import plus.plusproject.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {
    private final EntityManager em;

    public List<CommentQueryResponse> getCommentDetail(Long userId, Long commentId) {
        QCommentLike commentLike = new QCommentLike("commentLike");

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(commentLike.comment.commentId.eq(commentId));

        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(Projections.constructor(CommentQueryResponse.class,
                        commentLike,
                        ExpressionUtils.as(
                                JPAExpressions.selectOne()
                                        .from(commentLike.user)
                                        .where(commentLike.user.id.eq(userId)).exists(), "myLike")
                ))
                .from(commentLike)
                .innerJoin(commentLike.comment, new QComment("comment")).fetchJoin()
                .innerJoin(commentLike.user, new QUser("user")).fetchJoin()
                .innerJoin(commentLike.comment.post, new QPost("commentPost")).fetchJoin()
                .where(builder)
                .orderBy(commentLike.createdAt.desc())
                .fetch();
    }
}