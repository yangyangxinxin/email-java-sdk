package link.yangxin.email.java.sdk.parser;


import link.yangxin.email.java.sdk.exception.ParseException;
import link.yangxin.email.java.sdk.template.Template;

/**
 * 模板解析器
 * Created by yangxin on 2017/12/26.
 */
public interface TemplateParser extends Parser {

    /**
     * 解析
     *
     * @param template
     * @return
     * @throws ParseException 模板解析错误时抛出该异常
     */
    String parse(Template template) throws ParseException;

}
