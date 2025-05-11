package advancedweb.project.aiengineservice.domain.service;

import advancedweb.project.aiengineservice.dto.request.AiRecommendRequest;
import advancedweb.project.aiengineservice.domain.processor.GeminiResponseProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GeminiServiceTest {

    @Mock WebClient.Builder webClientBuilder;
    @Mock WebClient webClient;
    @Mock WebClient.RequestBodyUriSpec uriSpec;
    @Mock WebClient.RequestBodySpec     bodySpec;       // 제네릭 제거
    @Mock WebClient.RequestHeadersSpec headersSpec;
    @Mock WebClient.ResponseSpec         responseSpec;
    @Mock GeminiResponseProcessor        processor;

    @InjectMocks
    private GeminiService geminiService;

    @Test
    void testRecommendationWithSampleList() throws Exception {
        // 1) 사용자 입력 DTO 선언
        AiRecommendRequest req = new AiRecommendRequest(
                "나는 장애인 저소득층 20대야",
                List.of(101L, 102L, 103L),
                "장애인", "제목", "기준", "방법"
        );

        // 2) LLM이 반환할 가짜 JSON 문자열, 파싱용 JsonNode 선언
        String rawJsonArray = "[102,103]";
        JsonNode fakeResponse = new ObjectMapper().readTree(
                "{\"candidates\":[{\"output\":\"" + rawJsonArray + "\"}]}"
        );

        // 3) WebClient 호출 체인 모킹
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(fakeResponse));

        // 4) 프로세서 모킹
        when(processor.sanitizeRawContent(rawJsonArray)).thenReturn(rawJsonArray);
        when(processor.extractJsonArray(rawJsonArray)).thenReturn(rawJsonArray);
        when(processor.isValidJsonArray(rawJsonArray)).thenReturn(true);
        when(processor.parseIdList(rawJsonArray)).thenReturn(List.of(102L, 103L));

        // 5) 실제 서비스 호출 및 검증
        List<Long> result = geminiService.requestRecommendation(req);
        assertEquals(List.of(102L, 103L), result);

        System.out.println("Gemini service test 성공!");
    }
}
