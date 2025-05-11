package advancedweb.project.aiengineservice.domain.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GeminiResponseProcessorTest {

    private final GeminiResponseProcessor processor = new GeminiResponseProcessor();

    @Test
    void sanitizeAndExtract_validJsonArray() {
        String raw = "Here you go: [101, 207], thanks!";
        String cleaned = processor.sanitizeRawContent(raw);
        String json = processor.extractJsonArray(cleaned);

        assertTrue(processor.isValidJsonArray(json), "JSON 배열 형식이 유효해야 한다");
        assertEquals("[101, 207]", json);
    }

    @Test
    void parseIdList_success() {
        String json = "[5,10,15]";
        List<Long> ids = processor.parseIdList(json);

        assertEquals(3, ids.size());
        assertEquals(List.of(5L, 10L, 15L), ids);
    }

    @Test
    void invalidArray_returnsEmptyList() {
        String bad = "no json here";
        assertFalse(processor.isValidJsonArray(bad), "유효하지 않은 배열은 false를 반환해야 한다");
        List<Long> ids = processor.parseIdList(bad);
        assertTrue(ids.isEmpty(), "잘못된 JSON 배열 파싱 시 빈 리스트를 반환해야 한다");
    }

    @Test
    void handleParseError_returnsEmptyList() {
        List<Long> fallback = processor.handleParseError(new RuntimeException("test error"));
        assertNotNull(fallback, "fallback 리스트는 null이 아니어야 한다");
        assertTrue(fallback.isEmpty(), "에러 발생 시 빈 리스트를 반환해야 한다");
    }
}
