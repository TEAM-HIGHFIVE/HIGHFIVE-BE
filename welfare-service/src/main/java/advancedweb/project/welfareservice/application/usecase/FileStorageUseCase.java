package advancedweb.project.welfareservice.application.usecase;

import advancedweb.project.welfareservice.application.dto.response.DownloadFileRes;
import advancedweb.project.welfareservice.domain.entity.File;
import advancedweb.project.welfareservice.domain.entity.Welfare;
import advancedweb.project.welfareservice.domain.repository.FileRepository;
import advancedweb.project.welfareservice.domain.repository.WelfareRepository;
import advancedweb.project.welfareservice.domain.service.FileService;
import advancedweb.project.welfareservice.domain.service.WelfareService;
import advancedweb.project.welfareservice.global.exception.RestApiException;
import advancedweb.project.welfareservice.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class FileStorageUseCase {

    // DI
    private final S3Client s3Client;
    private final WelfareService welfareService;
    private final FileService fileService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public DownloadFileRes download(String welfareNo) {
        // 해당 Welfare의 파일명 가져오기
        Welfare welfare = welfareService.findByWelfareNo(welfareNo);
        File fileInfo = fileService.findByFileNo(welfare.getFileNo());
        String fileName = fileInfo.getFileName();

        // S3로 부터 파일 다운로드
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> s3Object = null;
        try {
            s3Object = s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            throw new RestApiException(GlobalErrorStatus._S3_DOWNLOAD_ERROR);
        }
        return DownloadFileRes.create(new InputStreamResource(s3Object), fileName);
    }
}
