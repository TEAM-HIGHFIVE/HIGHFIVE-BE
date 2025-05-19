package advancedweb.project.welfareservice.application.usecase;

import advancedweb.project.welfareservice.application.dto.request.AskReq;
import advancedweb.project.welfareservice.application.dto.request.ChatBotReq;
import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.service.WelfareService;
import advancedweb.project.welfareservice.infra.client.AIFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AskChatbotUseCase {

    // DI
    private final WelfareService welfareService;
    private final AIFeignClient aiFeignClient;

    /**
     * 챗봇 처리
     */
    public String ask(String welfareNo, AskReq request) {
        Welfare welfare = welfareService.findByWelfareNo(welfareNo); // Welfare 조회
        ChatBotReq chatBotReq = ChatBotReq.create(request.question(), welfare);

        return aiFeignClient.getChatbotResponse(chatBotReq); // 챗봇(Ai-Engine-Service)으로 부터 응답을 받아 반환
    }
}
