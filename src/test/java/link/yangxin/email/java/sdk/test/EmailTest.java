package link.yangxin.email.java.sdk.test;

import link.yangxin.email.java.sdk.client.EmailClient;
import link.yangxin.email.java.sdk.message.EmailMessage;
import link.yangxin.email.java.sdk.message.template.EmailTemplateMessage;
import link.yangxin.email.java.sdk.sender.EmailSender;
import link.yangxin.email.java.sdk.sender.EmailSenderExecutor;
import link.yangxin.email.java.sdk.template.freemarker.FreemarkerTemplate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class EmailTest {

    @Test
    public void test1() {
        EmailClient emailClient = new EmailClient();
        emailClient.setDebug(true);
        emailClient.setHost("smtp.exmail.qq.com");
        emailClient.setPassword("OInsBT2nFiphdoag");
        emailClient.setUsername("service@luckysweetheart.com");
        emailClient.setProtocol("smtp");
        emailClient.setPort(587);

        EmailSender emailSender = new EmailSenderExecutor(emailClient);
        Map<String, Object> map = new HashMap<>();
        map.put("a", "yangxin");
        map.put("b", "liujunyu");
        EmailMessage emailMessage = EmailTemplateMessage.create(new FreemarkerTemplate(EmailClient.class.getClassLoader().getResource("template/test.ftl").getFile(), map)).to("981987024@qq.com")
                .attach("C:\\Users\\Developer5\\Desktop\\ocr\\身份证.jpg");

        emailSender.send(emailMessage);


    }

}