//package advancedweb.project.aiengineservice.infra.client;
//
//import advancedweb.project.aiengineservice.dto.WelfareDetailResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
///**
// * AI-Service → Welfare-Service 간 OpenFeign 클라이언트
// */
//
//@FeignClient(name = "welfare-service")
//public interface WelfareServiceClient {
//
//    /**
//     * 1차 필터링: 지역(region) + 대상(target)에 맞는 복지 PK 리스트 조회
//     * GET /internal/welfare/ids?region={region}&target={target}
//     */
//    @GetMapping("/internal/welfare/ids")
//    List<Long> getWelfareIds(
//            @RequestParam("region") String region,
//            @RequestParam("target") String target
//    );
//
//    /**
//     * 상세조회: 단일 PK에 해당하는 복지 서비스 상세정보 조회
//     * GET /internal/welfare/details?ids={id}
//     */
//    @GetMapping("/internal/welfare/details")
//    WelfareDetailResponse getDetails(
//            @RequestParam("ids") Long id
//    );
//}
//*/