package advancedweb.project.welfareservice.domain.entity;

import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class Summary {

    private String name;
    private Set<Area> areas;    // 지역
    private Set<Target> targets;    // 지원 대상
}
