package cn.xh_net.studio.config;

import cn.xh_net.studio.entity.SiteConfig;
import cn.xh_net.studio.mapper.SiteConfigMapper;
import cn.xh_net.studio.utils.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

/**
 * 邮件配置类
 * @author XingHai
 * @date 2026/7/15 18:21
 */
@Configuration
public class MailConfig {

    private final SiteConfigMapper siteConfigMapper;

    private final TemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    private final MailProperties mailProperties;

    public MailConfig(SiteConfigMapper siteConfigMapper, TemplateEngine templateEngine, JavaMailSender mailSender, MailProperties mailProperties) {
        this.siteConfigMapper = siteConfigMapper;
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    /**
     * 获取邮件工具类
     * @return 邮件工具类
     */
    @Bean
    public MailUtil getMailUtil() {
        String studioName = siteConfigMapper.selectOne(new LambdaQueryWrapper<SiteConfig>().eq(SiteConfig::getConfigKey, "studio_name")).getConfigValue();
        MailUtil mailUtil = new MailUtil(mailSender, templateEngine, mailProperties);
        mailUtil.setStudioName(studioName);
        return mailUtil;
    }

}
