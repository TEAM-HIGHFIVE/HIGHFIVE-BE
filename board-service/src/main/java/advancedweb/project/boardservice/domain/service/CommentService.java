package advancedweb.project.boardservice.domain.service;

import advancedweb.project.boardservice.domain.entity.Comment;
import advancedweb.project.boardservice.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(String postNo, String userNo, String content) {
        Comment comment = Comment.builder()
                .postNo(postNo)
                .userNo(userNo)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    public void delete(String commentNo, String userNo) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserNo().equals(userNo)) {
            throw new RuntimeException("Not authorized to delete this comment");
        }

        commentRepository.deleteById(commentNo);
    }

    public List<Comment> findByPostNo(String postNo) {
        return commentRepository.findByPostNo(postNo);
    }
}
