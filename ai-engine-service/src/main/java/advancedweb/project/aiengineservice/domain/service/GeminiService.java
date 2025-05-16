package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.domain.processor.GeminiResponseProcessor;
import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.global.exception.RestApiException;
import advancedweb.project.aiengineservice.infra.client.GeminiApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static advancedweb.project.aiengineservice.global.exception.code.status.GlobalErrorStatus._PARSING_ERROR;

@Service
@RequiredArgsConstructor
public class GeminiService {
    /**
     * Gemini 관련 로직 처리 서비스
     */

    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseProcessor processor;

    @Value("${GEMINI_API_KEY:AIzaSyDOubo_biqXtT6fZYTF5mNEPZ0xQHDO6cc}") private String geminiKey;

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
    public List<String> requestRecommendation(AiRecommendRequest req){
        // 프롬프트 생성
        String prompt = buildPrompt(req);

        // 최종 gemini 호출 URL
        String fullUrl = apiUrl + "?key=" + geminiKey;

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        try {
            return extractStringListFromRawResponse(geminiApiClient.generateContent(geminiKey, body));
        } catch (IOException e) {
            throw new RestApiException(_PARSING_ERROR);
        }
    }

    public List<String> extractStringListFromRawResponse(Map<String, Object> rawResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. 깊게 들어가서 텍스트 추출
        String rawText = ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) rawResponse
                .get("candidates")).get(0))
                .get("content"))
                .get("parts") instanceof List partsList
                ? (String) ((Map<String, Object>) partsList.get(0)).get("text")
                : "";

        // 3. ``` 제거
        String cleaned = rawText.replaceAll("(?s)```json|```", "").trim();  // → "[3]"

        // 4. JSON 배열 파싱 후 문자열화
        List<Integer> parsed = objectMapper.readValue(cleaned, new TypeReference<List<Integer>>() {});
        return parsed.stream().map(String::valueOf).toList();
    }

    private String buildPrompt(AiRecommendRequest req) {
        String welfareDescriptions = req.getWelfareList().stream()
                .map(w -> String.format(
                        "• ID: %s\n" +
                                "  지원 대상: %s\n" +
                                "  서비스 제목: %s\n" +
                                "  선정 기준: %s\n" +
                                "  신청 방법: %s",
                        w.getPk(),
                        w.getSupportTarget(),
                        w.getWelfareTitle(),
                        w.getSelectionCriteria(),
                        w.getApplyMethod()
                ))
                .collect(Collectors.joining("\n\n"));

        return String.format(
                "사용자 입력: \"%s\"\n\n" +
                        "복지 서비스 목록:\n%s\n\n" +
                        "다음 복지 서비스 ID 목록 중 가장 적합한 ID만 JSON 배열로 출력하세요.\n" +
                        "예시 출력: [101]",
                req.getRawUserInput(),
                welfareDescriptions
        );
    }

}
