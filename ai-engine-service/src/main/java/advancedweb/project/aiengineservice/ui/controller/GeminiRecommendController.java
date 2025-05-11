package advancedweb.project.aiengineservice.ui.controller;

import advancedweb.project.aiengineservice.dto.response.AiRecommendResponse;
import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.domain.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * gemini 추천 복지 서비스 2차 필터링 컨트롤러
 */

@RestController
@RequestMapping("/internal/gemini")
@RequiredArgsConstructor
public class GeminiRecommendController {
    //DI
    private final RecommendationService recommendationService;

    /**
     * POST /internal/gemini/recommend
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
        var rec = recommendationService.requestRecommendation(req);
        //OK 신호와 함께 추천 복지리스트를 return
        return ResponseEntity.ok(new AiRecommendResponse(rec));
    }
}
