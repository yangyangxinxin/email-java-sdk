package link.yangxin.email.java.sdk.client;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 发送邮件时的身份验证器
 *
 * @author yangxin
 * @date 2018/4/16
 */
public class MailAuthenticator extends Authenticator {

    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户口令
     */
    private String password;

    /**
     * @param username
     * @param password
     */
    public MailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 身份验证
     *
     * @return
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}