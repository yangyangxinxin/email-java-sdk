package link.yangxin.email.java.sdk.template.freemarker;


import link.yangxin.email.java.sdk.parser.FreemarkerTemplateParser;
import link.yangxin.email.java.sdk.parser.TemplateParser;
import link.yangxin.email.java.sdk.template.BaseTemplate;

import java.util.Map;

/**
 * Freemarker 模板
 * Created by yangxin on 2017/12/26.
 */
public class FreemarkerTemplate extends BaseTemplate {

    public FreemarkerTemplate(String path, Map<String, Object> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    @Override
    public TemplateParser getParser() {
        return new FreemarkerTemplateParser();
    }
}
