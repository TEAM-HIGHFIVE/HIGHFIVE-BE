package advancedweb.project.welfareservice.infra.client;

import advancedweb.project.welfareservice.application.dto.request.RecommendReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ai-engine-service")
public interface AIFeignClient {
    @GetMapping("") // todo: ai-engine-service api 경로 입력
    List<String> getAIWelfareList(@RequestBody List<RecommendReq> recommendReqs);
}
