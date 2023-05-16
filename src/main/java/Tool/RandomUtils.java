package Tool;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;':,.<>?";
    private RandomUtils() {
    }
    public static String generateRandomString(int length, boolean includeDigits, boolean includeLowerCase,
                                              boolean includeUpperCase, boolean includeSpecialChars) {
        StringBuilder characters = new StringBuilder();
        if (includeDigits) {
            characters.append(DIGITS);
        }
        if (includeLowerCase) {
            characters.append(LOWER_CASE);
        }
        if (includeUpperCase) {
            characters.append(UPPER_CASE);
        }
        if (includeSpecialChars) {
            characters.append(SPECIAL_CHARS);
        }

        if (characters.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be included.");
        }

        Random random = new SecureRandom();
        StringBuilder result = new StringBuilder(length);
        List<Character> charList = new ArrayList<>(characters.length());
        for (char c : characters.toString().toCharArray()) {
            charList.add(c);
        }

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charList.size());
            result.append(charList.get(randomIndex));
        }

        return result.toString();
    }


        public static void main(String[] args) {
            // 生成包含数字和小写字母的长度为10的随机字符串
            String randomString = RandomUtils.generateRandomString(10, true, true, false, false);
            System.out.println(randomString);

            // 生成包含大小写字母和特殊字符的长度为8的随机字符串
            String randomString2 = RandomUtils.generateRandomString(8, false, true, true, true);
            System.out.println(randomString2);
        }


}
