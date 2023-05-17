package Tool;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

class EmailUtilsTest {

    @Test
    void testSendEmailWithInvalidCredentials() {
        String fromEmail = "your_email@qq.com";
        String password = "your_password";
        String toEmail = "recipient_email@example.com";
        String subject = "Hello!";
        String message = "This is a test email.";
        String host = "smtp.qq.com";
        String port = "465";

        MessagingException exception = assertThrows(MessagingException.class,() -> {
            EmailUtils.sendEmail(fromEmail, password, toEmail, subject, message, host, port);
        });


        String expectedErrorMessage = "535 Login Fail";
        String actualErrorMessage = exception.getMessage();

        System.out.println(actualErrorMessage);
        assertTrue(actualErrorMessage.contains(expectedErrorMessage));

    }
}