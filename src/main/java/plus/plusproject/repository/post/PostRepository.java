package plus.plusproject.repository.post;

import plus.plusproject.entity.user.User;
import plus.plusproject.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUser(Long postId, User user);

    List<Post> findAllByUser_Id(Long userId);
}