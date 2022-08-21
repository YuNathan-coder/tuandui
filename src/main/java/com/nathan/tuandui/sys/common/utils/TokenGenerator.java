package com.nathan.tuandui.sys.common.utils;

import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.handler.TokenException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author shizewang
 * @version 1.0
 * @description： token生成器
 * @date 2021/2/21 1:04 上午
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new TokenException(ErrorCodeEnums.TOKEN_GENERATE_FAILURE.code,
                    ErrorCodeEnums.TOKEN_GENERATE_FAILURE.message);
        }
    }
}
