package advancedweb.project.aiengineservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * AI-Service → Welfare-Service 로 반환하는 추천 응답
 */
public class AiRecommendResponse {
    private List<String> recommendedPks;
}
