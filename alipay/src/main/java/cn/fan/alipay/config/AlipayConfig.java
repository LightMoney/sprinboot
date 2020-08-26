package cn.fan.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
//    你的沙箱APPID
    public static String APP_ID = "2021000117612049";

    // 商户私钥，您的PKCS8格式RSA2私钥
//    你自己的私钥
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC09upXDz0GKccXQ2K7MzyoCNyuggd5iA1IpsAfTTHShvqc849EVI0/ZhxH4mEY2Su/JUIE+yXD8d84A/nOZzUtOSmfGBr0CT+HLOOielPyka1tcJzTqu44SA7NH80so1JpFbDx9mVH4ahAZE5rBp6db/zNwhsja0RA/cQU+A2tjGER0rL+zC/wvX+KL4X7TCzhWr6A4Nggy8S+DdMiUlqFaijJrMmLO0T0vfi+dfeQrh1AnSt6MPM6LgQB5Ec61+/X6WIIxvXrDSGeRambVZiqqsESy9S2PgrhwBojengb0EOzXkkY+XBbC7oT8GHNPmePn4o/OQOWTn8rGDcmbe1pAgMBAAECggEANy5Tpthus0JqAx35LTwictrA8DWWMejapBy02NahKqEeKl3ageuGZp6sYP8WvIz8WfzydLJgga9vcZVExzuRa4jSuECRd3iado2pnBHRJ1Zkm8qLB8BHg8H1QXdBCSuEJacHncCUgaa9t2TexKPwGfHcFKHrxJEg7vByqjI5eYGhiqaLY/xac3Peh67p6z5NgIx0f4AD2zaKF4z4iWiVPBuRJWRidKxdMVzdtiVRJVXn3q+xaRHokZcnG3Io84GA5xENKRsivDohHPZ7SHPa9WR+hMNRWJuOc+lh19YAOH40HCxX5tmKTRofK5i4dyguYkWu/OHr2mcSX++iOzN9QQKBgQDcYGrPXB8Viw62TKXzj7ctiwE0qWBnzM4I1ndXpagvmvyxwpsqihMN+DC9zFtFIkLnENoccmSFP8EMYEC+ZzGav+uBI2gRjniYfMDs13Cemv7Dz02XXDPYwVLsei8yTOqIb/xXvUr4TwPIixtOOyr04UrIVk0XutZHoQ1DeP9mhwKBgQDSN4266u3kSgi9aNFJ8BagqJ4jAG/LMDG6FZ0+vGToPgrplxZwkQ6SBQgD+g8gJ9IjrlUyYIFkTNMiBpE8gfCth0MaJ+CwAGL37PNP8WhQpHuRVYGlUhX+RxB1/3u+9hDRRDJWxj6LOoBOGt7c2Me+yy3MVIQY/xiKQUE9U2EYjwKBgD7Nx4LC2ST1lULEw0KPQX/cLoLIhm3ugwUnwakbz7uJatxp/nh4EwgXJCTi+VvRcMmTFuWmgBktWyHgyHK6y/szGLWb1ouyU5H5c63LlA/PUi2RhaZOI4RxS+WSyKKIIwqxB3QlwRjUw9pCMozv9cUF8572YVAS8gBF2JbTA84/AoGAN046UYThVYBM0Fg1R7iMm3QBNsA0qUZirsxk9E5oVdmM0WvYkYD4Tl5Yf4NYW2Nem9iPvZM6N4MmwPrtVvfwtBa0BrubF3LilsuWL+AZS64afF/8ndv8wUs8/25ZxiHCxuukOsQpTEP14ZacL4ow8vdNc0aBAnqKoEUOvu2iy+0CgYEA2ky6lqpmSk1WIDePimqgROkp4xG6pfMSWlO+eE3eSJqCnudmCRrf26hr/kOIZyPGBD9h59AP9JlwSuvJOyGtPuaAMy0MFhqxmB9gdLTOV7aCsBwDAZY6N4wr/+7SeDxJA2dbV22sih084lCDTooWnKlU1waphC3qyPtpAE1x4Tk=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    你自己的支付宝公钥，注意是支付宝公钥，不是应用公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgGXw2PPohWhQQA1t/qq+8E8axjwWY1KWU8YnUwc79J3QQ0pd2d0iEflD0OqrFdp9cda+H/OUjQjLtMksowmWPwsTkL6e6DQlVv1/kygWE50vu9ZCpuvT9djb5sXxYNEgSYz2MHlHSk+0nm2GrhdMX3TsnsrgirybVdZQ2RvE41A/TuuSxRzDpwz8oHRxSXeIal3BGooxFVaXAvMsc9xCFiUwos1qRsRgJtugpqWsldKgZ0Q0Jfz2qDOgG4L2GBZVL5pPb7Cx7nbwVUx9SPubW7bHGxPhSTpFf094Yp3EB+l2y474Xb5iin9PgUf/50jvJmWcw1w5YYFKAiq2JuTHSQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

//    扫码付款页面return_url
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    // 调试用，创建TXT日志文件夹路径 见AlipayCore.java类中logResult（string sWord） 打印方法
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
