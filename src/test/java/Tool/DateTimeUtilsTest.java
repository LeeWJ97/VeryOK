package Tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilsTest {

    @Test
    public void formatDate_ShouldReturnFormattedDate() {
        // Arrange
        Date date = new Date();

        // Act
        String formattedDate = DateTimeUtils.formatDate(date);

        // Assert
        assertEquals("2023", formattedDate.substring(0,4));
    }

    @Test
    public void parseDate_ShouldReturnParsedDate() throws ParseException {
        // Arrange
        String dateString = "2009-02-13 23:31:30";

        // Act
        Date parsedDate = DateTimeUtils.parseDate(dateString);

        // Assert
        assertEquals(1234539090000L, parsedDate.getTime());
    }

    @Test
    public void getCurrentTimestamp_ShouldReturnCurrentTimestamp() {
        // Act
        long timestamp = DateTimeUtils.getCurrentTimestamp();

        // Assert
        Assertions.assertTrue(timestamp > 0);
    }

    @Test
    public void timestampToDate_ShouldConvertTimestampToDate() {
        // Arrange
        long timestamp = 1234567890000L;

        // Act
        Date date = DateTimeUtils.timestampToDate(timestamp);

        // Assert
        assertEquals(timestamp, date.getTime());
    }

    @Test
    public void DatetoStr() throws ParseException {
        // Arrange
        String dateString_dd_MMM_yyyy = "03-Feb-2023 13:23:21";


        // Act
        Date parsedDate_dd_MMM_yyyy = DateTimeUtils.parseDate(dateString_dd_MMM_yyyy, "dd-MMM-yyyy HH:mm:ss", Locale.US);
        String str_dd_MMM_yyyy = DateTimeUtils.formatDateText(parsedDate_dd_MMM_yyyy);

        // Assert
        System.out.println("解析03-Feb-2023后的时间为字符串：" + str_dd_MMM_yyyy);
        assertEquals("2023-02-03 13:23:21", str_dd_MMM_yyyy);
    }
}
