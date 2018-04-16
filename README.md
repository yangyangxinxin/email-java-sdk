# email-java-sdk

## 意在简化邮件相关的操作。相信在每个项目中都会有发送邮件的需求。目前没有看到类似封装好的邮件发送的sdk，所以本项目诞生了。


## 项目的依赖很少，如下：

```
    <!--邮件必须所用的-->
     <dependency>
            <groupId>com.sun.mail</groupId>
            <artifactId>javax.mail</artifactId>
            <version>1.5.6</version>
        </dependency>

        <!--如果是freemarker模板邮件，那么需要加入此依赖-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.27-incubating</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

```

## 项目抽象了很多内容，邮件消息抽象，可以实现简单文本邮件、模板邮件等；模板抽象，可以自定义实现各种模板，例如freemarker、velocity等等；

## 先来一个hello world吧

```java

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


```

