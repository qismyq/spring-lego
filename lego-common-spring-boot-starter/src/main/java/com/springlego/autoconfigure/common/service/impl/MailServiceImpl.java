package com.springlego.autoconfigure.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.springlego.autoconfigure.common.service.IMailService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @Description 邮件服务
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/20 14:51
 **/
@Slf4j
@ConfigurationProperties(prefix = "spring.mail")
@Service
public class MailServiceImpl implements IMailService {


    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    private String from;


    @Async
    @Override
    public void sendSimpleMailAndCC(String[] toUsers, String[] ccUsers, String subject, String content) throws Exception {
        log.info("Sending a simple mail...");

        if (StringUtils.isBlank(from)) {
            return;
        }
        if (ArrayUtils.isEmpty(toUsers)) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        // 发送方
        message.setFrom(from);
        // 接收者
        message.setTo(toUsers);
        // 主题
        message.setSubject(subject);
        // 内容
        message.setText(content);
        // cc
        if (ArrayUtils.isNotEmpty(ccUsers)) {
            message.setCc(ccUsers);
        }
        try {
            javaMailSender.send(message);
            log.info("End of sending simple email...");
        } catch (Exception e) {
            log.error("send simple mail failed:{}", e.getLocalizedMessage());
        }

    }


    @Async
    @Override
    public void sendSimpleMail(String[] toUsers, String subject, String content) throws Exception {
        sendSimpleMailAndCC(toUsers, null, subject, content);
    }


    @Async
    @Override
    public void sendHtmlMailAndCC(String[] toUsers, String[] ccUsers, String subject, String content) throws Exception {
        log.info("Sending a html mail...");

        if (StringUtils.isBlank(from)) {
            return;
        }
        if (ArrayUtils.isEmpty(toUsers)) {
            return;
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(from);
        helper.setTo(toUsers);
        helper.setSubject(subject);
        helper.setText(content, true);

        if (ArrayUtils.isNotEmpty(ccUsers)) {
            helper.setCc(ccUsers);
        }

        try {
            javaMailSender.send(mimeMessage);
            log.info("End of sending html email...");
        } catch (Exception e) {
            log.error("send html mail failed:{}", e.getLocalizedMessage());
        }

    }


    @Async
    @Override
    public void sendHtmlMail(String[] toUsers, String subject, String content) throws Exception {
        sendHtmlMailAndCC(toUsers, null, subject, content);
    }
}
