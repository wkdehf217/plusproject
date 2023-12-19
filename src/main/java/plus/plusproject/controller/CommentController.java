package plus.plusproject.controller;


import plus.plusproject.dto.comment.CommentRequestDto;
import plus.plusproject.etc.response.ApiResponse;
import plus.plusproject.security.UserDetailsImpl;
import plus.plusproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> getComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(ApiResponse.ok(
                commentService.getComment(userDetails.getUser().getId(), postId)
        ));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> getCommentDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable("postId") Long postId,
                                                        @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(ApiResponse.ok(
                commentService.getCommentDetail(userDetails.getUser().getId(),postId,commentId)
        ));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable("postId") Long postId,
                                                     @RequestBody CommentRequestDto requestDto) {
        commentService.createComment(userDetails.getUser(), postId, requestDto);
        return ResponseEntity.ok(ApiResponse.ok("게시글 번호 " + postId + " 에 댓글이 달렸습니다."));
    }

    @PostMapping("/{postId}/{parentCommentId}/comments")
    public ResponseEntity<ApiResponse> createReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable("postId") Long postId,
                                                   @PathVariable("parentCommentId") Long parentCommentId,
                                                   @RequestBody CommentRequestDto requestDto) {
        commentService.createReply(userDetails.getUser(), postId, parentCommentId, requestDto);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + parentCommentId + " 에 댓글이 달렸습니다."));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId,
                                                     @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 가 업데이트 되었습니다."));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 가 삭제 되었습니다."));
    }

    @PostMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> likeComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable("postId") Long postId,
                                                   @PathVariable("commentId") Long commentId) {
        commentService.createCommentLike(userDetails.getUser(), commentId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 에 좋아요 가 적용되었습니다."));
    }

    @DeleteMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> deleteLikeComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable("postId") Long postId,
                                                         @PathVariable("commentId") Long commentId) {
        commentService.deleteCommentLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 에 좋아요 가 해제되었습니다."));
    }
}
