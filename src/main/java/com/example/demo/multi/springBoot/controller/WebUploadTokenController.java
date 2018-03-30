package com.example.demo.multi.springBoot.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.multi.springBoot.vo.WebUploadTokenVo;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web-uplaod上传工具token生成接口
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 10:28
 * @modefied:
 */
@RestController
@RequestMapping("token")
public class WebUploadTokenController {
    private static final String APP_ID = "bluemoon";
    private static final String SECRET_KEY = "sIZnX5jK7Fk&kUlKfho9GNNNY3ihQwKf";

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public WebUploadTokenVo create(Integer seconds, String params) {
        WebUploadTokenVo vo = new WebUploadTokenVo();

        // appId不可为空
        if (StrUtil.isBlank(APP_ID)) {
            return vo;
        }
        // seconds默认值
        seconds = (Integer) ObjectUtil.defaultIfNull(seconds, 60 * 60 * 2);

        try {
            Map<String, Object> paramsMap = null;
            if (StrUtil.isNotBlank(params)) {
                paramsMap = JSONUtil.toBean(params, HashMap.class);
            }
            String token = createNSecondsUploadToken(SECRET_KEY, seconds, paramsMap);
            vo.setAppId(APP_ID);
            vo.setToken(token);
            return vo;
        } catch (Exception e) {
            return vo;
        }
    }

    /**
     * 生成n秒有效期的token
     *
     * @param secretKey aes密钥
     * @param n token有效期，单位：s(秒second）
     * @param parmas 需要添加到token中的参数
     *
     * @return token字串
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static String createNSecondsUploadToken(String secretKey, int n, Map<String, Object> parmas) throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {
        if (ObjectUtils.isEmpty(parmas)) {
            parmas = new HashMap<String, Object>(2);
        }
        parmas.put("deadline", System.currentTimeMillis()/1000 + n);
        JSONObject paramsJsonObj = JSONUtil.parseFromMap(parmas);
        return createToken(paramsJsonObj.toStringPretty(), secretKey);
    }

    /**
     * 生成token字串
     * @param paramsJsonStr 需要添加到token中的参数字串，json格式
     * @param secretKey aes密钥
     *
     * @return token字串
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String createToken(String paramsJsonStr, String secretKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        byte[] aesEncryptedBytes = aesEncryptAndDecrypt(paramsJsonStr.getBytes(), secretKey.getBytes(), Cipher.ENCRYPT_MODE);
        String safeUrlBase64Str = Base64.getUrlEncoder().encodeToString(aesEncryptedBytes);
        return URLEncoder.encode(safeUrlBase64Str, "utf-8");
    }

    /**
     * AES encrypt and decrypt
     *
     * @param contentBytes wating to be encrypted or decrypted byte array
     * @param keyBytes secret key's byte array
     * @param mode AES's mode, default value is Cipher.ENCRYPT_MODE
     *
     * @return encrypted/decrypted byte array
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static byte[] aesEncryptAndDecrypt(byte[] contentBytes, byte[] keyBytes, Integer mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        /* set default mode */
        if (mode == null) {
            mode = Cipher.ENCRYPT_MODE;
        }
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(keyBytes);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, secretKey);
        return cipher.doFinal(contentBytes);
    }
}
