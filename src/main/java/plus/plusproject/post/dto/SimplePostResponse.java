package plus.plusproject.post.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;
import plus.plusproject.comment.entity.Comment;
import plus.plusproject.post.entity.Post;
import plus.plusproject.user.entity.User;

@Getter
public class SimplePostResponse {

    private final String writer;
    private final String title;
    private final String content;
    private final String lastModifiedDate;
    private final List<Long> commentIdList;

    public SimplePostResponse(Post post,List<Long> commentIdList) {

        User postUser = post.getUser();

        this.writer = postUser.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentIdList = commentIdList;
        this.lastModifiedDate = post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}