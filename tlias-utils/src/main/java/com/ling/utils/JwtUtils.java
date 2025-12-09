package com.ling.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT令牌工具类
 * 提供令牌生成和解析功能
 */
public class JwtUtils {

    // 密钥，按照要求设置为"ling"
    private static final String SECRET_KEY = "ling";
    
    // 过期时间，按照要求设置为12小时（单位：毫秒）
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     * @param claimsMap 自定义的载荷信息
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claimsMap) {
        // 创建一个可修改的HashMap，避免使用不可修改的Map.of()导致UnsupportedOperationException
        Map<String, Object> claims = new HashMap<>(claimsMap);
        
        // 计算过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        
        // 生成令牌
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用HS256算法和指定密钥签名
                .setClaims(claims) // 设置载荷信息
                .setExpiration(expirationDate) // 设置过期时间
                .compact(); // 压缩生成最终的令牌字符串
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌字符串
     * @return 令牌中的载荷信息
     */
    public static Claims parseToken(String token) {
        // 解析令牌并获取载荷信息
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置签名密钥用于验证
                .parseClaimsJws(token) // 解析JWT令牌
                .getBody(); // 获取载荷部分
    }

    /**
     * 检查令牌是否过期
     * @param token JWT令牌字符串
     * @return 如果令牌已过期返回true，否则返回false
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration().before(new Date());
    }

    /**
     * 从令牌中获取指定的载荷信息
     * @param token JWT令牌字符串
     * @param claimKey 载荷键名
     * @return 载荷值
     */
    public static Object getClaimFromToken(String token, String claimKey) {
        Claims claims = parseToken(token);
        return claims.get(claimKey);
    }
}