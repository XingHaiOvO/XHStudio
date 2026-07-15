package cn.xh_net.studio.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 邮件工具类
 * @author XingHai
 * @date 2026/7/15 16:07
 */
public class MailUtil {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final MailProperties mailProperties;

    /**
     * -- GETTER AND SETTER -- --
     *  获取和设置工作室名称
     */
    @Getter
    @Setter
    private String studioName;

    public MailUtil(JavaMailSender mailSender, TemplateEngine templateEngine, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.mailProperties = mailProperties;
    }

    /**
     * 发送邮件
     * @param email 收件人
     * @param type 邮件类型
     * @param subject 邮件主题
     * @param content 邮件模板数据
     * @throws MessagingException 异常
     */
    public void sendMail(String email, String type, String subject, Map<String, String> content)
            throws MessagingException, UnsupportedEncodingException {

        // 准备模板数据
        Context context = new Context();
        content.forEach(context::setVariable);
        context.setVariable("studioName", studioName);

        // 渲染模板，生成HTML
        String html = templateEngine.process(type, context);

        // 构建并发送邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
                mailProperties.getDefaultEncoding().toString());
        helper.setFrom(mailProperties.getUsername(), studioName);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(mimeMessage);
    }

}
