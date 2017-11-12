package edu.cmu.cs.lti.deiis.controllers

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 */
@Slf4j('logger')
@Controller
class Admin {

    @GetMapping('/admin')
    String admin() {
        logger.info('/admin')
        return "admin/admin"
    }

    @GetMapping('/admin/test')
    String test() {
        return 'admin/test'
    }

    @GetMapping('/admin/form')
    String form() {
        return 'admin/form'
    }
}
