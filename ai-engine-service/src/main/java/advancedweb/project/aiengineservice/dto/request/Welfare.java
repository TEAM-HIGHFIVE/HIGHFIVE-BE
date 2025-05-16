package advancedweb.project.aiengineservice.dto.request;

import lombok.Getter;

@Getter
public class Welfare {
    public String pk;
    private String supportTarget;
    private String welfareTitle;
    private String selectionCriteria;
    private String applyMethod;
}