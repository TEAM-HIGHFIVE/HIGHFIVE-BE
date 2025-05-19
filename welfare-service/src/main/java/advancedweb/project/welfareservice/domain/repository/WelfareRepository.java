package advancedweb.project.welfareservice.domain.repository;

import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface WelfareRepository extends MongoRepository<Welfare, String> {
    // Area 또는 Target이 포함된 복지를 검색 (단일 조건)
    List<Welfare> findBySummary_AreasInAndSummary_TargetsIn(Set<Area> areas, Set<Target> targets);
}
