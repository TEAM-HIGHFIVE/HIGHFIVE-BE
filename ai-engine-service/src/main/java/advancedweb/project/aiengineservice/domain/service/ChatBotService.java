package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.domain.processor.GeminiResponseProcessor;
import advancedweb.project.aiengineservice.dto.request.ChatBotRequest;
import advancedweb.project.aiengineservice.infra.client.GeminiApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * ChatBotRequest를 받아 Gemini API를 통해 답변 String을 리턴
 */
@Service
@RequiredArgsConstructor
public class ChatBotService {

//    private final WebClient.Builder webClientBuilder;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseProcessor processor;

    @Value("${GEMINI_API_KEY") private String gemini_Key;

    //model URL
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent}")
    private String apiUrl;

    //사용할 Gemini 모델 이름
    @Value("${gemini.api.model:gemini-1.5-pro-latest}")
    private String modelName;

    public String generateReply(ChatBotRequest req) {
        String prompt = buildPrompt(req);
        Instant start = Instant.now();
        //Gemini api 호출
        try {
            JsonNode resp = null;
//            JsonNode resp = webClientBuilder.build()
//                    .post()
//                    .uri(apiUrl)
//                    .header("Authorization", "Bearer " + gemini_Key)
//                    .bodyValue(JsonNodeFactory.instance.objectNode()
//                            .put("model", modelName)
//                            .put("temperature", 0.7)
//                            .set("prompt", JsonNodeFactory.instance.textNode(prompt))
//                    )
//                    .retrieve()
//                    .bodyToMono(JsonNode.class)
//                    .doOnError(processor::detectAndHandleTimeout)
//                    .block();

            if (resp == null || !resp.has("candidates") || resp.get("candidates").isEmpty()) {
                throw new IllegalStateException("Gemini 응답이 유효하지 않습니다.");
            }

            //Gemini output 가져옴
            String output = resp.get("candidates")
                    .get(0)
                    .path("output")
                    .asText("")
                    .trim();

            // 성공 메트릭 기록
            processor.recordMetrics(output, Duration.between(start, Instant.now()), true);
            return output;
        } catch (Exception ex) {
            // 실패 메트릭 기록
            processor.recordMetrics("", Duration.between(start, Instant.now()), false);
            processor.detectAndHandleTimeout(ex);
            return "죄송합니다. 답변을 가져오는 중 문제가 발생했습니다.";
        }
    }

    private String buildPrompt(ChatBotRequest r) {
        return String.format(
                "복지 서비스 정보:\n" +
                        "• ID: %d\n" +
                        "• 이름: %s\n" +
                        "• 지원 대상: %s\n" +
                        "• 선정 기준: %s\n" +
                        "• 서비스 내용: %s\n" +
                        "• 신청 방법: %s\n\n" +
                        "사용자 질문: \"%s\"\n\n" +
                        "위 정보를 참고하여 챗봇 형식으로 간결하게 50자 이내로 답변해.",
                r.getCurrentWelfarePk(),
                r.getCurrentWelfareName(),
                r.getSupportTarget(),
                r.getSelectionCriteria(),
                r.getWelfareContent(),
                r.getApplicationMethod(),
                r.getQuestion()
        );
    }
}
