package link.yangxin.email.java.sdk.demo;

import link.yangxin.email.java.sdk.client.MailAuthenticator;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class MailSender {
    /**
     * 以文本格式发送邮件
     *
     * @param mailInfo 待发送邮件的信息
     */
    public boolean sendTextMail(MailSenderInfo mailInfo)
            throws Exception {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties props = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getInstance(props, authenticator);

        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mailInfo.getToAddress());
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        MimeBodyPart mbpContent = new MimeBodyPart();
        // 设置邮件消息的主要内容
        String mailContent = mailInfo.getContent();
        mbpContent.setText(mailContent);
        mainPart.addBodyPart(mbpContent);
        // 添加附件
        if (mailInfo.getAttachFileNames().length != 0) {
            String[] arrayOfString;
            int j = (arrayOfString = mailInfo.getAttachFileNames()).length;
            for (int i = 0; i < j; ++i) {
                String attachFile = arrayOfString[i];
                MimeBodyPart mbpart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(attachFile); // 得到数据源
                mbpart.setDataHandler(new DataHandler(fds));   // 得到附件本身并放入BodyPart
                mbpart.setFileName(MimeUtility.encodeText(fds.getName()));  // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
                mainPart.addBodyPart(mbpart);
            }
        }
        mailMessage.setContent(mainPart);
        // 发送邮件
        Transport.send(mailMessage);
        return true;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param mailInfo 待发送邮件的信息
     */
    public boolean sendHtmlMail(MailSenderInfo mailInfo)
            throws Exception {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mailInfo.getToAddress());
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 添加附件
        if (mailInfo.getAttachFileNames().length != 0) {
            String[] arrayOfString;
            int j = (arrayOfString = mailInfo.getAttachFileNames()).length;
            for (int i = 0; i < j; ++i) {
                String attachFile = arrayOfString[i];
                html = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(attachFile); // 得到数据源
                html.setDataHandler(new DataHandler(fds));   // 得到附件本身并放入BodyPart
                html.setFileName(MimeUtility.encodeText(fds.getName()));  // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
                mainPart.addBodyPart(html);
            }
        }
        // 将MimeMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);
        // 发送邮件
        Transport.send(mailMessage);

        return true;
    }

    /**
     * @param SMTP    邮件服务器
     * @param PORT    端口
     * @param EMAIL   发送方邮箱账号
     * @param PAW     发送方邮箱密码
     * @param toEMAIL 接收方邮箱账号
     * @param TITLE   标题
     * @param CONTENT 内容
     * @param FJ      附件
     * @param TYPE    1：文本格式;2：HTML格式
     */
    public static void sendEmail(String SMTP, String PORT, String EMAIL, String PAW, String toEMAIL, String TITLE, String CONTENT, String[] FJ, String TYPE)
            throws Exception {
        // 设置邮件信息
        MailSenderInfo mailInfo = new MailSenderInfo();

        mailInfo.setMailServerHost(SMTP);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(EMAIL);
        mailInfo.setPassword(PAW);
        mailInfo.setFromAddress(EMAIL);
        mailInfo.setToAddress(toEMAIL);
        mailInfo.setSubject(TITLE);
        mailInfo.setContent(CONTENT);
        mailInfo.setAttachFileNames(FJ);

        // 发送邮件
        MailSender sms = new MailSender();

        if ("1".equals(TYPE)) {
            sms.sendTextMail(mailInfo);
        } else {
            sms.sendHtmlMail(mailInfo);
        }
    }

    public static void main(String[] args) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("1@qq.com");
        mailInfo.setPassword("123");
        mailInfo.setFromAddress("1@qq.com");
        mailInfo.setToAddress("3@qq.com");
        mailInfo.setSubject("设置邮箱标题");
        mailInfo.setContent("设置邮箱内容");

        MailSender sms = new MailSender();
        // sms.sendTextMail(mailInfo);//发送文体格式
        // sms.sendHtmlMail(mailInfo);//发送HTML格式
    }

}