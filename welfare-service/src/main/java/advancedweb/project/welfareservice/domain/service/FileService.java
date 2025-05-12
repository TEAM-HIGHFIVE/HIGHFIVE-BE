package advancedweb.project.welfareservice.domain.service;

import advancedweb.project.welfareservice.domain.entity.File;
import advancedweb.project.welfareservice.domain.repository.FileRepository;
import advancedweb.project.welfareservice.global.exception.RestApiException;
import advancedweb.project.welfareservice.global.exception.code.BaseCodeInterface;
import advancedweb.project.welfareservice.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public File findByFileNo(String fileNo) {
        return fileRepository.findById(fileNo)
                .orElseThrow(() -> new RestApiException(GlobalErrorStatus._NOT_FOUND));
    }
}
