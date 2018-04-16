package link.yangxin.email.java.sdk.parser;


import freemarker.template.TemplateException;
import link.yangxin.email.java.sdk.exception.ParseException;
import link.yangxin.email.java.sdk.template.Template;

import java.io.*;

/**
 * Freemarker模板解析器
 * Created by yangxin on 2017/12/26.
 */
public class FreemarkerTemplateParser implements TemplateParser {

    @Override
    public String parse(Template template) throws ParseException {
        try {
            Reader reader = new FileReader(new File(template.getPath()));

            freemarker.template.Template temp = new freemarker.template.Template(System.currentTimeMillis() + "", reader, null, null);

            StringWriter result = new StringWriter();
            temp.process(template.getParameters(), result);
            return result.toString();
        } catch (IOException | TemplateException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
