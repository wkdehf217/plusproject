package plus.plusproject.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeKey implements Serializable {

    @Column(name = "user_id",nullable = true)
    private Long userId;

    @Column(name = "comment_id",nullable = true)
    private Long commentId;

    @Builder
    public CommentLikeKey(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
