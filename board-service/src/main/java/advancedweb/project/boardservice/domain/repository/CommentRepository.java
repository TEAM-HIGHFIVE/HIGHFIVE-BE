package advancedweb.project.boardservice.domain.repository;

import advancedweb.project.boardservice.domain.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostNo(String postNo);
}
