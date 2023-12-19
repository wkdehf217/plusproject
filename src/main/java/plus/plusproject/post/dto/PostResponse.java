package plus.plusproject.post.dto;

import plus.plusproject.post.entity.Post;
import plus.plusproject.post.entity.PostLike;
import plus.plusproject.user.entity.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class PostResponse {
    private final Long postId;
    private final Long writerId;
    private final String writer;
    private final String title;
    private final String content;
    private final int likeCount;
    private final String recentLikeUser;
    private final boolean myLike;
    private final String lastModifiedDate;

    public PostResponse(PostQueryResponse res, int likeCount) {
        Post post = res.getPostLike().getPost();
        User user = res.getPostLike().getUser();
        User postUser = post.getUser();

        this.postId = post.getId();
        this.writerId = postUser.getId();
        this.writer = postUser.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = likeCount;
        this.recentLikeUser = user.getUsername();
        this.myLike = res.isMyLike();
        this.lastModifiedDate = post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public PostResponse(Post post, List<PostLike> postLike, Boolean myLike) {
        this.postId = post.getId();
        this.writerId = post.getUser().getId();
        this.writer = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = postLike.size();
        this.recentLikeUser = postLike.size() == 0 ? null : postLike.get(0).getUser().getUsername();
        this.myLike = myLike;
        this.lastModifiedDate = post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}