package com.cma.common;

import com.cma.Batch;
import com.cma.Student;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 20/12/2555
 * Time: 11:45 น.
 * To change this template use File | Settings | File Templates.
 */
public class MailManager
{
    private JavaMailSenderImpl mailSender;
    /*private String filepath = "d:/tmp/attachfile/";*/
    private String filepath = "/cma/http/attachMail/";

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Student std_profile,String from, String to ,String cc, String bcc , String subject, String msg) {
        Batch batch = std_profile.getStudentClass();

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true ,"UTF-8");
            helper.setFrom(new InternetAddress(from));
            if(!cc.equals("")){
                helper.setCc(new InternetAddress(cc));
            }

            if (!bcc.equals("")){
                helper.setBcc(new InternetAddress(bcc));
            }
            helper.setTo(new InternetAddress(to));
            helper.setSubject(subject);
            helper.setText(msg,true);

            if(batch.getUploadfile1() && batch.getFilename1()!=null){
                FileSystemResource file = new FileSystemResource(filepath+ batch.getFilename1());
                helper.addAttachment(file.getFilename(),file);
            }
            if(batch.getUploadfile2() && batch.getFilename2()!=null){
                FileSystemResource file = new FileSystemResource(filepath+ batch.getFilename2());
                helper.addAttachment(file.getFilename(),file);
            }
            if(batch.getUploadfile3() && batch.getFilename3()!=null){
                FileSystemResource file = new FileSystemResource(filepath+ batch.getFilename3());
                helper.addAttachment(file.getFilename(),file);
            }
            if(batch.getUploadfile4() && batch.getFilename4()!=null){
                FileSystemResource file = new FileSystemResource(filepath+ batch.getFilename4());
                helper.addAttachment(file.getFilename(),file);
            }
            if(batch.getUploadfile5() && batch.getFilename5()!=null){
                FileSystemResource file = new FileSystemResource(filepath+ batch.getFilename5());
                helper.addAttachment(file.getFilename(),file);
            }
           /* message.setContent(msg,"text/html; charset=utf-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);*/
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        mailSender.send(message);
    }
}
