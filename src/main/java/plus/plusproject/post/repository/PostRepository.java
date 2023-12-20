package plus.plusproject.post.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plus.plusproject.post.entity.Post;
import plus.plusproject.user.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUser(Long postId, User user);

    List<Post> findAllByUser_Id(Long userId);
}