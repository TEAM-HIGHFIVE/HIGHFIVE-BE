package advancedweb.project.welfareservice.application.dto.response;

import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;

import java.time.LocalDateTime;
import java.util.Set;

public record WelfareDetailRes(
        String title,       // 제목
        LocalDateTime updatedAt,    // 갱신일
        Set<Area> areas,    // 지역 태그
        Set<Target> targets,    // 지원 대상 태그
        String criteria,    // 선정 기준
        String content,     // 서비스 내용
        String applyMethod,     // 신청 방법
        String tel,     // 전화 문의
        String referenceLink,    // 관련 웹사이트
        String reference    // 근거 법령 및 자료
) {
    public static WelfareDetailRes create(Welfare welfare) {
        return new WelfareDetailRes(
                welfare.getSummary().getName(),
                LocalDateTime.now(),
                welfare.getSummary().getAreas(),
                welfare.getSummary().getTargets(),
                welfare.getDetail().getCriteria(),
                welfare.getDetail().getContent(),
                welfare.getDetail().getApplyMethod(),
                welfare.getDetail().getTel(),
                welfare.getDetail().getReferenceLink(),
                welfare.getDetail().getReference()
        );
    }
}
