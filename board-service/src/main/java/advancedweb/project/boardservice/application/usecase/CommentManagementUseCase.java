package advancedweb.project.boardservice.application.usecase;

import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentManagementUseCase {

    // DI


    // Method
    public void write(String postNo, WriteCmtReq request, String userNo) {

    }

    public void delete(String commentNo, String userNo) {

    }
}
