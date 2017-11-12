package edu.cmu.cs.lti.deiis.security

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
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    static Logger logger = LoggerFactory.getLogger(SecurityConfiguration)

    @Autowired
    AccessDenied accessDeniedHandler

    @Override
    void configure(HttpSecurity http) {
        logger.info("Configuring security.")
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/about", "/question", '/css/**', '/js/**', '/images/**', '/show', '/raw').permitAll()
                .antMatchers("/", "/gold/**", "/baseline/**", "/update", "/save").hasRole('USER')
//                .antMatchers("/", "/gold/**", "/baseline/**", "/update").permitAll()
                .antMatchers("/admin").hasRole('ADMIN')
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
            logger.info("Adding user {}", it)
            auth.withUser(it).password('cmu11791').roles('USER')
        }
    }

}
