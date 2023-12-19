package plus.plusproject.repository.comment;

import plus.plusproject.entity.comment.CommentLike;
import plus.plusproject.entity.comment.CommentLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeKey> {
}
