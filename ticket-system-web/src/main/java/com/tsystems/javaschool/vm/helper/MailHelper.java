package com.tsystems.javaschool.vm.helper;

import com.tsystems.javaschool.vm.converter.TicketConverter;
import com.tsystems.javaschool.vm.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailHelper {
    @Autowired
    private TicketConverter ticketConverter;

    public void sendEmail(String to, Ticket ticket) throws MessagingException {
        String subject = "SBB: ticket No " + ticket.getId();
        String body = ticketConverter.convertToString(ticket);

        sendEmail(to, subject, body);
    }

    public void sendEmail(String recipient, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    private static final String username = "sbb.javaschool@gmail.com";
    private static final String password = "javaschool123";
}
