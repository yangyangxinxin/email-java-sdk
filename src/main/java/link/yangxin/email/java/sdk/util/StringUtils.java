package link.yangxin.email.java.sdk.util;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class StringUtils {

    public static String[] split(String str, String b) {
        if (isBlank(str)) {
            return null;
        }
        return str.split(b);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}