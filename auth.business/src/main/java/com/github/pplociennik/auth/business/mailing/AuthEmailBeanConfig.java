package com.github.pplociennik.auth.business.mailing;

import com.github.pplociennik.auth.business.shared.system.SystemPropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final SystemPropertiesProvider propertiesProvider;

    @Autowired
    public AuthEmailBeanConfig(
            @NonNull TemplateEngine aTemplateEngine,
            @NonNull JavaMailSender aMailSender, SystemPropertiesProvider aPropertiesProvider ) {
        templateEngine = requireNonNull( aTemplateEngine );
        mailSender = requireNonNull( aMailSender );
        propertiesProvider = requireNonNull( aPropertiesProvider );
    }

    @Bean
    EmailService emailService() {
        return new EmailService( mailSender, propertiesProvider, templateEngine );
    }

    @Bean
    EmailFacade emailFacade() {
        return new EmailFacade( emailService() );
    }
}
