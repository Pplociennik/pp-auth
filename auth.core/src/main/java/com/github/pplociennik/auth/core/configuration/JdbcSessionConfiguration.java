//package com.github.pplociennik.auth.core.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
//import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * A configuration class for Session Management.
// *
// * @author Created by: Pplociennik at 18.01.2023 23:31
// */
//@Configuration
//@EnableJdbcHttpSession
//public class JdbcSessionConfiguration extends AbstractHttpSessionApplicationInitializer {
//
//    @Bean
//    public PlatformTransactionManager transactionManager( DataSource dataSource ) {
//        return new DataSourceTransactionManager( dataSource );
//    }
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//}
