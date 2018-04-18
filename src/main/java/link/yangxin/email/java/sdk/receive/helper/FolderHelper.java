package link.yangxin.email.java.sdk.receive.helper;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author yangxin
 * @date 2018/1/16
 */
public class FolderHelper {

    private Folder folder;

    private FolderHelper() {
    }

    private FolderHelper(Folder folder) {
        this.folder = folder;
    }

    public static FolderHelper create(Folder folder) {
        return new FolderHelper(folder);
    }

    public Folder getFolder() {
        return folder;
    }

    public void close() {
        if (folder != null && folder.isOpen()) {
            try {
                folder.close(true);
            } catch (MessagingException ignored) {
            }
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
     * 获得邮件夹Folder内的所有邮件个数
     *
     * @return
     */
    public int messageCount() {
        checkFolder();
        try {
            return folder.getMessageCount();
        } catch (Exception ignored) {

        }
        return 0;
    }

    public Message[] getMessage(int start, int end) {
        checkFolder();
        try {
            return folder.getMessages(start, end);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
