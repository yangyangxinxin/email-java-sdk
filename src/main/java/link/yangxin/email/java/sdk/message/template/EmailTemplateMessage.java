package link.yangxin.email.java.sdk.message.template;


import link.yangxin.email.java.sdk.exception.ParseException;
import link.yangxin.email.java.sdk.message.EmailMessage;
import link.yangxin.email.java.sdk.template.Template;

/**
 * 模板邮件消息
 * Created by yangxin on 2017/12/18.
 */
public class EmailTemplateMessage extends EmailMessage {

    /**
     * 模板类型。是Freemarker还是Velocity或其他模板
     */
    private Template template;

    private EmailTemplateMessage() {

    }

    public static EmailTemplateMessage create() {
        return new EmailTemplateMessage();
    }

    public static EmailTemplateMessage create(Template template) {
        return create().template(template);
    }

    public EmailTemplateMessage template(Template template) {
        this.template = template;
        return this;
    }

    public Template getTemplate() {
        return template;
    }

    @Override
    public String getContent() throws ParseException {
        return template.getParser().parse(template);
    }

    @Override
    public void validate() {
        // 模板消息的验证条件
        super.validate();
    }
}
