package plus.plusproject.service;

import plus.plusproject.entity.comment.Comment;
import plus.plusproject.entity.comment.CommentLike;
import plus.plusproject.entity.user.User;
import plus.plusproject.dto.comment.CommentQueryResponse;
import plus.plusproject.dto.comment.CommentRequestDto;
import plus.plusproject.dto.comment.CommentResponseDto;
import plus.plusproject.entity.comment.CommentLikeKey;
import plus.plusproject.entity.post.Post;
import plus.plusproject.repository.comment.CommentLikeRepository;
import plus.plusproject.repository.comment.CommentQueryRepository;
import plus.plusproject.repository.comment.CommentRepository;
import plus.plusproject.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentQueryRepository commentQueryRepository;

    public List<CommentResponseDto> getComment(Long userId, Long postId) {
        List<Comment> comments = commentRepository.findAllByPost_Id(postId);
        List<CommentLike> commentLikes = commentLikeRepository.findAll();
        List<CommentResponseDto> response = new ArrayList<>();
        if (commentLikes.isEmpty()) {
            for (Comment comment : comments) {
                response.add(
                        CommentResponseDto.commentNoResponseDtoBuilder()
                                .comment(comment)
                                .build());
            }
        } else {
            for (Comment comment : comments) {
                List<CommentQueryResponse> res = commentQueryRepository.getCommentDetail(userId, comment.getCommentId());
                if (res.isEmpty()) {
                    response.add(
                            CommentResponseDto.commentNoResponseDtoBuilder()
                                    .comment(comment)
                                    .build());
                } else {
                    response.add(
                            CommentResponseDto.commentResponseDtoBuilder()
                                    .res(res.get(0))
                                    .likeCount(res.size())
                                    .build());
                }
            }
        }
        return response;
    }

    public CommentResponseDto getCommentDetail(Long userId, Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );

        List<CommentQueryResponse> res = commentQueryRepository.getCommentDetail(userId, postId);

        List<CommentLike> commentLikes = commentLikeRepository.findAll();
        if (commentLikes.isEmpty()) {
            return CommentResponseDto.commentNoResponseDtoBuilder()
                    .comment(comment)
                    .build();
        }
        else{
            return CommentResponseDto.commentResponseDtoBuilder()
                    .res(res.get(0))
                    .likeCount(res.size())
                    .build();
        }
    }

    @Transactional
    public Comment createComment(User user, Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        return commentRepository.save(Comment.commentBuilder()
                .user(user)
                .requestDto(requestDto)
                .post(post)
                .build());
    }

    @Transactional
    public Comment createReply(User user, Long postId, Long parentCommentId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(NoSuchElementException::new);

        return commentRepository.save(Comment.replyBuilder()
                .user(user)
                .requestDto(requestDto)
                .post(parentComment.getPost())
                .parentCommentId(parentCommentId)
                .depth(parentComment.getDepth())
                .build());
    }

    @Transactional
    public Comment updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );

        return comment.update(requestDto);
    }

    @Transactional
    public Comment deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );
        return comment.delete();
    }

    @Transactional
    public CommentLike createCommentLike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchElementException::new);

        return commentLikeRepository.save(
                CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .build()
        );
    }

    @Transactional
    public void deleteCommentLike(Long userId, Long commentId) {
        CommentLike commentLike = commentLikeRepository.findById(
                CommentLikeKey.builder()
                        .userId(userId)
                        .commentId(commentId)
                        .build()
        ).orElseThrow(NoSuchElementException::new);
        commentLikeRepository.delete(commentLike);
    }


}