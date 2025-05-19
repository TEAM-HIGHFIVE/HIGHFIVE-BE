package advancedweb.project.boardservice.application.usecase;

import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import advancedweb.project.boardservice.application.dto.response.ReadCmtRes;
import advancedweb.project.boardservice.application.dto.response.WriteCmtRes;
import advancedweb.project.boardservice.domain.entity.Comment;
import advancedweb.project.boardservice.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentManagementUseCase {

    private final CommentService commentService;

    public WriteCmtRes write(String postNo, WriteCmtReq request, String userNo) {
        Comment saved = commentService.save(postNo, userNo, request.content());
        return new WriteCmtRes(saved.getCommentNo());
    }

    public void delete(String commentNo, String userNo) {
        commentService.delete(commentNo, userNo);
    }

    public List<ReadCmtRes> getComments(String postNo) {
        return commentService.findByPostNo(postNo).stream()
                .map(c -> new ReadCmtRes(
                        c.getCommentNo(),
                        c.getUserNo(),
                        c.getContent(),
                        c.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
