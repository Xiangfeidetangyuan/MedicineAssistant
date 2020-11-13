package org.unboxing.medicineassistant.util.mail;

public class mail {
    private static final String HOST = "smtp.126.com";
    private static final String PORT = "25"; //或者465  994
    private static final String FROM_ADD = "hitwh_jzy@126.com";
    private static final String FROM_PSW = "WDTMGFMREVPRBKEE";
    private static final String TO_ADD = "xujiachen9@163.com";

    public static void send(String toAdd,String code){
        final MailInfo mailInfo = creatMail(toAdd,code);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }
    public static void send(String toAdd,String message,String subject){
        final MailInfo mailInfo = creatMail(toAdd,message);
        mailInfo.setSubject(subject);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    private static MailInfo creatMail(String toAdd,String code) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject("用药助手验证码"); // 邮件主题
        mailInfo.setContent(code); // 邮件文本
        return mailInfo;
    }
}