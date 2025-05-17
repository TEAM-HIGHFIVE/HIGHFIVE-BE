package advancedweb.project.aiengineservice.application;

import advancedweb.project.aiengineservice.domain.service.GeminiService;
import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.dto.response.AiRecommendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendWelfareUseCase {

    private final GeminiService geminiService;

    public AiRecommendResponse recommend(AiRecommendRequest request) {
        return new AiRecommendResponse(geminiService.requestRecommendation(request));
    }
}
