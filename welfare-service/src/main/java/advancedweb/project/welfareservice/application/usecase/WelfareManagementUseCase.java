package advancedweb.project.welfareservice.application.usecase;

import advancedweb.project.welfareservice.application.dto.response.WelfareDetailRes;
import advancedweb.project.welfareservice.application.dto.response.WelfareSummaryRes;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WelfareManagementUseCase {

    // DI


    // Method
    public Page<WelfareSummaryRes> search(Area area, Target target, String question) {
        return null;
    }

    public WelfareDetailRes read(String welfareNo) {
        return null;
    }
}
