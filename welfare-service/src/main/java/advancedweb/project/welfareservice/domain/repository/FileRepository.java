package advancedweb.project.welfareservice.domain.repository;

import advancedweb.project.welfareservice.domain.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}
