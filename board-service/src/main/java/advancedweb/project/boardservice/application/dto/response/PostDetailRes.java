package advancedweb.project.boardservice.application.dto.response;

import advancedweb.project.boardservice.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailRes(
        String postNo,
        String title,   // 게시물 제목
        String content, // 게시물 내용
        LocalDateTime createdAt,    // 작성일
        List<CommentRes> comments,   // 댓글 목록
        Boolean isMine
) {
    public static PostDetailRes create(Post post, List<CommentRes> comments, String userNo) {
        return new PostDetailRes(
                post.getPostNo(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                comments,
                userNo.equals(post.getWriterNo())
        );
    }
}