package com.fline.form.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.UserSessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @desc JWT工具类
 **/
public class JwtUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    public static final int EXPIRE_TIME =  60 * 60 * 1000 * 9; //9小时过期时间

    /**
     * 校验token是否正确
     *
     * @param token  令牌
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(UserSessionConst.TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            LOG.info("token过期，token：{}", token);
        } catch (Exception e) {
            LOG.info("token无效，token：{}", token);
        }
        return false;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(UserSessionConst.USERNAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的userSessionId
     */
    public static String getUserSessionId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(UserSessionConst.USER_SESSION_ID).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从cookie中获取
     * @return
     */
    public static long getUserSessionId() {
        String token = UserSessionCookieUtil.getCookieValue(UserSessionConst.TOKEN);
        return Detect.asLong(getUserSessionId(token), 0);
    }



    /**
     * 生成token
     * @param userSessionId
     * @param username
     * @return token
     */
    public static String generateToken(String userSessionId, String username) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(UserSessionConst.TOKEN_SECRET);
        // 附带username信息
        return JWT.create()
                .withClaim(UserSessionConst.USER_SESSION_ID, userSessionId)
                .withClaim(UserSessionConst.USERNAME, username)
                .withExpiresAt(date)
                .sign(algorithm);
 
    }

    public static void main(String[] args) {
        String token = generateToken("1","a");
        DecodedJWT decode = JWT.decode(token);
    }
}