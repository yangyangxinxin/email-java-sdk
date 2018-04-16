package link.yangxin.email.java.sdk.exception;

/**
 * 解析模板、内容发生的异常
 * Created by yangxin on 2018/1/4.
 */
public class ParseException extends Exception {

    private String code;

    private String msg;

    public ParseException() {
    }

    public ParseException(String msg) {
        this(msg, null);
    }

    public ParseException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
