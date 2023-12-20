package plus.plusproject.post.service;

import java.io.File;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import plus.plusproject.post.dto.PostRequest;
import plus.plusproject.post.dto.PostResponse;
import plus.plusproject.post.entity.PostLike;
import plus.plusproject.user.entity.User;
import plus.plusproject.post.repository.PostLikeRepository;
import plus.plusproject.post.repository.PostQueryRepository;
import plus.plusproject.post.repository.PostRepository;
import plus.plusproject.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final PostLikeRepository postLikeRepository;

    /**
     * Service for post
     * */
    @Transactional
    public Post createPost(User user, PostRequest request) {

        return postRepository.save(
                Post.builder()
                        .user(user)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build()
        );
    }

    @Transactional
    public Post updatePost(User user, PostRequest request, Long postId) {
        Post post = postRepository.findByIdAndUser(postId, user)
                .orElseThrow(NoSuchElementException::new);
        return post.update(request.getTitle(), request.getContent());
    }

    @Transactional
    public void deletePost(User user, Long postId) {
        Post post = postRepository.findByIdAndUser(postId, user)
                .orElseThrow(NoSuchElementException::new);
        postRepository.delete(post);
    }

    public PostResponse readPost(Long userId, Long postId) {
//        List<PostQueryResponse> res = postQueryRepository.readPost(userId, postId);
//        return PostResponse.builder()
//                .res(res.get(0))
//                .likeCount(res.size())
//                .build();

        Post findPost = postRepository.findById(postId)
                .orElseThrow(NoSuchElementException::new);
        List<PostLike> findPostLike = postLikeRepository.findByPost_IdOrderByCreatedAtDesc(postId);
        boolean myLike =
                !postLikeRepository.findByPost_IdAndUser_Id(postId, userId).isEmpty();

        return new PostResponse(findPost, findPostLike, myLike);
    }

    public Page<Post> readPostAll(Pageable pageable) {

        return postRepository.findAll(pageable);
    }

    public List<PostResponse> readPostAllByUser(Long userId, Long targetUserId) {
        List<Post> posts = postRepository.findAllByUser_Id(targetUserId);
        List<PostResponse> response = new ArrayList<>();

        for (Post post : posts) {
            List<PostLike> findPostLike = postLikeRepository.findByPost_IdOrderByCreatedAtDesc(post.getId());
            boolean myLike = !postLikeRepository.findByPost_IdAndUser_Id(post.getId(), userId)
                    .isEmpty();
            response.add(new PostResponse(post, findPostLike, myLike));
        }

        return response;
    }



    /**
     *  Service for postLike
     * */
    @Transactional
    public PostLike createPostLike(User user, Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(NoSuchElementException::new);

        return postLikeRepository.save(
                PostLike.builder()
                    .user(user)
                    .post(findPost)
                    .build()
        );
    }

    @Transactional
    public void deletePostLike (User user, Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(NoSuchElementException::new);
        postLikeRepository.deletePostLikeByUserAndPost(user, findPost);
    }

    @Transactional
    public void uploadFile(User user, Long postId, MultipartFile multipartFile) throws IOException {

        String fullPath="";
        if(!multipartFile.isEmpty()){
            fullPath = "C:/Users/wkdeh/OneDrive/바탕 화면/spartaSpring/" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(fullPath));
        }

        Post post = postRepository.findByIdAndUser(postId, user)
                .orElseThrow(NoSuchElementException::new);

        post.uploadFile(fullPath);

    }
}
