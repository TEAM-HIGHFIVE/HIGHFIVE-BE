package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final GeminiService geminiService;
    /**
     * 추천 비즈니스 흐름을 관리하는 상위 서비스
     * @return Gemini API를 통해 추출한 추천 PK 리스트
     */
    public List<String> requestRecommendation(AiRecommendRequest req){
        return geminiService.requestRecommendation(req);
    }
}
