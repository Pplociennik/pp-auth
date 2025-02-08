/*
 * MIT License
 *
 * Copyright (c) 2021 Przemysław Płóciennik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.pplociennik.auth.core.runner;

import com.github.pplociennik.auth.core.configuration.DefaultStaticSecurityConfiguration;
import com.github.pplociennik.auth.core.configuration.lang.SystemLangTranslateConfiguration;
import com.github.pplociennik.commons.audit.DefaultAuditor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * A test configuration for Auth runner.
 *
 * @author Created by: Pplociennik at 03.02.2022 19:07
 */
@Configuration
@EnableJpaAuditing( auditorAwareRef = "auditorProvider" )
@EnableDiscoveryClient
@Import( { DefaultStaticSecurityConfiguration.class, SystemLangTranslateConfiguration.class } ) // Using default configuration for testing
class RunnerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi
                .builder()
                .group( "pp.auth-all" )
                .pathsToMatch( "/*" )
                .build();
    }

    @Bean
    AuditorAware< String > auditorProvider() {
        return new DefaultAuditor();
    }
}
