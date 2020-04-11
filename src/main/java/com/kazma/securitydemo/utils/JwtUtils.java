package com.kazma.securitydemo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kazma.securitydemo.constants.JwtConstants;
import com.kazma.securitydemo.entity.user.vo.UserToken;
import com.kazma.securitydemo.utils.date.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class JwtUtils {

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();
    private static final SecretKey SECRET_KEY = generalKey();

    public static String getLoginToken(UserToken userToken) throws JsonProcessingException {
        return Jwts.builder().
                setSubject(JSON_MAPPER.writeValueAsString(userToken)).
                setExpiration(DateUtils.dateTimeToDateZH(LocalDateTime.now().plusDays(1))).
                signWith(SECRET_KEY).
                compact();
    }

    public static UserToken validationJwtAndGetSubject(String jwt) throws JsonProcessingException {
        Claims claims = Jwts.parserBuilder().
                setSigningKey(SECRET_KEY).
                build().
                parseClaimsJws(jwt).
                getBody();

        return JSON_MAPPER.readValue(claims.getSubject(), UserToken.class);
    }

    private static SecretKey generalKey() {
        byte[] encodedKey = JwtConstants.JWT_SEC_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS256.getJcaName());
    }

}
