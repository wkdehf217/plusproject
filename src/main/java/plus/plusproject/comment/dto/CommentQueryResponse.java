package plus.plusproject.comment.dto;

import plus.plusproject.comment.entity.CommentLike;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentQueryResponse {
    private final CommentLike commentLike;
    private final boolean myLike;

    @Builder
    public CommentQueryResponse(CommentLike commentLike, boolean myLike) {
        this.commentLike = commentLike;
        this.myLike = myLike;
    }
}