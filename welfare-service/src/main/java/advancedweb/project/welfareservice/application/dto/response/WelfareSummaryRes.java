package advancedweb.project.welfareservice.application.dto.response;

import advancedweb.project.welfareservice.domain.entity.Welfare;

public record WelfareSummaryRes (
        String title
) {
    public static WelfareSummaryRes create(Welfare welfare) {
        return new WelfareSummaryRes(welfare.getSummary().getName());
    }
}
