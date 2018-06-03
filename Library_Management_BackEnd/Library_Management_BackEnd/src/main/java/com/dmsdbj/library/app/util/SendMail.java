package com.dmsdbj.library.app.util;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * 邮件开发的工具类--王华伟--2018年4月27日09:32:55
 */
public class SendMail{
    /**
     * 发送邮件的方法
     *  @param from  收件人
     * @param bccUser  抄送人
     */
    public static void sendMail(String from,String bccUser){
        /*
         * 1、获得1个Session对象
         * 2、创建一个代表邮件的对象Message
         * 3、发送邮件transport
         */
        //1、获得对连接象
        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.tfjybj.com");
        props.setProperty("mail.smtp.auth","true");
        props.setProperty("mail.transport.protocol","smtp");

        Session session=Session.getInstance(props, new Authenticator(){

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dmsd@tfjybj.com","TGBgm@6688");
            }

        });
        //2、创建邮件对象
        Message message = new MimeMessage(session);

        //设置自定义发件人名称
        String SendPersonName="";
        try {
            SendPersonName = MimeUtility.encodeText("大米时代邮件提醒");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        //邮件设置
        try {
            // 设置发件人:
            message.setFrom(new InternetAddress(SendPersonName+"<"+"dmsd@tfjybj.com"+">"));
            // 设置收件人:
            InternetAddress[] addresses = {new InternetAddress(from)};
            message.addRecipients(RecipientType.TO, addresses);
            // 设置多个抄送地址
            if(null != bccUser && !bccUser.isEmpty()){
                @SuppressWarnings("static-access")
                InternetAddress[] internetAddressCC = new InternetAddress().parse(bccUser);
                message.setRecipients(Message.RecipientType.CC, internetAddressCC);
            }

            //  密送BCC
            // 设置标题
            message.setSubject("还书提醒");
            // 设置邮件正文:
            message.setContent("您所借阅的书即将到期，请您尽快归还，以免影响他人的使用，谢谢合作","text/plain;charset=GB2312");
            // 3.发送邮件:
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
