package com.atguigu.crowd.utils;

import com.atguigu.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 判断当前是否是ajax
     * @param request 请求对象
     * @return true 当前请求是ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String XRequestHeader = request.getHeader("X-Requested-With");
        // 判断
        return (acceptHeader != null && acceptHeader.contains("application/json")) ||
                (XRequestHeader != null && "XMLHttpRequest".equals(XRequestHeader));
    }

    /**
     * 对明文字符串进行md5加密
     * @param source
     * @return 加密结果
     */
    public static String md5(String source) {
        // 判断source是否有效
        if (source == null || source.length() == 0) {
            // 如果不是有效的字符串抛异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 获取明文字符串对应的字符数组
            byte[] input = source.getBytes();
            // 加密
            byte[] output = messageDigest.digest(input);
            // 创建一个BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
