package advancedweb.project.boardservice.ui.controller;

import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import advancedweb.project.boardservice.application.dto.response.ReadCmtRes;
import advancedweb.project.boardservice.application.dto.response.WriteCmtRes;
import advancedweb.project.boardservice.application.usecase.CommentManagementUseCase;
import advancedweb.project.boardservice.global.annotation.CheckAuthorization;
import advancedweb.project.boardservice.global.annotation.CurrentUser;
import advancedweb.project.boardservice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class CommentController {

    private final CommentManagementUseCase commentManagementUseCase;

    @PostMapping("/{postNo}")
    @CheckAuthorization
    public BaseResponse<WriteCmtRes> write(@PathVariable String postNo,
                                           @RequestBody WriteCmtReq request,
                                           @CurrentUser @Parameter(hidden = true) String userNo) {
        return BaseResponse.onSuccess(commentManagementUseCase.write(postNo, request, userNo));
    }

    @DeleteMapping("/{commentNo}")
    @CheckAuthorization
    public BaseResponse<Void> delete(@PathVariable String commentNo,
                                     @CurrentUser @Parameter(hidden = true) String userNo) {
        commentManagementUseCase.delete(commentNo, userNo);
        return BaseResponse.onSuccess();
    }

    @GetMapping("/{postNo}/comments")
    public BaseResponse<List<ReadCmtRes>> getComments(@PathVariable String postNo) {
        return BaseResponse.onSuccess(commentManagementUseCase.getComments(postNo));
    }
}
