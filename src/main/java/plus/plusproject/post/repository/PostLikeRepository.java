package plus.plusproject.post.repository;

import plus.plusproject.user.entity.User;
import plus.plusproject.post.entity.Post;
import plus.plusproject.post.entity.PostLike;
import plus.plusproject.post.entity.PostLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {
    void deletePostLikeByUserAndPost(User user, Post post);

    List<PostLike> findByPost_IdOrderByCreatedAtDesc(Long postId);

    List<PostLike> findByPost_IdAndUser_Id(Long postId, Long userId);
}