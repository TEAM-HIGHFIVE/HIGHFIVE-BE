package advancedweb.project.aiengineservice.domain.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gemini가 출력한 raw값들을 전처리/후처리 하는 클래스
 * 왜 사용하는가? : 1. LLM 출력은 완전히 신뢰할 수 없고, 안정적인 전처리/후처리 파이프라인이 필요하며,
 * 에러가 어디서 났는지, 어떤 raw 응답이 문제였는지, 응답 지연은 얼마나 되는지 파악하기에 용이하기 때문임
 */

/**
 *  todo : 에러 로그 좀 더 가독성 있게 만들기
 */

@Component
public class GeminiResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(GeminiResponseProcessor.class);
    private static final Pattern JSON_ARRAY_PATTERN = Pattern.compile("\\[(?:\\s*\\d+\\s*,?)+\\]");
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** 불필요한 문장 or 제어문자 제거 */
    public String sanitizeRawContent(String raw){
        if (raw == null) return "";
        // 불필요한 제어문자 제거
        return raw.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").trim();
    }
    /** JSON 배열 부분만 추출 */
    public String extractJsonArray(String clean){
        Matcher m = JSON_ARRAY_PATTERN.matcher(clean);
        if (m.find()){
            return m.group();
        }
        return clean;
    }

    /** 배열 형태 유효성 검사 */
    public boolean isValidJsonArray(String jsonArray){
        return JSON_ARRAY_PATTERN.matcher(jsonArray).matches();
    }

    /** JSON -> List<Long> 역 직렬화 */
    public List<Long> parseIdList(String jsonArray){
        try {
            return objectMapper.readValue(jsonArray, new TypeReference<List<Long>>() {});
        } catch (Exception e){
            log.error("JSON 파싱 실패: {}", jsonArray, e);
            return Collections.emptyList();
        }
    }

    /** parsing 실패시 폴백 */
    public List<Long> handleParseError(Exception ex){
        log.warn("추천 결과 파싱 오류, 빈 리스트 반환", ex);
        return Collections.emptyList();
    }

    /** 메트릭 기록 */
    public void recordMetrics(String raw, Duration elapsed, boolean success){
        log.info("Gemini 응답 길이: {}, 처리 시간: {}ms, 성공 여부: {}", raw.length(), elapsed.toMillis(), success);
    }

    /** 타임아웃 / 오류 감지 */
    public void detectAndHandleTimeout(Throwable t){
        log.error("LLM 호출 중 오류 발생" ,t);
    }
}
