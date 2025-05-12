package advancedweb.project.boardservice.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String commentNo;

    @Field("post_no")
    private String postNo;

    @Field("user_no")
    private String userNo;

    @Field("content")
    private String content;

    @Field("created_at")
    private LocalDateTime createdAt;
}
