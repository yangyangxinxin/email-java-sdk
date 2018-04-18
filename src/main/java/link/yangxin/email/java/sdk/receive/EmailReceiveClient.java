package link.yangxin.email.java.sdk.receive;

import javax.mail.*;
import java.util.*;

/**
 * @author yangxin
 * @date 2018/4/18
 */
public class EmailReceiveClient {

    /**
     * The Folder is read only.  The state and contents of this
     * folder cannot be modified.
     */
    public static final int READ_ONLY = 1;

    /**
     * The state and contents of this folder can be modified.
     */
    public static final int READ_WRITE = 2;

    /**
     * 服务器ip
     */
    private String server;

    /**
     * 邮箱用户名
     */
    private String username;

    /**
     * 邮箱密码
     */
    private String password;

    /**
     * 服务类型
     */
    private String protocol;

    private Store store;

    private Session session;

    private Folder folder;

    /**
     * 获取邮件的配置
     *
     * @return
     */
    public Properties getEmailProperties() {
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", protocol);
        // 发件人的邮箱的 SMTP服务器地址
        props.setProperty("mail.smtp.host", server);
        return props;
    }

    /**
     * 得到邮件会话对象
     *
     * @return
     */
    public synchronized Session getSession() {
        if (session == null) {
            session = Session.getDefaultInstance(getEmailProperties());
            session.setDebug(false);
        }
        return session;
    }

    /**
     * 获取store对象
     *
     * @return
     * @throws NoSuchProviderException
     */
    public synchronized Store getStore() throws NoSuchProviderException {
        if (store == null) {
            Session session = getSession();
            this.store = session.getStore(getProtocol());
        }
        return store;
    }

    /**
     * 打开文件夹
     *
     * @param permission 权限
     * @throws MessagingException
     */
    public Folder openFolder(Integer permission) throws MessagingException {
        // POP3服务器的登陆认证
        getStore().connect(server, username, password);
        // 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        // 获得用户的邮件帐户
        folder = getStore().getFolder("INBOX");
        // 设置对邮件帐户的访问权限
        folder.open(permission);
        return folder;
    }

    /**
     * 打开文件夹 默认以 “读写” 权限打开
     *
     * @return
     * @throws MessagingException
     */
    public Folder openFolder() throws MessagingException {
        return openFolder(READ_WRITE);
    }

    public void closeFolder() throws MessagingException {
        if (folder != null && folder.isOpen()) {
            folder.close(true);
        }
    }

    private void checkFolder() {
        if (folder == null) {
            throw new NullPointerException();
        }
        if (!folder.isOpen()) {
            throw new RuntimeException("folder is not opened");
        }
    }

    /**
     * 拉取消息
     *
     * @param permission 读写权限
     * @return
     * @throws MessagingException
     */
    public List<Message> getMessages(Integer permission) throws MessagingException {
        Folder folder = openFolder(permission);
        checkFolder();
        Message[] messages = folder.getMessages();
        if (messages != null) {
            return new ArrayList<>(Arrays.asList(messages));
        }
        return null;
    }

    public List<Message> getMessages(Integer permission, int start, int end) throws MessagingException {
        Folder folder = openFolder(permission);
        checkFolder();
        Message[] messages = folder.getMessages(start, end);
        if (messages != null) {
            return new ArrayList<>(Arrays.asList(messages));
        }
        return null;
    }

    public List<Message> getMessages(int start, int end) throws MessagingException {
        return getMessages(READ_WRITE, start, end);
    }

    public List<Message> getMessages() throws MessagingException {
        return getMessages(READ_WRITE);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Folder getFolder() {
        return folder;
    }

}