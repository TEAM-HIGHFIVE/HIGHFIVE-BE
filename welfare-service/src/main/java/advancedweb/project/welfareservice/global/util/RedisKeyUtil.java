package advancedweb.project.welfareservice.global.util;

import advancedweb.project.welfareservice.domain.entity.enums.Area;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RedisKeyUtil {
    public static String hashKey(String original) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            // or return Hex.encodeHexString(hash); if you want hex
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public static void main(String[] args) {
        String korean = "서울특별시 영등포구 여의도동";
        String redisKey = "address:" + hashKey(korean);
        System.out.println("Redis key: " + redisKey);
    }
}
