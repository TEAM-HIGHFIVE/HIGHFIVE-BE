package advancedweb.project.boardservice.application.dto.response;

import java.time.LocalDateTime;

public record ReadCmtRes(
    String commentNo,
    String userNo,
    String content,
    LocalDateTime createdAt
) {}
