package advancedweb.project.aiengineservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotRequest {
    private String currentWelfarePk;
    private String currentWelfareName;
    private String supportTarget;
    private String selectionCriteria;
    private String welfareContent;
    private String applicationMethod;
    private String userQuestion;
}
