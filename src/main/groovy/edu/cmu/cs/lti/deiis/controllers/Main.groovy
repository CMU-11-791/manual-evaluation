package edu.cmu.cs.lti.deiis.controllers

import edu.cmu.cs.lti.deiis.evaluation.manual.Version
import edu.cmu.cs.lti.deiis.json.Data
import edu.cmu.cs.lti.deiis.json.Question
import groovy.util.logging.Slf4j
import org.lappsgrid.serialization.Serializer
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 */
@Slf4j('logger')
@Controller
class Main {

    Data data
    List<Question> questions

    public Main() {
        URL url = this.class.getResource("/data/training5b.json")
        if (url == null) {
            logger.error("Unable to find the data file.")
        }
        else {
            data = Serializer.parse(url.text, Data)
        }
    }

    @GetMapping(value="/", produces = "text/html")
    String root(Model model) {
        return "main"
    }

    @GetMapping(value="/gold/{type}", produces = "text/html")
    String gold(@PathVariable('type') String type, Model model) {
        logger.info("/gold/{}", type)
        List questions = data.findByType(type)
        logger.info("There are {} questions", questions.size())
        model.addAttribute('questions', questions)
        return "gold"
    }

    @GetMapping(value="/baseline/{type}", produces = "text/html")
    String baseline(@PathVariable('type') String type, Model model) {
        logger.info("/baseline/{}", type)
        List questions = data.findByType(type)
        logger.info("There are {} questions", questions.size())
        model.addAttribute('questions', questions)
        return "baseline"
    }

    @PostMapping(value="/update", produces = 'text/html')
    String post(Model model) {
        model.addAttribute('loggedin', true)
        logger.info("Data was posted.")
        model.asMap().each { k,v ->
            logger.info("$k = $v")
        }
        return "post"
    }

    @GetMapping("/login")
    String login(@RequestParam(value = 'error', required = false) String error,
                 @RequestParam(value='logout', required = false) String logout,
                 Model model) {
        if (error != null) model.addAttribute('invalid', 'error')
        if (logout != null) model.addAttribute('logout', 'logout')

        return "login"
    }

    @GetMapping('/about')
    String about(Model model) {
        model.addAttribute('version', Version.version)
        return 'about'
    }

    @GetMapping(value = '/question', produces = 'application/json')
    @ResponseBody Map question(@RequestParam('id') String id) {
        logger.info("Getting question {}", id)
        Question q = data.findById(id)
        Map response = [:]
        response.candidate = 'This is the candidate answer.  Hopefully this was retreived from the BioASQ service directly.'
        if (q) {
            logger.trace("Found the question.")
            response.exact = toHtml(q.exact)
            response.ideal = toHtml(q.ideal)
            response.candidate = toHtml(q.ideal)
        }
        else {
            response.exact = "Unable to fetch question data."
            response.ideal = 'Unable to fetch question data.'
        }
        return response
    }

    private String toHtml(Object object) {
        StringBuilder buffer = new StringBuilder()
        if (object == null) {
            logger.debug("Null object passed to the toHtml() method.")
            return '<p>None specified.</p>'
        }
        else if (object instanceof List) {
            logger.trace("Object is a list")
            List list = (List) object
            buffer.append("<ul>")
            list.each { item ->
                buffer.append("<li>")
                buffer.append(item.toString())
                buffer.append("</li>")
            }
            buffer.append("</ul>")
        }
        else {
            logger.trace("Object is a {}", object.class.name)
            buffer.append("<p>")
            buffer.append(object.toString())
            buffer.append("</p>")
        }
        String string = buffer.toString()
        logger.trace(string)
        return string
    }
}
