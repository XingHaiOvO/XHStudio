package cn.xh_net.studio.utils;

import cn.xh_net.studio.prpperties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * jwt 工具类
 * @author XingHai
 * @date 2026/7/12 23:27
 * @description 用于生成、解析和验证 jwt 令牌
 */
@Component
public class JwtUtil {

    // Jwt令牌有效期
    private static Long JWT_TTL;

    // Jwt密钥
    private static String JWT_KEY;

    public JwtUtil(JwtProperties jwtProperties) {
        JWT_TTL = jwtProperties.getTtl();
        JWT_KEY = jwtProperties.getSecretKey();
    }

    /**
     * 生成唯一标识
     * @return 唯一标识
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建 jwt 令牌
     * @param subject 令牌主题
     * @return jwt 令牌
     */
    public static String createJwt(String subject) {
        return getJwtBuilder(subject, null, getUUID()).compact();
    }


    /**
     * 创建 jwt 令牌
     * @param subject 令牌主题
     * @param ttlMillis 令牌过期时间（毫秒）
     * @return jwt 令牌
     */
    public static String createJwt(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
    }

    /**
     * 创建 jwt 令牌
     * @param subject 令牌主题（
     * @param ttlMillis 令牌过期时间（毫秒）
     * @param uuid 令牌唯一标识
     * @return jwt 令牌
     */
    public static String createJwt(String subject, Long ttlMillis, String uuid) {
        return getJwtBuilder(subject, ttlMillis, uuid).compact();
    }

    /**
     * 获取 jwt 构建器
     * @param subject 令牌主题
     * @param ttlMillis 过期时间(毫秒)
     * @return jwt 构建器
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {

        SecretKey key = encryptKey();
        Long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = ttlMillis == null ? nowMillis + JwtUtil.JWT_TTL : nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        return Jwts
                .builder()
                .id(uuid)    // 令牌唯一标识
                .subject(subject)     // 令牌主题
                .issuer("xh-net")    // 令牌发行者
                .issuedAt(now)   // 令牌发行时间
                .signWith(key, Jwts.SIG.HS256)  // 签名算法和密钥
                .expiration(exp);     // 令牌过期时间
    }

    /**
     * 生成加密的密钥
     * @return 加密的密钥
     */
    private static SecretKey encryptKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, "HmacSHA256");
    }

    /**
     * 解析 jwt 令牌
     * @param jwt jwt 令牌
     * @return 解析后的 jwt 令牌主题
     */
    public static Claims parseJwt(String jwt) {
        SecretKey secretKey = encryptKey();
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
