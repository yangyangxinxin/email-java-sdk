package link.yangxin.email.java.sdk.sender;

import link.yangxin.email.java.sdk.client.EmailClient;
import link.yangxin.email.java.sdk.exception.EmailMessageException;
import link.yangxin.email.java.sdk.message.EmailAttachment;
import link.yangxin.email.java.sdk.message.EmailMessage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class EmailSenderExecutor implements EmailSender {

    private EmailClient emailClient;

    public EmailSenderExecutor(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public void send(EmailMessage emailMessage) {
        try {
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getInstance(emailClient.getProperties(), emailClient.getAuthenticator());
            sendMailSession.setDebug(emailClient.isDebug());

            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(emailClient.getUsername());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中

            mailMessage.setRecipients(Message.RecipientType.TO, emailMessage.getToAddress());
            Address[] ccAddress = emailMessage.getCcAddress();
            if (ccAddress != null && ccAddress.length > 0) {
                mailMessage.setRecipients(Message.RecipientType.CC, ccAddress);
            }
            Address[] bccAddress = emailMessage.getBccAddress();
            if (bccAddress != null && bccAddress.length > 0) {
                mailMessage.setRecipients(Message.RecipientType.BCC, bccAddress);
            }

            // 设置邮件消息的主题
            mailMessage.setSubject(emailMessage.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            MimeBodyPart mbpContent = new MimeBodyPart();
            // 设置邮件消息的主要内容
            String mailContent = emailMessage.getContent();

            mbpContent.setContent(mailContent, "text/html;charset=UTF-8");
            mainPart.addBodyPart(mbpContent);
            // 添加附件
            if (emailMessage.getEmailAttachments() != null) {

                List<EmailAttachment> emailAttachments = emailMessage.getEmailAttachments();
                for (EmailAttachment emailAttachment : emailAttachments) {
                    MimeBodyPart mbpart = new MimeBodyPart();
                    // 得到数据源
                    DataSource dataSource = emailAttachment.createDataSource();

                    // 得到附件本身并放入BodyPart
                    mbpart.setDataHandler(new DataHandler(dataSource));

                    // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
                    mbpart.setFileName(MimeUtility.encodeText(emailAttachment.getName()));

                    mainPart.addBodyPart(mbpart);
                }

            }
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
        } catch (Exception e) {
            throw new EmailMessageException(e.getMessage());
        }
    }
}