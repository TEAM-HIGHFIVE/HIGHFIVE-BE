package advancedweb.project.boardservice.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {

    @Id
    private String commentNo;

    private String postNo;
    private String userNo;
    private String content;
    private LocalDateTime createdAt;
}
