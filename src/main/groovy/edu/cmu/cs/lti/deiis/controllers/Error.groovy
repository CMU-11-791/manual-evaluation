package edu.cmu.cs.lti.deiis.controllers

import groovy.util.logging.Slf4j
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.security.access.method.P
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 */
@Slf4j('logger')
@Controller
class Error implements ErrorController {

    @GetMapping("/403")
    String error403() {
        logger.info("/403")
        return "error/403"
    }

    @GetMapping("/error")
    String error() {
        logger.info("Caught an internal error.")
        return "error/500"
    }

    @Override
    String getErrorPath() {
        logger.debug("Returning the error path")
        return "/error"
    }
}
