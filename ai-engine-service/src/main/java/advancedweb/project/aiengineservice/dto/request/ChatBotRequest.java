package advancedweb.project.aiengineservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
