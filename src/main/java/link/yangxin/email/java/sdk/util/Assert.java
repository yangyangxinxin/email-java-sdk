package link.yangxin.email.java.sdk.util;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class Assert {

    public static void isTrue(boolean a, String b) {
        if (!a) {
            throw new IllegalArgumentException(b);
        }
    }

    public static void notNull(Object obj, String msg) {
        isTrue(obj != null, msg);
    }

}