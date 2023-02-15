package com.javasm.commons.util;
import io.jsonwebtoken.security.Keys;
import com.javasm.commons.enums.Flags;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:SG
 * @classname: JwtUtil
 * @description:
 * @date: 2022/9/13 19:15
 * @version:0.1
 * @since:jdk11
 */
public class JwtUtil {


    public static void generateKey() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
    }


    public static String createJwt(String uid,String keyStr,Integer expireMinutes) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        Date date = new Date();
        Date endDate = new Date(date.getTime() + expireMinutes * 60 * 1000);

        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyStr));

        String jws = Jwts.builder()
                .setClaims(map)
                .setIssuedAt(date)
                .setExpiration(endDate)
                .signWith(key)
                .compact();
        return jws;
    }

    public static String parseJws(String token,String keyStr) {//t,key
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyStr));
        Jws<Claims> jws;

        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        return jws.getBody().get("uid",String.class);

        } catch (SignatureException ex) {
        return Flags.SignatureError;
        }catch (ExpiredJwtException ex) {
        return Flags.ExpiredJwtError;
        }catch (Exception ex) {
        return null;
        }
    }


}
