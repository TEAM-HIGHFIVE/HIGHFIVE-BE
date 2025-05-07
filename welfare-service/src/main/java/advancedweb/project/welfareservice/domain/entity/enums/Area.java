package advancedweb.project.welfareservice.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Area {
    SUWON("수원시"),
    SEONGNAM("성남시"),
    UIJEONGBU("의정부시"),
    ANYANG("안양시"),
    BUCHEON("부천시"),
    GWANGMYEONG("광명시"),
    PYEONGTAEK("평택시"),
    DONGDUCHEON("동두천시"),
    ANSAN("안산시"),
    GWANGJU("광주시"),
    GOYANG("고양시"),
    GWACHEON("과천시"),
    GURI("구리시"),
    NAMYANGJU("남양주시"),
    OSAN("오산시"),
    SIHEUNG("시흥시"),
    GUNPO("군포시"),
    UIWANG("의왕시"),
    HWASEONG("화성시"),
    HANAM("하남시"),
    YONGIN("용인시"),
    PAJU("파주시"),
    ICHEON("이천시"),
    ANSEONG("안성시"),
    GIMPO("김포시"),
    YANGJU("양주시"),
    POCHEON("포천시"),
    YEOJU("여주시"),
    GAPYEONG("가평군"),
    YANGPYEONG("양평군"),
    YEONCHEON("연천군"),
    GYEONGGI("경기도교육청")
    ;

    private final String desc;

    public static Area from(String desc) {
        return Arrays.stream(Area.values())
                .filter(area -> area.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Area description: " + desc));
    }
}
