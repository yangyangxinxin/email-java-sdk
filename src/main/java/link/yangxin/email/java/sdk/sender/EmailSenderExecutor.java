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
            emailMessage.validate();
            MimeMessage mimeMessage = emailClient.createMimeMessage();

            // 创建邮件发送者地址
            Address from = new InternetAddress(emailClient.getUsername());
            // 设置邮件消息的发送者
            mimeMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            mimeMessage.addRecipients(Message.RecipientType.TO, emailMessage.getToAddress());

            // 设置抄送人
            Address[] ccAddress = emailMessage.getCcAddress();
            if (ccAddress != null && ccAddress.length > 0) {
                mimeMessage.addRecipients(Message.RecipientType.CC, ccAddress);
            }

            // 设置密送人
            Address[] bccAddress = emailMessage.getBccAddress();
            if (bccAddress != null && bccAddress.length > 0) {
                mimeMessage.addRecipients(Message.RecipientType.BCC, bccAddress);
            }

            // 设置邮件消息的主题
            mimeMessage.setSubject(emailMessage.getSubject());
            // 设置邮件消息发送的时间
            mimeMessage.setSentDate(new Date());
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
            mimeMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailMessageException(e.getMessage());
        }
    }
}