package advancedweb.project.welfareservice.application.dto.response;

import advancedweb.project.welfareservice.domain.entity.Welfare;

public record WelfareSummaryRes (
        String welfareNo,
        String title
) {
    public static WelfareSummaryRes create(Welfare welfare) {
        return new WelfareSummaryRes(welfare.getWelfareNo(), welfare.getSummary().getName());
    }
}
