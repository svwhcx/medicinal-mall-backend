package com.medicinal.mall.mall.demos.util;

import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.token.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @description Token工具类
 * @Author cxk
 * @Date 2022/4/30 16:42
 */
public class TokenUtil {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_SECRET = "*^%=joh#ads";

    /**
     * 解析Token
     */

    public static Claims parse(String jwt, String secretKey){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new ParamException(ResponseDataEnum.TOKEN_VERIFY_FAIL);
        }
        return claims;
    }

    public static Claims parse(String jwt){
        return parse(jwt,TOKEN_SECRET);
    }

    /**
     * 生成 token
     * @param claims
     * @param millisecond
     * @return
     */
    public static String genToken(Map<String,Object> claims, int millisecond){
        //获取当前的时间
        Calendar calendar = Calendar.getInstance();
        //获取系统当前时间
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        //失效的时间
        calendar.add(Calendar.MILLISECOND,millisecond);
        //拿到预定过期时间的日期
        Date endTime = calendar.getTime();
        JwtBuilder jwtBuilder = Jwts.builder()
                //签名算法
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                //签发时间
                .setIssuedAt(new Date())
                //到期时间
                .setClaims(claims)
                .setExpiration(endTime)
                //playLoad
                //签发者
                .setIssuer("OJ")
                //接收者
                .setAudience("swustoj");
        return TOKEN_PREFIX + jwtBuilder.compact();
    }

    private static<T> T getInfoData(Claims claims,String key,Class<T> clazz){
        T t;
        try {
             t = claims.get(key, clazz);
        } catch (Exception e) {
            throw new ParamException(ResponseDataEnum.INVALID_TOKEN);
        }
        return t;
    }

    /**
     * 是否是合法的token
     * @param token
     * @return
     */
    public static boolean isValid(String token){
        return token.contains(TOKEN_PREFIX);
    }

    /**
     * 从Token中获取token信息
     * @param token
     * @return
     */
    public static TokenInfo getTokenInfo(String token){
        token = token.replaceAll(TOKEN_PREFIX,"");
        Claims parse = parse(token);
        parse.get("",String.class);
        return new TokenInfo.Builder()
                .username(getInfoData(parse,"username",String.class))
                .userId(getInfoData(parse,"userId",Integer.class))
                .role(getInfoData(parse,"roleId",Integer.class))
                .build();
    }

}
