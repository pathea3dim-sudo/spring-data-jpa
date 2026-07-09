//package com.example.ecommerceiteapp.feature.email;
//
//import com.example.ecommerceiteapp.feature.auth.dto.RegisterRequest;
//import jakarta.mail.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    @Value("${app.frontend.url:http://localhost:3000}")
//    private String frontendUrl;
//
//    @Value("${app.email.welcome.subject:Welcome to Ecommerce App!}")
//    private String welcomeSubject;
//
//    @Value("${app.email.welcome.from:noreply@ecommerce-app.com}")
//    private String fromEmail;
//
//    @Async
//    public void sendWelcomeEmail(RegisterRequest registerRequest) {
//        try {
//            log.info("Sending welcome email to: {}", registerRequest.email());
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setFrom(fromEmail);
//            helper.setTo(registerRequest.email());
//            helper.setSubject(welcomeSubject);
//
//            String htmlContent = buildWelcomeEmailHtml(registerRequest);
//            helper.setText(htmlContent, true);
//
//            mailSender.send(message);
//            log.info("Welcome email sent successfully to: {}", registerRequest.email());
//
//        } catch (Exception e) {
//            log.error("Failed to send welcome email to: {}", registerRequest.email(), e);
//        }
//    }
//
//    private String buildWelcomeEmailHtml(RegisterRequest request) {
//        return String.format("""
//            <!DOCTYPE html>
//            <html>
//            <head>
//                <meta charset="UTF-8">
//                <meta name="viewport" content="width=device-width, initial-scale=1.0">
//                <title>Welcome to Ecommerce App</title>
//                <style>
//                    body {
//                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
//                        line-height: 1.6;
//                        color: #333;
//                        margin: 0;
//                        padding: 0;
//                        background-color: #f4f4f4;
//                    }
//                    .container {
//                        max-width: 600px;
//                        margin: 20px auto;
//                        padding: 20px;
//                        background-color: #ffffff;
//                        border-radius: 10px;
//                        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
//                    }
//                    .header {
//                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
//                        color: white;
//                        padding: 30px 20px;
//                        text-align: center;
//                        border-radius: 10px 10px 0 0;
//                    }
//                    .header h1 {
//                        margin: 0;
//                        font-size: 28px;
//                    }
//                    .content {
//                        padding: 30px 20px;
//                    }
//                    .content h2 {
//                        color: #667eea;
//                        margin-top: 0;
//                    }
//                    .details-box {
//                        background-color: #f8f9fa;
//                        padding: 20px;
//                        border-radius: 8px;
//                        margin: 20px 0;
//                        border-left: 4px solid #667eea;
//                    }
//                    .details-box ul {
//                        list-style: none;
//                        padding: 0;
//                        margin: 0;
//                    }
//                    .details-box ul li {
//                        padding: 8px 0;
//                        border-bottom: 1px solid #e9ecef;
//                    }
//                    .details-box ul li:last-child {
//                        border-bottom: none;
//                    }
//                    .details-box strong {
//                        color: #495057;
//                        display: inline-block;
//                        width: 100px;
//                    }
//                    .button-container {
//                        text-align: center;
//                        margin: 30px 0;
//                    }
//                    .button {
//                        display: inline-block;
//                        padding: 14px 35px;
//                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
//                        color: white;
//                        text-decoration: none;
//                        border-radius: 25px;
//                        font-weight: 600;
//                    }
//                    .footer {
//                        text-align: center;
//                        padding: 20px;
//                        font-size: 12px;
//                        color: #6c757d;
//                        border-top: 1px solid #e9ecef;
//                        margin-top: 20px;
//                    }
//                </style>
//            </head>
//            <body>
//                <div class="container">
//                    <div class="header">
//                        <h1>🎉 Welcome to Ecommerce!</h1>
//                    </div>
//                    <div class="content">
//                        <h2>Hello %s %s,</h2>
//                        <p>Thank you for registering with our Ecommerce platform!</p>
//                        <div class="details-box">
//                            <ul>
//                                <li><strong>Username:</strong> %s</li>
//                                <li><strong>Email:</strong> %s</li>
//                                <li><strong>Full Name:</strong> %s %s</li>
//                                <li><strong>Phone:</strong> %s</li>
//                            </ul>
//                        </div>
//                        <div class="button-container">
//                            <a href="%s/login" class="button">Login to Your Account</a>
//                        </div>
//                        <p>Best regards,<br><strong>The Ecommerce Team</strong></p>
//                    </div>
//                    <div class="footer">
//                        <p>This is an automated message, please do not reply.</p>
//                        <p>&copy; 2026 Ecommerce App. All rights reserved.</p>
//                    </div>
//                </div>
//            </body>
//            </html>
//            """,
//                request.firstName(),
//                request.lastName(),
//                request.username(),
//                request.email(),
//                request.firstName(),
//                request.lastName(),
//                request.phoneNumber(),
//                frontendUrl
//        );
//    }
//}