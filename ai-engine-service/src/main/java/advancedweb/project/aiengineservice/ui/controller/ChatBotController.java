package advancedweb.project.aiengineservice.ui.controller;

import advancedweb.project.aiengineservice.dto.request.ChatBotRequest;
import advancedweb.project.aiengineservice.dto.response.ChatBotResponse;
import advancedweb.project.aiengineservice.application.ChatBotUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * welfare-service로부터 ChatBotRequest를 받아서 ChatUsecase에 전달, 결과(ChatBotResponse) 반환
 */
@RestController
@RequestMapping("/internal/chatbot")
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBotUsecase chatUsecase;

    @PostMapping
    public ResponseEntity<ChatBotResponse> chat(
            @RequestBody ChatBotRequest req
    ){
        String reply = chatUsecase.chat(req);
        return ResponseEntity.ok(new ChatBotResponse(reply));
    }
}
