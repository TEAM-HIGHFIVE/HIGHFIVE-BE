package advancedweb.project.boardservice.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "comments") // MongoDB 컬렉션 이름 지정
public class Comment {

    @Id
    private String id;

    private String postNo;
    private String userNo;
    private String content;

    private LocalDateTime createdAt;

    @Builder
    public Comment(String postNo, String userNo, String content, LocalDateTime createdAt) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.content = content;
        this.createdAt = createdAt;
    }
}
