package edu.cmu.cs.lti.deiis.controllers

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 */
@Slf4j('logger')
@Controller
class Error {
    @GetMapping("/403")
    String error403() {
        logger.info("/403")
        return "error/403"
    }

}
