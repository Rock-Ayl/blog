package cn.anyongliang.util;

import java.util.Random;

/**
 * create by Rock-Ayl 2019-6-13
 * 生成提取码和手机短信6位验证码
 */
public class PwdTools {

    //生成手机登录时的6位数字随机验证码 eg 859213
    public static String getMobileCode() {
        String chars = "0123456789";
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int rand = (int) (Math.random() * 10);
            str.append(chars.charAt(rand));
        }
        return str.toString();
    }

    //获取验证过的随机密码
    public static String getRandomPassword(int len) {
        String result;
        if (len >= 6) {
            for (result = makeRandomPassword(len); len >= 6; result = makeRandomPassword(len)) {
                if (result.matches(".*[a-z]{1,}.*") && result.matches(".*[A-Z]{1,}.*") &&
                        result.matches(".*\\d{1,}.*") && result.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
                    return result;
                }
            }
        }
        while (len >= 6) {
            result = makeRandomPassword(len);
            if (result.matches(".*[a-z]{1,}.*") && result.matches(".*[A-Z]{1,}.*") && result.matches(".*\\d{1,}.*") && result.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
                return result;
            }
            makeRandomPassword(len);
        }
        return "长度不得少于6位!";
    }

    //随机密码生成
    public static String makeRandomPassword(int len) {
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }

}

