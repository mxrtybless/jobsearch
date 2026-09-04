package kg.attractor.jobsearch.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.attractor.jobsearch.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class EmailServiceImpl
        implements EmailService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            MessageSource messageSource
    ) {
        this.mailSender =
                mailSender;

        this.messageSource =
                messageSource;
    }

    @Override
    public void send(
            String to,
            String link
    ) throws MessagingException,
            UnsupportedEncodingException {

        Locale locale =
                LocaleContextHolder.getLocale();

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message
                );

        helper.setFrom(
                emailFrom,
                getMessage(
                        "mail.reset.fromName",
                        locale
                )
        );

        helper.setTo(
                to
        );

        String subject =
                getMessage(
                        "mail.reset.subject",
                        locale
                );

        String content =
                "<p>"
                        + getMessage(
                        "mail.reset.hello",
                        locale
                )
                        + "</p>"
                        + "<p>"
                        + getMessage(
                        "mail.reset.request",
                        locale
                )
                        + "</p>"
                        + "<p>"
                        + getMessage(
                        "mail.reset.instruction",
                        locale
                )
                        + "</p>"
                        + "<p><a href=\""
                        + link
                        + "\">"
                        + getMessage(
                        "mail.reset.button",
                        locale
                )
                        + "</a></p>"
                        + "<br>"
                        + "<p>"
                        + getMessage(
                        "mail.reset.ignore",
                        locale
                )
                        + "</p>";

        helper.setSubject(
                subject
        );

        helper.setText(
                content,
                true
        );

        mailSender.send(
                message
        );
    }

    private String getMessage(
            String key,
            Locale locale
    ) {
        return messageSource.getMessage(
                key,
                null,
                locale
        );
    }
}