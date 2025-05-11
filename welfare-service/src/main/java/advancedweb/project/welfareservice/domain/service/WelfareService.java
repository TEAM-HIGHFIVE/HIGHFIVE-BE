package advancedweb.project.welfareservice.domain.service;

import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import advancedweb.project.welfareservice.domain.repository.WelfareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WelfareService {

    private final WelfareRepository welfareRepository;

    public List<Welfare> findWelfares(Area area, Target target) {
        return welfareRepository.
                findBySummary_AreasInAndSummary_TargetsIn(Set.of(area), Set.of(target));
    }
}
