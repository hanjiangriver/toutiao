package com.zhj.ToutiaoUtil;

import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class MailSender implements InitializingBean{

	  private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
	    private JavaMailSenderImpl mailSender;

	    @Autowired
	    private VelocityEngine velocityEngine;

	    public boolean sendWithHTMLTemplate(String to, String subject,
	                                        String template, Map<String, Object> model) {
	        try {
	            String nick = MimeUtility.encodeText("江上轻风");
	            InternetAddress from = new InternetAddress(nick + "<573449958@qq.com>");
	            MimeMessage mimeMessage = mailSender.createMimeMessage();
	            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	            String result = VelocityEngineUtils
	                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
	            mimeMessageHelper.setTo(to);
	            mimeMessageHelper.setFrom(from);
	            mimeMessageHelper.setSubject(subject);
	            mimeMessageHelper.setText(result, true);
	            mailSender.send(mimeMessage);
	            return true;
	        } catch (Exception e) {
	            logger.error("发送邮件失败" + e.getMessage());
	            return false;
	        }
	    }
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		mailSender = new JavaMailSenderImpl();

        // 请输入自己的邮箱和密码，用于发送邮件
        mailSender.setUsername("573449958@qq.com");
        mailSender.setPassword("vyuieiftinkbbfgf");
        mailSender.setHost("smtp.qq.com");
        // 请配置自己的邮箱和密码

        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
	}

}
