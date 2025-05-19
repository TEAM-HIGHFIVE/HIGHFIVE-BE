package advancedweb.project.welfareservice.ui.controller;

import advancedweb.project.welfareservice.application.dto.response.DownloadFileRes;
import advancedweb.project.welfareservice.application.dto.response.WelfareDetailRes;
import advancedweb.project.welfareservice.application.dto.response.WelfareSummaryRes;
import advancedweb.project.welfareservice.application.usecase.FileStorageUseCase;
import advancedweb.project.welfareservice.application.usecase.WelfareManagementUseCase;
import advancedweb.project.welfareservice.domain.entity.enums.Area;
import advancedweb.project.welfareservice.domain.entity.enums.Target;
import advancedweb.project.welfareservice.global.annotation.CheckAuthorization;
import advancedweb.project.welfareservice.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/welfare")
public class WelfareController {

    private final WelfareManagementUseCase welfareManagementUseCase;
    private final FileStorageUseCase fileStorageUseCase;

    /**
     * 복지 서비스 검색 데이터 생성 API
     */
    @PostMapping("/search")
    @CheckAuthorization
    public BaseResponse<String> createWelfare(@RequestParam Area area,
                                              @RequestParam Target target,
                                              @RequestParam String question) {

        welfareManagementUseCase.createSearch(area, target, question);
        return BaseResponse.onSuccess("검색 데이터가 생성되었습니다.");
    }

    /**
     *  복지 서비스 검색 데이터 조회 API
     *  지역 및 장애 구분으로 필터링한 뒤 ai-engine-service로 검색어와 데이터 전송
     *  응답을 받고 해당 PK 값들만 디자인 템플릿에 맞게 응답
     *  필터링 기준은 Enum으로 두 항목을 선언하여 RequestParam으로 필터링
     */
    @GetMapping
    @CheckAuthorization
    public BaseResponse<Page<WelfareSummaryRes>> searchWelfare(@RequestParam Area area,
                                                               @RequestParam Target target,
                                                               @RequestParam String question,
                                                               Pageable pageable
                                                               ) {
        return BaseResponse.onSuccess(welfareManagementUseCase.search(area, target, question, pageable));
    }

    /**
     *  복지 상세 조회 API
     *  PK를 기준으로 상세 조회
     */
    @GetMapping("/{welfareNo}")
    @CheckAuthorization
    public BaseResponse<WelfareDetailRes> readWelfare(@PathVariable String welfareNo) {
        return BaseResponse.onSuccess(welfareManagementUseCase.read(welfareNo));
    }

    /**
     * 최근 조회한 복지 서비스 조회 API
     */
    @GetMapping("/recent")
    @CheckAuthorization
    public BaseResponse<List<WelfareSummaryRes>> readRecentWelfare(@RequestParam(defaultValue = "5") int amount) {
        return BaseResponse.onSuccess(welfareManagementUseCase.readRecentWelfare(amount));
    }

    /**
     *  상세 조회 페이지 내에서 원본 파일 다운로드 API
     *  welfare PK 기준으로 파일을 찾아서 URI 전송
     */
    @GetMapping("/download/{welfareNo}")
    @CheckAuthorization
    public ResponseEntity<Resource> downloadFile(@PathVariable String welfareNo) {
        DownloadFileRes downloadFileRes = fileStorageUseCase.download(welfareNo);

        String fileName = downloadFileRes.filename();

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8) // 한글 파일명: 인코딩 필요
                .replaceAll("\\+", "%20"); // 파일명의 띄어쓰기가 +로 표시되는 부분 해결
        Resource resource = downloadFileRes.resource();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
