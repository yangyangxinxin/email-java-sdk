package link.yangxin.email.java.sdk.util;

import java.io.*;
import java.util.Objects;

/**
 * @author yangxin
 * @date 2018/4/16
 */
public class FileUtils {

    /**
     * 获取classPath下的文件路径
     *
     * @param name  文件名（如果有文件夹需要加文件夹名）
     * @param clazz Class
     * @return
     */
    public static String getClassPathFile(String name, Class clazz) {
        return Objects.requireNonNull(clazz.getClassLoader().getResource(name)).getFile();
    }

    public static byte[] readFileToByteArray(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException();
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             InputStream inputStream = new FileInputStream(file)) {
            int n;
            while ((n = inputStream.read()) != -1) {
                outputStream.write(n);
            }
            return outputStream.toByteArray();
        }
    }

}