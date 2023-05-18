package Tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtilsTest {
    private static final String filePath = "D://testexcel.xlsx";

    @Test
    public void testWriteAndReadExcel() {
        List<List<String>> inputData = new ArrayList<>();
        inputData.add(Arrays.asList("Name", "Age", "City"));
        inputData.add(Arrays.asList("John", "25", "New York"));
        inputData.add(Arrays.asList("Jane", "30", "London"));

        // 写入数据到 Excel 文件
        try {
            ExcelUtils.writeExcel(filePath, inputData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取 Excel 文件
        List<List<String>> outputData = null;
        try {
            outputData = ExcelUtils.readExcel(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 验证写入和读取的数据是否一致
        System.out.println(outputData);
        Assertions.assertEquals(inputData, outputData);
    }
}
