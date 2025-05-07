package advancedweb.project.welfareservice.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum Target {
    DISABILITY("장애인"),          // 장애인
    VETERANS("보훈대상자"),         // 보훈대상자
    LOW_INCOME("저소득"),          // 저소득
    MIGRANT_OR_MULTICULTURAL("다문화·탈북민"),   // 다문화/탈북민
    SINGLE_PARENT_OR_GRANDPARENT("한부모·조손"),   // 한부모/조손
    MULTI_CHILD_FAMILY("다자녀")      // 다자녀
    ;

    private final String desc;

    public static Target from(String desc) {
        return Arrays.stream(Target.values())
                .filter(target -> target.getDesc().equals(desc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown target description: " + desc));
    }
}
