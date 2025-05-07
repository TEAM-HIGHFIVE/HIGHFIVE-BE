package advancedweb.project.welfareservice.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Detail {

    private String target;
    private String criteria;
    private String content;
    private String applyMethod;
    private String tel;
    private String referenceLink;
    private String reference;
}
