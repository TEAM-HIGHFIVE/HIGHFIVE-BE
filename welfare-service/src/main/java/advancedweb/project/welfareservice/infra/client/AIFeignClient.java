package advancedweb.project.welfareservice.infra.client;

import advancedweb.project.welfareservice.application.dto.request.AskReq;
import advancedweb.project.welfareservice.application.dto.request.ChatBotReq;
import advancedweb.project.welfareservice.application.dto.request.RecommendReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ai-engine-service")
public interface AIFeignClient {
    /**
     * AI-Engine-Service에게 Welfare 2차 필터링 요청
     */
    @GetMapping("") // todo: ai-engine-service api 경로 입력
    List<String> getAIWelfareList(@RequestBody List<RecommendReq> recommendReqs);

    /**
     * AI-Engine-Service에게 챗봇 응답 요청
     */
    @GetMapping("") // todo: ai-engine-service api 경로 입력
    String getChatbotResponse(@RequestBody ChatBotReq chatBotReq);
}
