package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.password}")
    private String password;
    /**
     * 发送纯文本的简单邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            sender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发送html格式的邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String subject, String content){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
            logger.info("html邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            sender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     * 发送嵌入静态资源（一般是图片）的邮件
     * @param to
     * @param subject
     * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
     * @param rscPath 静态资源路径和文件名
     * @param rscId 静态资源id
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            sender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
    public  boolean sendMail(String subject, String toMail, String content, String fileName, InputStream is, String ccList) {
        boolean isFlag = false;
        try {
            Properties props = new Properties();
            props.put("smtp.163.com", host);   // 指定SMTP服务器
            props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);

            MimeMessage message = new MimeMessage(session);
            try {
                //指定发送人
                message.setFrom(new InternetAddress(from));
                //指定接收人
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                //指定抄送人
                if(ccList!=null){
                    message.addRecipients(Message.RecipientType.CC,ccList);
                }
                //设置标题
                message.setSubject(subject);
                message.addHeader("charset", "UTF-8");

                /*添加正文内容*/
                //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
                Multipart multipart = new MimeMultipart();

                MimeBodyPart contentPart = new MimeBodyPart();
                contentPart.setText(content,"UTF-8");
                contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
                multipart.addBodyPart(contentPart);

                /*添加附件*/
                if(is != null) {
                    MimeBodyPart fileBody = new MimeBodyPart();
                    DataSource source = new ByteArrayDataSource(is, "application/msexcel");
                    fileBody.setDataHandler(new DataHandler(source));
                    // 中文乱码问题
                    fileBody.setFileName(MimeUtility.encodeText(fileName));
                    multipart.addBodyPart(fileBody);
                }

                message.setContent(multipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport transport = session.getTransport("smtp");
                transport.connect(host, from, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                isFlag = true;
                logger.info(Calendar.getInstance().getTime()+":#Send mail to"+toMail+"success #");
            } catch (Exception e) {
                logger.info(Calendar.getInstance().getTime()+":#Send mail to"+toMail+"error #");
                logger.info(e.toString());
                e.printStackTrace();
                isFlag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFlag;
    }
}
