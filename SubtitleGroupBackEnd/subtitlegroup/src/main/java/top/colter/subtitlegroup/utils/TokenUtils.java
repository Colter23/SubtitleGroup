package top.colter.subtitlegroup.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    public static String getToken(String id) {

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.YEAR, 1);

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("id", id);

        return JWTUtil.createToken(payload, DigestUtil.md5Hex(id).getBytes());
    }

    public static boolean verify(String token){
        JWT jwt = JWTUtil.parseToken(token);
        String id = (String) jwt.getPayload("id");
        //if (!jwt.validate(0)){
        //    return false;
        //}
        return jwt.setKey(DigestUtil.md5Hex(id).getBytes()).verify();
    }

}
