package plus.plusproject.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import plus.plusproject.user.entity.User;
import plus.plusproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUser(Long postId, User user);

    List<Post> findAllByUser_Id(Long userId);

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT title, id, lastModifiedAt FROM Post WHERE :id = :id order by :sort")
    List<Post> findAll(String id, Sort sort);
}