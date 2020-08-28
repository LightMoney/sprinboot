package cn.fan.testfunction.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BCrypt加密解密工具
 */
public class BCryptUtil {
    /**
     * 用于加密
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPass = passwordEncoder.encode(password);
        return hashPass;
    }

    /**
     * 用于验证
     *
     * @param password
     * @param hashPass
     * @return
     */
    public static boolean matches(String password, String hashPass) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(password, hashPass);
        return f;
    }

    public static void main(String[] args) {
        System.out.println(BCryptUtil.matches("cdhtgs681005","$2a$10$2pjEue61utB.j/E2GCzpS.MX5IBozSTQUnACjCeKIZja7ynqtq7P2"));
    }
}
