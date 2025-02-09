package com.github.pplociennik.auth.core.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * A configuration class for Eureka client.
 *
 * @author Created by: Pplociennik at 08.02.2025 21:43
 */
@Configuration
class EurekaClientConfig {

    public static final String EUREKA_CLIENT_SERVICE_URL_DEFAULT_ZONE_PROPERTY = "eureka.client.serviceUrl.defaultZone";
    private final Environment environment;

    @Autowired
    public EurekaClientConfig( Environment environment ) {
        this.environment = environment;
    }

    @Bean
    @Profile( "!dev" )
    public EurekaClientConfigBean eurekaClientConfigBean() {
        EurekaClientConfigBean config = new EurekaClientConfigBean();
        config.getServiceUrl().put( "defaultZone", environment.getProperty( EUREKA_CLIENT_SERVICE_URL_DEFAULT_ZONE_PROPERTY ) );
        return config;
    }
}
