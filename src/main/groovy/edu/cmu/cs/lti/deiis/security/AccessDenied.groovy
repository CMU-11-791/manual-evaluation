package edu.cmu.cs.lti.deiis.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 */
@Component
class AccessDenied implements AccessDeniedHandler {
    static Logger logger = LoggerFactory.getLogger(AccessDenied)
    @Override
    void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.context
        if (auth) {
            logger.info("User {} attempted to access the protected URL {}", auth.name, request.requestURI)
        }
        response.sendRedirect(request.contextPath, '/403')
    }
}
