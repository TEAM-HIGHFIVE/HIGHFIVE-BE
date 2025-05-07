package advancedweb.project.boardservice.application.dto.response;

import advancedweb.project.boardservice.domain.entity.Comment;

import java.time.LocalDateTime;

public record CommentRes (
        String commentNo,
        String content,     // 댓글 내용
        Boolean isMine,
        LocalDateTime createdAt     // 댓글 작성일
) {
    public static CommentRes create(Comment comment, String userNo) {
        return new CommentRes(
                comment.getCommentNo(),
                comment.getContent(),
                userNo.equals(comment.getWriterNo()),
                comment.getCreatedAt()
        );
    }
}
