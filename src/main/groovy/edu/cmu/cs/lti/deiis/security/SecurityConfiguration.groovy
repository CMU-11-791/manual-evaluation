package edu.cmu.cs.lti.deiis.security

import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 *
 */
@Slf4j('logger')
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AccessDenied accessDeniedHandler

    @Override
    void configure(HttpSecurity http) {
        logger.info("Configuring security.")

        // Anyone may access these
        String[] publicUrls = [ "/about", "/question", '/css/**', '/js/**', '/images/**', '/show', '/list', '/raw' ]

        // Users must be logged in.
        String[] protectedUrls = [
                '/', '/gold/**', '/baseline/**', '/goto/**',
                '/select', '/save', '/upload', '/update',
                '/status', '/list', '/evaluated', '/remaining'
        ]

        // Administrators only.
        String[] privateUrls = [ "/admin/**" ]

        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(publicUrls).permitAll()
//                .antMatchers(protectedUrls).permitAll()
//                .antMatchers(privateUrls).permitAll()
                .antMatchers(protectedUrls).hasRole('USER')
                .antMatchers(privateUrls).hasRole('ADMIN')
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder builder) {
        logger.info("Configuring the authentication manager.")
        def auth = builder.inMemoryAuthentication()

        ['ehn', 'khyathi', 'suderman'].each {
            auth.withUser(it)
                .password('cmu11791')
                .roles('USER', 'ADMIN')
        }
        auth.withUser('u')
                .password('p')
                .roles('ADMIN', "USER")
        ['yuyanz1', 'shengjil', 'yutong1', 'zkaden', 'ngekakis', 'kec1'].each {
            logger.debug("Adding user {}", it)
            auth.withUser(it).password('cmu11791').roles('USER')
        }
    }

}
