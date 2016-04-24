/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.email;

import java.io.UnsupportedEncodingException;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Czuczi
 */
@Stateless
public class EmailSessionBean {
    
    @Inject
    private org.slf4j.Logger log;

    private static final String USERNAME = "unizonwebshop@gmail.com";
    private static final String PASSWORD = "szrt2016";

    public void sendEmail(String from, String subject, String text, String name) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            InternetAddress internetAddress = new InternetAddress(USERNAME);
            internetAddress.setPersonal(name + " <" + from + ">");
            message.setFrom(internetAddress);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(USERNAME));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }
}
