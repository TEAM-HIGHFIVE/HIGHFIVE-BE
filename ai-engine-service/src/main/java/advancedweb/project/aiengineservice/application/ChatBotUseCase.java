package advancedweb.project.aiengineservice.application;

import advancedweb.project.aiengineservice.domain.service.GeminiService;
import advancedweb.project.aiengineservice.dto.request.ChatBotRequest;
import advancedweb.project.aiengineservice.dto.response.ChatBotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ChatBotRequest를 넘겨 Gemini 답변을 생성
 */
@Service
@RequiredArgsConstructor
public class ChatBotUseCase {
    private final GeminiService geminiService;

    public ChatBotResponse chat(ChatBotRequest req){
        return new ChatBotResponse(geminiService.chatReply(req));
    }
}
