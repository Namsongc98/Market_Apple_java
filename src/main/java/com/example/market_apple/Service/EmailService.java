package com.example.market_apple.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.market_apple.Enum.LoginStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendLoginNotification(String username, String email, String role, LoginStatus status) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Người gửi: bạn nên đặt một email cố định (vd: no-reply@domain.com)
        helper.setFrom(email);
        helper.setTo("namsongc98@gmail.com");
        helper.setSubject("User Login Notification");

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // HTML template
        String htmlTemplate = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding:20px;">
                    <div style="max-width:600px;margin:0 auto;background:white;border-radius:10px;padding:20px;box-shadow:0 0 10px rgba(0,0,0,0.1)">
                        <h2 style="color:#2d89ef;text-align:center;">🔔 User Login Notification</h2>
                        <table style="width:100%%;border-collapse:collapse;margin-top:20px;">
                            <tr>
                                <td style="padding:8px;border:1px solid #ddd;"><b>Username</b></td>
                                <td style="padding:8px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px;border:1px solid #ddd;"><b>Email</b></td>
                                <td style="padding:8px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px;border:1px solid #ddd;"><b>Role</b></td>
                                <td style="padding:8px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px;border:1px solid #ddd;"><b>Login Time</b></td>
                                <td style="padding:8px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px;border:1px solid #ddd;"><b>Status</b></td>
                                <td style="padding:8px;border:1px solid #ddd;">%s</td>
                            </tr>
                        </table>
                        <p style="margin-top:20px;color:#555;text-align:center;">This is an automated message from the system.</p>
                    </div>
                </body>
                </html>
                """;

        // ✅ dùng String.format thay vì .formatted
        String html = String.format(htmlTemplate, username, email, role, time, status);

        helper.setText(html, true);

        mailSender.send(mimeMessage);
        System.out.println(">>> HTML email notification sent to admin!");
    }
}
