package advancedweb.project.aiengineservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotRequest {
    private Long CurrentWelfarePk;
    private String CurrentWelfareName;
    private String supportTarget;
    private String selectionCriteria;
    private String WelfareContent;
    private String applicationMethod;
    private String question;
}
