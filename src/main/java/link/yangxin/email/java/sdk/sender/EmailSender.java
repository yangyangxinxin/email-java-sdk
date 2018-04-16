package link.yangxin.email.java.sdk.sender;

import link.yangxin.email.java.sdk.message.EmailMessage;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public interface EmailSender {

    /**
     * 发送邮件
     *
     * @param
     */
    void send(EmailMessage emailMessage);

}