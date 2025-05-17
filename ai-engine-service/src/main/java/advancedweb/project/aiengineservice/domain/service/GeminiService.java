package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.dto.request.ChatBotRequest;
import advancedweb.project.aiengineservice.global.exception.RestApiException;
import advancedweb.project.aiengineservice.infra.client.GeminiApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Value("${GEMINI_API_KEY}") private String geminiKey;

    /**
     *
     * @param req 요청 DTO
     * @return 추천된 복지 PK 리스트
     * 프롬프트 구성 →  HTTP 호출 →  응답 처리 →  파싱 →  메트릭
     */
    public List<String> requestRecommendation(AiRecommendRequest req){
        // 프롬프트 생성
        String prompt = buildRecommendPrompt(req);
        try {
            return extractStringListFromRawResponse(geminiApiClient.generateContent(
                    geminiKey, Map.of(
                            "contents", List.of(
                                    Map.of("parts",
                                            List.of(Map.of("text", prompt)))
                            )
                    )
            ));
        } catch (IOException e) {
            throw new RestApiException(_PARSING_ERROR);
        }
    }

    public String chatReply(ChatBotRequest req) {
        System.out.println("req = " + req);
        String prompt = buildChatBotPrompt(req);

        try {
            Map<String, Object> rawResponse = geminiApiClient.generateContent(
                    geminiKey,
                    Map.of("contents",
                            List.of(Map.of("parts", List.of(Map.of("text", prompt))))
                    )
            );
            return extractChatReplyFromRawResponse(rawResponse);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(_PARSING_ERROR);
        }
    }

    /**
     *  Private Method
     */
    private List<String> extractStringListFromRawResponse(Map<String, Object> rawResponse) throws IOException {
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

    private String extractChatReplyFromRawResponse(Map<String, Object> rawResponse) {
        // candidates → 첫번째 element → content → parts[0] → text
        List<Object> candidates = (List<Object>) rawResponse.get("candidates");
        Map<String, Object> firstCandidate = (Map<String, Object>) candidates.get(0);
        Map<String, Object> content       = (Map<String, Object>) firstCandidate.get("content");
        List<Object> parts                = (List<Object>) content.get("parts");
        Map<String, Object> part0         = (Map<String, Object>) parts.get(0);

        return ((String) part0.get("text")).trim();
    }

    private String buildRecommendPrompt(AiRecommendRequest req) {
        String welfareDescriptions = req.getWelfareItemList().stream()
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
    public String buildChatBotPrompt(ChatBotRequest req) {
    // 복지 서비스 정보를 문자열로 포맷팅
        String serviceInfo = String.format(
                "• ID: %s\n" +
                        "  서비스명: %s\n" +
                        "  지원 대상: %s\n" +
                        "  선정 기준: %s\n" +
                        "  서비스 내용: %s\n" +
                        "  신청 방법: %s",
                req.getCurrentWelfarePk(),
                req.getCurrentWelfareName(),
                req.getSupportTarget(),
                req.getSelectionCriteria(),
                req.getWelfareContent(),
                req.getApplicationMethod()
        );

        // 최종 프롬프트 조립
        return String.format(
                "다음 복지 서비스 정보를 참고하여 사용자 질문에 대해 친절하고 구체적으로 300자 이내로 답변해주세요.\n\n" +
                        "=== 복지 서비스 정보 ===\n" +
                        "%s\n\n" +
                        "=== 사용자 질문 ===\n" +
                        "\"%s\"\n\n" +
                        "답변:",
                serviceInfo,
                req.getUserQuestion());
    };
}