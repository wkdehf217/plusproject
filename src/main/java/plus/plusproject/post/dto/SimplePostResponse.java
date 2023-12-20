package plus.plusproject.post.dto;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import plus.plusproject.post.entity.Post;
import plus.plusproject.user.entity.User;

@Getter
public class SimplePostResponse {

    private final String writer;
    private final String title;
    private final String content;
    private final String lastModifiedDate;

    public SimplePostResponse(Post post) {

        User postUser = post.getUser();

        this.writer = postUser.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.lastModifiedDate = post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}