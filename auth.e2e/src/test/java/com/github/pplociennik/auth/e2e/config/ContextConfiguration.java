package com.github.pplociennik.auth.e2e.config;

import com.github.pplociennik.auth.core.configuration.DefaultStaticSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * A context configuration for EmailFacadeTest.
 *
 * @author Created by: Pplociennik at 14.06.2022 18:09
 */
@Configuration
@Import( DefaultStaticSecurityConfiguration.class )
class ContextConfiguration {
}
