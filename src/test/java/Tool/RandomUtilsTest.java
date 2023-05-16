package Tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilsTest {
    @Test
    void testGenerateRandomString() {
        String randomString = RandomUtils.generateRandomString(15, true, false, false, false);
        System.out.println(randomString);
        assertEquals(15, randomString.length());
        assertTrue(randomString.matches("[0-9]+"));

        // 测试生成包含数字和小写字母的长度为10的随机字符串
        String randomString1 = RandomUtils.generateRandomString(10, true, true, false, false);
        System.out.println(randomString1);
        assertEquals(10, randomString1.length());
        assertTrue(randomString1.matches("[0-9a-z]+"));

        // 测试生成包含大小写字母和特殊字符的长度为8的随机字符串
        String randomString2 = RandomUtils.generateRandomString(8, false, true, true, true);
        System.out.println(randomString2);
        assertEquals(8, randomString2.length());
        assertTrue(randomString2.matches("[a-zA-Z!@#$%^&*()_+\\-=\\[\\]{}|;':,.<>?]+"));
    }

    @Test
    void testGenerateRandomString_withInvalidArguments() {
        // 测试当不选择任何字符类型时，是否抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            RandomUtils.generateRandomString(8, false, false, false, false);
        });

        assertThrows(RuntimeException.class, () -> {
            RandomUtils.generateRandomString(-1,true,true,true,true);
        });
    }
}