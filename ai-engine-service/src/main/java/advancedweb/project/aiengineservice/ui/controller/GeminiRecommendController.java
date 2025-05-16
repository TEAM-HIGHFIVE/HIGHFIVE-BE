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

    /**
     * POST /api/ai/recommend
     * {
     *   "rawUserInput": "...",
     *   "welfarePKs": [101,207,312],
     *   "supportTarget": "...",
     *   "welfareTitle": "...",
     *   "selectionCriteria": "...",
     *   "applyMethod": "..."
     * }
     *
     * → { "recommendedPKs": [207,312] }
     */
    /**
     * 근데 이렇게 받는것 보다  DTO 리스트를 더 낫지 않나?
     * @param req
     * @return
     */
    @PostMapping("/recommend")
    public ResponseEntity<AiRecommendResponse> recommend(
            @RequestBody AiRecommendRequest req
    ){
        return ResponseEntity.ok(recommendWelfareUseCase.recommend(req));
    }
}
