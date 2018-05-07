package link.yangxin.email.java.sdk.test;

import link.yangxin.email.java.sdk.client.EmailClient;
import link.yangxin.email.java.sdk.receive.EmailReceiveClient;
import link.yangxin.email.java.sdk.message.EmailMessage;
import link.yangxin.email.java.sdk.message.template.EmailTemplateMessage;
import link.yangxin.email.java.sdk.receive.helper.MessageHelper;
import link.yangxin.email.java.sdk.sender.EmailSender;
import link.yangxin.email.java.sdk.sender.EmailSenderExecutor;
import link.yangxin.email.java.sdk.template.freemarker.FreemarkerTemplate;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.*;
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
        emailClient.setHost("smtpdm.aliyun.com");
        emailClient.setPassword("1qw3ee6rr0gDF");
        emailClient.setUsername("yangxin@yangxin.link");
        emailClient.setProtocol("smtp");
        emailClient.setPort(25);

        EmailSender emailSender = new EmailSenderExecutor(emailClient);
        Map<String, Object> map = new HashMap<>();
        map.put("a", "yangxin");
        map.put("b", "liujunyu");
        EmailMessage emailMessage = EmailTemplateMessage.create(new FreemarkerTemplate(EmailClient.class.getClassLoader().getResource("template/test.ftl").getFile(), map)).to("981987024@qq.com")
                .attach("C:\\Users\\Developer5\\Desktop\\ocr\\身份证.jpg").cc("848135512@qq.com").bcc("354394024@qq.com").subject("test");

        emailSender.send(emailMessage);
    }

    @Test
    public void testReceive() throws MessagingException, IOException {
        EmailReceiveClient client = new EmailReceiveClient();
        client.setPassword("yangxin19941023");
        client.setUsername("yangxin@ebaoquan.org");
        client.setServer("mail.ebaoquan.org");
        client.setProtocol("pop3");
        List<Message> messages = client.getMessages();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Message message = messages.get(0);
        String content = MessageHelper.create(message).getContent();
        System.out.println(content);

        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        OutputStream outputStream = new FileOutputStream(new File("a.html"));
        int n ;
        while((n = inputStream.read() ) != -1)
            outputStream.write(n);

        inputStream.close();
        outputStream.close();
    }

}