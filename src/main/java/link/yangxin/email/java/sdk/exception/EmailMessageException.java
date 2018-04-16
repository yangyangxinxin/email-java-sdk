package link.yangxin.email.java.sdk.exception;

/**
 * Created by yangxin on 2017/12/29.
 */
public class EmailMessageException extends RuntimeException {

    private String code;

    private String msg;

    public EmailMessageException() {

    }

    public EmailMessageException(String msg) {
        this(msg, null);
    }

    public EmailMessageException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public EmailMessageException(Exception e) {
        this(e.getMessage());
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
