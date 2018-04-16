package link.yangxin.email.java.sdk.template;


import link.yangxin.email.java.sdk.parser.TemplateParser;

import java.io.Serializable;
import java.util.Map;

/**
 * 模板
 * Created by yangxin on 2017/12/26.
 */
public interface Template extends Serializable {

    /**
     * 模板路径
     *
     * @return
     */
    String getPath();

    /**
     * 模板变量
     *
     * @return
     */
    Map<String, Object> getParameters();

    /**
     * 模板解析器
     *
     * @return
     */
    TemplateParser getParser();

}
