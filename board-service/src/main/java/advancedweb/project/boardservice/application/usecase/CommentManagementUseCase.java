package advancedweb.project.boardservice.application.usecase;

import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import advancedweb.project.boardservice.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentManagementUseCase {

    private final CommentService commentService;

    public void write(String postNo, WriteCmtReq request, String userNo) {
        commentService.write(postNo, request, userNo);
    }
}
