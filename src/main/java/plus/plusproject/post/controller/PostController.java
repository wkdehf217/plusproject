package plus.plusproject.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import plus.plusproject.global.response.ApiResponse;
import plus.plusproject.post.dto.PostPageRequestDto;
import plus.plusproject.post.service.PostService;
import plus.plusproject.global.security.userdetails.UserDetailsImpl;
import plus.plusproject.post.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    /**
     * Controller for post
     * */
    @PostMapping("/posts")
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                           @RequestBody PostRequest request) {
        postService.createPost(userDetailsImpl.getUser(), request);
    }

    @PatchMapping("/posts/{postId}")
    public void updatePost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                           @RequestBody PostRequest request, @PathVariable("postId") Long postId) {
        postService.updatePost(userDetailsImpl.getUser(), request, postId);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                           @PathVariable("postId") Long postId) {
        postService.deletePost(userDetailsImpl.getUser(), postId);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> readPost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                               @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(ApiResponse.ok(
                postService.readPost(userDetailsImpl.getUser().getId(), postId)
        ));
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<ApiResponse> readPostAllByUser(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                         @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(ApiResponse.ok(
                postService.readPostAllByUser(userDetailsImpl.getUser().getId(), userId)
        ));
    }

//    @GetMapping("/posts")
//    public ResponseEntity<ApiResponse> readPostAll(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
//        return ResponseEntity.ok(ApiResponse.ok(
//                postService.readPostAll(userDetailsImpl.getUser().getId())
//        ));
//    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> readPostAll(final Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(
                postService.readPostAll(pageable)
        ));
    }

    /**
     * Controller for postLike
     * */
    @PostMapping("/posts/{postId}/likes")
    public void createPostLike(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                               @PathVariable("postId") Long postId) {
        postService.createPostLike(userDetailsImpl.getUser(), postId);
    }

    @DeleteMapping("/posts/{postId}/likes")
    public void deletePostLike(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                               @PathVariable("postId") Long postId) {
        postService.deletePostLike(userDetailsImpl.getUser(), postId);
    }
}