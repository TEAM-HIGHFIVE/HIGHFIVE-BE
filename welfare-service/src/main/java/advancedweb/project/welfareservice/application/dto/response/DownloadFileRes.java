package advancedweb.project.welfareservice.application.dto.response;

import org.springframework.core.io.Resource;

public record DownloadFileRes(
        Resource resource, // 파일 내용
        String filename // 파일 이름
) {
    public static DownloadFileRes create(Resource resource, String filename) {
        return new DownloadFileRes(resource, filename);
    }
}
