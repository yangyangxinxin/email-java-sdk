package link.yangxin.email.java.sdk.client;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class EmailClient {

    /**
     * The default protocol: 'smtp'
     */
    public static final String DEFAULT_PROTOCOL = "smtp";

    /**
     * 登录用户名,发件人
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 是否测试环境
     */
    private boolean debug;

    /**
     * 会话
     */
    private Session session;

    public EmailClient() {

    }

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.host);
        p.put("mail.smtp.port", this.port);
        p.put("mail.smtp.auth", "true");
        p.put("mail.transport.protocol", protocol);
        return p;
    }

    /**
     * 测试邮件连通性
     *
     * @throws MessagingException 不成功抛出该异常
     */
    public void ping() throws MessagingException {
        Transport transport = null;
        try {
            transport = connectTransport();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    protected Transport connectTransport() throws MessagingException {
        String username = getUsername();
        String password = getPassword();
        // probably from a placeholder
        if ("".equals(username)) {
            username = null;
            // in conjunction with "" username, this means no password to use
            if ("".equals(password)) {
                password = null;
            }
        }

        Transport transport = getTransport(getSession());
        transport.connect(getHost(), getPort(), username, password);
        return transport;
    }

    protected Transport getTransport(Session session) throws NoSuchProviderException {
        String protocol = getProtocol();
        if (protocol == null) {
            protocol = session.getProperty("mail.transport.protocol");
            if (protocol == null) {
                protocol = DEFAULT_PROTOCOL;
            }
        }
        return session.getTransport(protocol);
    }

    /**
     * 根据邮件会话属性和密码验证器构造一个发送邮件的session
     *
     * @return
     */
    public synchronized Session getSession() {
        if (this.session == null) {
            this.session = Session.getInstance(getProperties(), getAuthenticator());
            session.setDebug(isDebug());
        }
        return session;
    }

    /**
     * 根据session创建一个邮件消息
     *
     * @return
     */
    public MimeMessage createMimeMessage() {
        return new MimeMessage(getSession());
    }

    public Authenticator getAuthenticator() {
        return new MailAuthenticator(username, password);
    }

    // ------------------------getters and setters

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}