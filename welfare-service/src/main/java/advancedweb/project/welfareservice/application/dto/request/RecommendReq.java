package advancedweb.project.welfareservice.application.dto.request;

import advancedweb.project.welfareservice.domain.entity.Welfare;

public record RecommendReq(
        String target,
        String criteria,
        String content,
        String applyMethod,
        String question
) {
    public static RecommendReq create(Welfare welfare, String question) {
        return new RecommendReq(
                welfare.getDetail().getTarget(),
                welfare.getDetail().getCriteria(),
                welfare.getDetail().getContent(),
                welfare.getDetail().getApplyMethod(),
                question
        );
    }
}
