package edu.cmu.cs.lti.deiis.controllers

import edu.cmu.cs.lti.deiis.db.Database
import edu.cmu.cs.lti.deiis.db.Record
import edu.cmu.cs.lti.deiis.json.Data
import edu.cmu.cs.lti.deiis.json.Question
import edu.cmu.cs.lti.deiis.services.RepositoryService
import edu.cmu.cs.lti.deiis.services.UserService
import edu.cmu.cs.lti.deiis.util.Session
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

import java.security.Principal

/**
 *
 */
@Slf4j('logger')
@Controller
class Admin {

    @Autowired
    RepositoryService repository

    @Autowired
    UserService user

    @Autowired
    Session session

//    @Autowired
//    Database database

    @GetMapping('/admin/repository')
    String repo(Model model) {
        model.addAttribute('filelist', repository.list())
        return 'admin/repo'
    }

    @GetMapping('/admin/session')
    String session(Model model) {
        model.addAttribute('username', user.name)
        model.addAttribute('roles', user.roles)
        model.addAttribute('reference', session.reference)
        model.addAttribute('type', session.type)
        model.addAttribute('index', session.index)
        model.addAttribute('dataset', session.dataset)
        model.addAttribute('path', repository.path)
        Data data = repository.load(session.dataset)
        List list = []
        session.questions.each { id ->
            list << data.findById(id)
        }
        model.addAttribute('questions', list)
        return 'admin/session'
    }

    @GetMapping('/admin/radios')
    String radios(Model model) {
        return 'radios'
    }

    @GetMapping('/admin/delete')
    String delete(@RequestParam('id') String id, Model model) {
        logger.info("Attempting to delete.")
        String status = repository.delete(id)
        update(model, status)
        return 'admin/repo'
    }

    @PostMapping(value='/admin/upload')
    String upload(@RequestParam('file') MultipartFile file, Model model) {
        logger.info("")
        String content = new String(file.bytes)
        String name = file.originalFilename
        String message
        if (repository.save(name, content)) {
            message = "Received $name"
            logger.info(message)
        }
        else {
            message = "Unable to save $name to the repository"
            logger.error("Unable to save {} to the repository", name)
        }

        update(model,message)
        return 'admin/repo'
    }

    void update(Model model, String message) {
        model.addAttribute('message', message)
        model.addAttribute('filelist', repository.list())
    }
}
