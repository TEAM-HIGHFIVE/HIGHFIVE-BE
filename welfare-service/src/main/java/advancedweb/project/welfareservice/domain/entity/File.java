package advancedweb.project.welfareservice.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
public class File {

    @Id
    private String fileNo;

    private String fileName;
}
