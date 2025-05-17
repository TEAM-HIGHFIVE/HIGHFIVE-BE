package advancedweb.project.aiengineservice.ui.controller;

import advancedweb.project.aiengineservice.application.RecommendWelfareUseCase;
import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.dto.response.AiRecommendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * gemini 추천 복지 서비스 2차 필터링 컨트롤러
 */

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiRecommendController {
    //DI
    private final RecommendWelfareUseCase recommendWelfareUseCase;

    @PostMapping("/recommend")
    public ResponseEntity<AiRecommendResponse> recommend(
            @RequestBody AiRecommendRequest req
    ){
        return ResponseEntity.ok(recommendWelfareUseCase.recommend(req));
    }
}
