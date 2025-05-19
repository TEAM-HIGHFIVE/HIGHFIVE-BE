package advancedweb.project.welfareservice.infra.client;

import advancedweb.project.welfareservice.application.dto.response.WelfareDetailRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cache-service")
public interface CacheFeignClient {
    @GetMapping
    WelfareDetailRes getWelfareInCache(@PathVariable String welfareNo);

    @GetMapping("")
    List<WelfareDetailRes> getRecentWelfareListInCache(@RequestParam int amount);
}
