package Tool;


import java.io.*;

public class IOUtil {
    private IOUtil(){}

    /**
     * 读取文本文件内容
     *
     * @param filePath  文件路径
     * @param encoding  文件编码
     * @return  文件内容
     * @throws IOException 读取文件时发生的异常
     */
    public static String readTextFile(String filePath, String encoding) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            StringBuilder totalLine = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                totalLine.append(line).append("\r\n");
            }
            if (totalLine.length() >= 2) {
                totalLine.setLength(totalLine.length() - 2);
            }
            return totalLine.toString();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 写入文本文件内容
     *
     * @param filePath  文件路径
     * @param content   要写入的内容
     * @param encoding  文件编码
     * @throws IOException 写入文件时发生的异常
     */
    public static void writeTextFile(String filePath, String content, String encoding) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), encoding))) {
            writer.write(content);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 追加文本文件内容
     *
     * @param filePath  文件路径
     * @param content   要追加的内容
     * @param encoding  文件编码
     * @throws IOException 追加文件时发生的异常
     */
    public static void appendTextFile(String filePath, String content, String encoding) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, true), encoding))) {
            writer.write(content);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 读取二进制文件内容
     *
     * @param filePath  文件路径
     * @return  二进制文件内容
     * @throws IOException 读取文件时发生的异常
     */
    public static ByteArrayOutputStream readBytesFile(String filePath) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];  //length
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 写入二进制文件内容
     *
     * @param filePath  文件路径
     * @param content   要写入的二进制内容
     * @throws IOException 写入文件时发生的异常
     */
    public static void writeBytesFile(String filePath, byte[] content) throws IOException {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
            outputStream.write(content);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 追加二进制文件内容
     *
     * @param filePath  文件路径
     * @param content   要追加的二进制内容
     * @throws IOException 追加文件时发生的异常
     */
    public static void appendBytesFile(String filePath, byte[] content) throws IOException {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath, true))) {
            outputStream.write(content);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void main(String[] args) throws IOException {
        // 示例用法
        IOUtil.writeTextFile("D:/test.txt","测试haha\r\nsss","utf8");
        IOUtil.appendTextFile("D:/test.txt","aa啊aaa\r\nbbb","utf8");
        String readStr = IOUtil.readTextFile("D:/test.txt", "utf8");
        System.out.println(readStr);

        IOUtil.writeBytesFile("D:/test.bin","test123啊".getBytes("gbk"));
        IOUtil.appendBytesFile("D:/test.bin","\r\n呃".getBytes("gbk"));
        ByteArrayOutputStream byteArrayOutputStream = IOUtil.readBytesFile("D:/test.bin");
        System.out.println(byteArrayOutputStream.toString("gbk"));
    }
}
