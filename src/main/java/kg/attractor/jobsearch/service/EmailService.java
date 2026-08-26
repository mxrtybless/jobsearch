package kg.attractor.jobsearch.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void send(
            String to,
            String link
    ) throws MessagingException,
            UnsupportedEncodingException;
}
