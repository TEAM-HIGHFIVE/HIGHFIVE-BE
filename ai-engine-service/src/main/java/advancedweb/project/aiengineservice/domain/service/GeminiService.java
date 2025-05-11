package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.domain.processor.GeminiResponseProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {
    /**
     * Gemini 관련 로직 처리 서비스
     */

    private final WebClient.Builder webClientBuilder;
    private final GeminiResponseProcessor processor;

    @Value("${GEMINI_API_KEY") private String gemini_Key;

    //model URL
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent}")
    private String apiUrl;

    //사용할 Gemini 모델 이름
    @Value("${gemini.api.model:gemini-1.5-pro-latest}")
    private String modelName;

    /**
     *
     * @param req 요청 DTO
     * @return 추천된 복지 PK 리스트
     * 프롬프트 구성 →  HTTP 호출 →  응답 처리 →  파싱 →  메트릭
     */
    public List<Long> requestRecommendation(AiRecommendRequest req){
        // 프롬프트 생성
        String prompt = buildPrompt(req);

        // 최종 gemini 호출 URL
        String fullUrl = apiUrl + "?key=" + gemini_Key;

        Instant start = Instant.now();
        //
        String raw;

        try {
            // request body
            Map<String, Object> body = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(Map.of("text", prompt)))
                    )
            );
            // Gemini API 호출
            JsonNode root = webClientBuilder.build()
                    .post()
                    .uri(fullUrl)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            raw = root.path("candidates").get(0).path("output").asText();
        } catch (Exception ex) {
            processor.detectAndHandleTimeout(ex);
            return processor.handleParseError((Exception) ex);
        }

        String clean = processor.sanitizeRawContent(raw);
        String jsonArray = processor.extractJsonArray(clean);
        List<Long> ids;
        if(!processor.isValidJsonArray(jsonArray)){
            ids = processor.handleParseError(
                    new IllegalAccessException("유효하지 않는 JSON 배열: "+ jsonArray)
            );
        } else {
            ids = processor.parseIdList(jsonArray);
        }

        processor.recordMetrics(raw, Duration.between(start, Instant.now()), !ids.isEmpty());
        return ids;
    }

    private String buildPrompt(AiRecommendRequest r) {
        return "사용자 입력: \"" + r.getRawUserInput() + "\"\n" +
                "지원 대상: " + r.getSupportTarget() + "\n" +
                "서비스 제목: " + r.getWelfareTitle() + "\n" +
                "선정 기준: " + r.getSelectionCriteria() + "\n" +
                "신청 방법: " + r.getApplyMethod() + "\n\n" +
                "다음 복지 서비스 ID 목록 중 가장 적합한 ID만 JSON 배열로 출력하세요:\n" +
                r.getWelfarePKs() + "\n" +
                "예시 출력: [101, 207]";
    }
}
