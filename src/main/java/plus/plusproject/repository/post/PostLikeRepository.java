package plus.plusproject.repository.post;

import plus.plusproject.entity.user.User;
import plus.plusproject.entity.post.Post;
import plus.plusproject.entity.post.PostLike;
import plus.plusproject.entity.post.PostLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {
    void deletePostLikeByUserAndPost(User user, Post post);

    List<PostLike> findByPost_IdOrderByCreatedAtDesc(Long postId);

    List<PostLike> findByPost_IdAndUser_Id(Long postId, Long userId);
}