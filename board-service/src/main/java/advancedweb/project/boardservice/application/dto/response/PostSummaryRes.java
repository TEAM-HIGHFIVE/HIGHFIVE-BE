package advancedweb.project.boardservice.application.dto.response;

import advancedweb.project.boardservice.domain.entity.Post;

import java.time.LocalDateTime;

public record PostSummaryRes(
        String postNo,
        String title,
        LocalDateTime createdAt
) {
    public static PostSummaryRes create(Post post) {
        return new PostSummaryRes(
                post.getPostNo(),
                post.getTitle(),
                post.getCreatedAt()
        );
    }
}
