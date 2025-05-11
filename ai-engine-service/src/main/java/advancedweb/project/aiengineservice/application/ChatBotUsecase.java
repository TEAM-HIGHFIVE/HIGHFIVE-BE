package advancedweb.project.aiengineservice.application;

import advancedweb.project.aiengineservice.dto.request.ChatBotRequest;
import advancedweb.project.aiengineservice.domain.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ChatBotRequest를 넘겨 Gemini 답변을 생성
 */
@Service
@RequiredArgsConstructor
public class ChatBotUsecase {
    private final ChatBotService chatBotService;

    public String chat(ChatBotRequest req){
        return chatBotService.generateReply(req);
    }
}
