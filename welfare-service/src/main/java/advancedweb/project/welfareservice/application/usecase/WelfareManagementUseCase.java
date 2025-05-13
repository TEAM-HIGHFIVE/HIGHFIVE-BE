package advancedweb.project.welfareservice.application.usecase;

import advancedweb.project.welfareservice.application.dto.request.RecommendReq;
import advancedweb.project.welfareservice.application.dto.response.WelfareDetailRes;
import advancedweb.project.welfareservice.application.dto.response.WelfareSummaryRes;
import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import advancedweb.project.welfareservice.domain.service.WelfareService;
import advancedweb.project.welfareservice.global.util.RedisKeyUtil;
import advancedweb.project.welfareservice.infra.client.AIFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WelfareManagementUseCase {

    // DI
    private final WelfareService welfareService;
    private final AIFeignClient aiFeignClient;

    // Method
    public void createSearch(Area area, Target target, String question) {

        // 지역, 지원대상을 기준으로 복지 데이터 조회
        List<Welfare> welfares = welfareService.findWelfares(area, target);
        log.info("welfares: {}", welfares);


        // AI-Service에 2차 필터링 요청
        List<RecommendReq> recommendReqs = welfares.stream()
                .map(welfare -> RecommendReq.create(welfare, question)).toList();
        List<String> welfarePKList = aiFeignClient.getAIWelfareList(recommendReqs);

        // AI가 선정한 PK만 필터링
        List<Welfare> filteredWelfares = welfares.stream()
                .filter(welfare -> welfarePKList.contains(welfare.getWelfareNo()))
                .toList();

        // PK 리스트 → Summary DTO 리스트
        List<WelfareSummaryRes> summaryList = filteredWelfares.stream()
                .map(WelfareSummaryRes::create)
                .toList();

        // Redis Key 생성
        String key = createRedisKey(area, target, question);

        // Redis에 Summary DTO 리스트 저장
    }


    public Page<WelfareSummaryRes> search(Area area, Target target, String question, Pageable pageable) {
        // Redis Key 생성
        String key = createRedisKey(area, target, question);

        // Redis에 데이터가 있는지 확인

        // 없으면 예외 반환

        // 있으면 해당 데이터 페이징 요청에 맞게 반환

        return null;
    }

    private String createRedisKey(Area area, Target target, String question) {
        return area + ":" + target + ":" + RedisKeyUtil.hashKey(question);
    }

    // TODO: 캐싱 서비스와 연동
    public WelfareDetailRes read(String welfareNo) {
        // 캐시에 있는지 확인

        // 없으면 데이터베이스에서 조회
        Welfare welfare = welfareService.findByWelfareNo(welfareNo);

        // 캐시에 해당 welfare 저장

        return WelfareDetailRes.create(welfare);
    }

    // TODO: 캐싱 서비스와 연동
    public List<WelfareSummaryRes> readRecentWelfare(int amount) {

        // 캐시 서버에 최근 조회한 복지 서비스 조회 요청. amount 수만큼 받아옴



        return List.of();
    }
}
