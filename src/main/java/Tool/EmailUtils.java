package Tool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

    public static void sendEmail(String fromEmail, String password, String toEmail, String subject, String message, String host, String port) throws MessagingException {
        // 邮件发送者信息
        String from = fromEmail;

        // 邮件接收者信息
        String to = toEmail;

        // 邮件登录凭据
        final String username = fromEmail;
        final String userPassword = password;

        // 创建Properties对象并设置邮件服务器相关配置
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        if ("587".equals(port)) {
            props.put("mail.smtp.starttls.enable", "true");   //587端口, office365 outlook仅支持587
        }
        else if ("465".equals(port)){
            props.put("mail.smtp.ssl.enable", "true");  //465端口
        }


        // 创建Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, userPassword);
            }
        });

        try {
            // 创建MimeMessage对象
            MimeMessage mimeMessage = new MimeMessage(session);

            // 设置发件人
            mimeMessage.setFrom(new InternetAddress(from));

            // 设置收件人
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // 设置邮件主题
            mimeMessage.setSubject(subject);

            // 设置邮件正文
            mimeMessage.setText(message);

            // 发送邮件
            Transport.send(mimeMessage);

            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            e.setNextException(new MessagingException("邮件发送失败"));
            throw e;
        }


    }


    public static void main(String[] args) throws MessagingException {
        String fromEmail = "test_qq.com";
        String password = "test";
        String toEmail = "test_qq.com";
        String subject = "Hello!";
        String message = "This is a test email.";
        String host = "smtp.qq.com";
        String port = "465";

        EmailUtils.sendEmail(fromEmail, password, toEmail, subject, message, host, port);
    }
}
