package plus.plusproject.dto.post;

import plus.plusproject.entity.post.PostLike;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostQueryResponse {
    private final PostLike postLike;
    private final boolean myLike;

    @Builder
    public PostQueryResponse(PostLike postLike, boolean myLike) {
        this.postLike = postLike;
        this.myLike = myLike;
    }
}