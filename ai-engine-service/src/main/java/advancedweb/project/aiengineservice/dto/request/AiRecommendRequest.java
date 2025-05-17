package advancedweb.project.aiengineservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * Welfare-Service → AI-Service 로 넘어오는 추천 요청
 */
public class AiRecommendRequest {
    private String rawUserInput;
    private List<WelfareItem> welfareItemList;
}

