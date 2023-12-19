package plus.plusproject.comment.repository;

import plus.plusproject.comment.entity.CommentLike;
import plus.plusproject.comment.entity.CommentLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeKey> {
}
