package com.github.pplociennik.auth.business.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import static java.util.Objects.requireNonNull;

/**
 * A Spring's configuration class for mailing beans definition.
 *
 * @author Created by: Pplociennik at 30.06.2022 20:32
 */
@Configuration
class AuthEmailBeanConfig {

    private final Environment environment;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    public AuthEmailBeanConfig( @NonNull Environment aEnvironment, @NonNull TemplateEngine aTemplateEngine, @NonNull JavaMailSender aMailSender ) {
        environment = requireNonNull( aEnvironment );
        templateEngine = requireNonNull( aTemplateEngine );
        mailSender = requireNonNull( aMailSender );
    }

    @Bean
    MailingPropertiesProvider mailingPropertiesProvider() {
        return new MailingPropertiesProvider( environment );
    }

    @Bean
    EmailService emailService() {
        return new EmailService( mailingPropertiesProvider(), templateEngine, mailSender );
    }

    @Bean
    EmailFacade emailFacade() {
        return new EmailFacade( emailService() );
    }
}
