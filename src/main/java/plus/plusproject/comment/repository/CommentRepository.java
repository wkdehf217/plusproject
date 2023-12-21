package plus.plusproject.comment.repository;

import org.springframework.data.domain.Pageable;
import plus.plusproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost_Id(Long postId);

    List<Comment> findAllByPostId(Long postId, Pageable pageable);
}
