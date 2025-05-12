package advancedweb.project.boardservice.domain.service;

import advancedweb.project.boardservice.domain.entity.Comment;
import advancedweb.project.boardservice.domain.repository.CommentRepository;
import advancedweb.project.boardservice.application.dto.request.WriteCmtReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void write(String postNo, WriteCmtReq request, String userNo) {
        Comment comment = Comment.builder()
                .postNo(postNo)
                .userNo(userNo)
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }
}
