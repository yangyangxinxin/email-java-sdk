package link.yangxin.email.java.sdk.message;


import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 邮件附件
 * Created by yangxin on 2017/12/18.
 */
public class EmailAttachment implements Serializable {

    /**
     * 附件名
     */
    private String name;

    /**
     * 附件字节数组
     */
    private byte[] contents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public DataSource createDataSource() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(getContents())) {
            return new ByteArrayDataSource(inputStream, "application/octet-stream");
        }
    }
}
