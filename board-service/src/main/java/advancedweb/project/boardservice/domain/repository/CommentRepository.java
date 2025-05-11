package advancedweb.project.boardservice.domain.repository;

import advancedweb.project.boardservice.domain.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    // 필요시, 게시글 ID로 댓글 조회하는 쿼리 등 추가 가능
}
