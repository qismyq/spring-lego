package com.springlego.autoconfigure.common.service;

/**
 * @Description 发送邮件服务
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/22 19:36
 **/
public interface IMailService {

    /**
     * @param toUsers 收件人
     * @param ccUsers 抄送人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @desc: 发送简易邮件并cc
     * @return: void
     * @auther: Michael Wong
     * @email: michael_wong@yunqihui.net
     * @date: 2019/11/22 19:31
     * @update:
     */
    public void sendSimpleMailAndCC(String[] toUsers, String[] ccUsers, String subject, String content) throws Exception;

    /**
     * @param toUsers 收件人
     * @param subject 主题
     * @param content 内容
     * @desc: 发送简易邮件
     * @return: void
     * @auther: Michael Wong
     * @email: michael_wong@yunqihui.net
     * @date: 2019/11/22 19:32
     * @update:
     */
    public void sendSimpleMail(String[] toUsers, String subject, String content) throws Exception;

    /**
     * 发送邮件-邮件正文是HTML
     * 并cc
     * @param toUsers 收件人
     * @param subject 主题
     * @param content 内容
     * @throws Exception
     * @return: void
     * @auther: Michael Wong
     * @email: michael_wong@yunqihui.net
     * @date: 2019/11/22 19:32
     * @update:
     */
    public void sendHtmlMailAndCC(String[] toUsers, String[] ccUsers, String subject, String content) throws Exception;

    /**
     * 发送邮件-邮件正文是HTML
     * @param toUsers 收件人
     * @param subject 主题
     * @param content 内容
     * @throws Exception
     * @return: void
     * @auther: Michael Wong
     * @email: michael_wong@yunqihui.net
     * @date: 2019/11/22 19:32
     * @update:
     */
    public void sendHtmlMail(String[] toUsers, String subject, String content) throws Exception;
}
