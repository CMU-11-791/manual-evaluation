package edu.cmu.cs.lti.deiis.controllers

import edu.cmu.cs.lti.deiis.util.Session
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.security.access.method.P
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 */
@Slf4j('logger')
@Controller
class Error implements ErrorController {

    @Autowired
    Session session

    @GetMapping("/403")
    String error403(Model model) {
        logger.info("/403")
        model.addAttribute('link', link())
        return "error/403"
    }

    @GetMapping("/error")
    String error(Model model) {
        logger.warn("Caught an internal error.")
        model.addAttribute('link', link())
        return "error/500"
    }

    @Override
    String getErrorPath() {
        logger.trace("Returning the error path")
        return "/error"
    }

    String link() {
        return "/${session.reference}/${session.type}"
    }
}
