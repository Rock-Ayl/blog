package cn.anyongliang.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * created by anyongliang at 2018-12-20
 * <p>
 * 存放着邮件的工具类
 */
public class MailUtil {

    /**
     * 发送邮件
     * created by anyongliang on 2018圣诞节-夜
     *
     * @param email     收件人email
     * @param body      <body></body>
     * @param userAddr  发件人email
     * @param emailHost 发件人邮箱host
     * @param password  发件人授权码
     */
    public static void sendMail(String email, String body, String userAddr, String emailHost, String password, String userName) {
        // 收件人电子邮箱（需要修改）
        String to = email;
        // 发件人电子邮箱（需要修改）
        String from = userAddr;
        // 指定发送邮件的主机服务器
        // 上163或者其他邮箱的网页版-设置中开启smtp权限才可以（必须），然后按照权限上的写
        String host = emailHost;  //163邮箱默认的邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发送者邮箱账户名 （需要修改）     授权码（必须修改）-开启smtp权限后自己设置
                return new PasswordAuthentication(userAddr, password);
            }
        });
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject(userName);
            // 设置<body></body>和字符集
            message.setContent(body, "text/html;charset=utf-8");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}