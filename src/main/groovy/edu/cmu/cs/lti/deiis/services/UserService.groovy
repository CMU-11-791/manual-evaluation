package edu.cmu.cs.lti.deiis.services

import groovy.util.logging.Slf4j
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

/**
 *
 */
@Slf4j('logger')
@Service
class UserService {

    String getName() {
        Authentication auth = SecurityContextHolder.context.authentication
        User user = auth.principal
        return user.username
    }

    List<String> getRoles() {
        Authentication auth = SecurityContextHolder.context.authentication
        User user = auth.principal

        return user.authorities.inject { it.authority }
    }
}
