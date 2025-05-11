package advancedweb.project.boardservice.domain.service;

import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import advancedweb.project.boardservice.domain.entity.Comment;
import advancedweb.project.boardservice.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void write(String postNo, WriteCmtReq request, String userNo) {
        // 댓글 생성
        Comment comment = Comment.create(postNo, userNo, request.content());

        // DB 저장
        commentRepository.save(comment);
    }
}
