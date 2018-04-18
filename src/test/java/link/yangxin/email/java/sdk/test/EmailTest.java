package link.yangxin.email.java.sdk.test;

import link.yangxin.email.java.sdk.client.EmailClient;
import link.yangxin.email.java.sdk.receive.EmailReceiveClient;
import link.yangxin.email.java.sdk.message.EmailMessage;
import link.yangxin.email.java.sdk.message.template.EmailTemplateMessage;
import link.yangxin.email.java.sdk.sender.EmailSender;
import link.yangxin.email.java.sdk.sender.EmailSenderExecutor;
import link.yangxin.email.java.sdk.template.freemarker.FreemarkerTemplate;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class EmailTest {

    @Test
    public void testPing() throws MessagingException {
        EmailClient emailClient = new EmailClient();
        emailClient.setDebug(true);
        emailClient.setHost("smtp.exmail.qq.com");
        emailClient.setPassword("OInsBT2nFiphdoag");
        emailClient.setUsername("service@luckysweetheart.com");
        emailClient.setProtocol("smtp");
        emailClient.setPort(587);

        emailClient.ping();
    }

    @Test
    public void test1() {
        EmailClient emailClient = new EmailClient();
        emailClient.setDebug(false);
        emailClient.setHost("smtp.exmail.qq.com");
        emailClient.setPassword("OInsBT2nFiphdoag");
        emailClient.setUsername("service@luckysweetheart.com");
        emailClient.setProtocol("smtp");
        emailClient.setPort(587);

        EmailSender emailSender = new EmailSenderExecutor(emailClient);
        Map<String, Object> map = new HashMap<>();
        map.put("a", "yangxin");
        map.put("b", "liujunyu");
        EmailMessage emailMessage = EmailTemplateMessage.create(new FreemarkerTemplate(EmailClient.class.getClassLoader().getResource("template/test.ftl").getFile(), map)).to("yangxin_y@qq.com")
                .attach("C:\\Users\\Developer5\\Desktop\\ocr\\身份证.jpg").cc("848135512@qq.com").bcc("981987024@qq.com");

        emailSender.send(emailMessage);
    }

    @Test
    public void testReceive() throws MessagingException, IOException {
        EmailReceiveClient client = new EmailReceiveClient();
        client.setPassword("");
        client.setUsername("");
        client.setServer("");
        client.setProtocol("pop3");
        List<Message> messages = client.getMessages();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Object content = messages.get(0).getContent();
        System.out.println(content);
        System.out.println(messages.get(0).getSubject() + "====" + simpleDateFormat.format(messages.get(0).getSentDate()));
    }

}