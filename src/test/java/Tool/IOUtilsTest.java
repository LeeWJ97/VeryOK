package Tool;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilsTest {

    @Test
    void testTextFile() throws IOException {
        IOUtils.writeTextFile("D:/test.txt","测试haha\r\nsss","utf8");
        IOUtils.appendTextFile("D:/test.txt","aa啊aaa\r\nbbb","utf8");
        String readStr = IOUtils.readTextFile("D:/test.txt", "utf8");

        System.out.println(readStr);
        assertEquals("测试haha\r\nsssaa啊aaa\r\nbbb",readStr);
    }

    @Test
    void testBytesFile() throws IOException {
        IOUtils.writeBytesFile("D:/test.bin","test123啊".getBytes("gbk"));
        IOUtils.appendBytesFile("D:/test.bin","\r\n呃".getBytes("gbk"));
        ByteArrayOutputStream byteArrayOutputStream = IOUtils.readBytesFile("D:/test.bin");
        String result = byteArrayOutputStream.toString("gbk");
        System.out.println(result);

        assertEquals("test123啊\r\n呃",result);


    }


}