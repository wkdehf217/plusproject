package plus.plusproject.post.dto;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import plus.plusproject.post.entity.Post;

@Getter
public class PostResponseSimplify {
    private final String writer;
    private final String title;
    private final String lastModifiedDate;

    public PostResponseSimplify(Post post) {

        this.writer = post.getUser().getUsername();
        this.title = post.getTitle();
        this.lastModifiedDate = post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}