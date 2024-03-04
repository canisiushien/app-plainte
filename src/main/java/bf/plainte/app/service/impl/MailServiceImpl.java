/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            InternetAddress fromAddress = new InternetAddress("no_reply@plainte.org", "VOTRE PLAINTE");
            helper.setReplyTo(fromAddress);
            helper.setFrom(fromAddress);
            helper.setText(body, true);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(MailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        javaMailSender.send(message);
    }

    /**
     * permet de verifier la validité d'une email
     *
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        boolean isValid = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            isValid = false;
        }
        return isValid;
    }
}
