package edu.cmu.cs.lti.deiis.controllers

import edu.cmu.cs.lti.deiis.db.Database
import edu.cmu.cs.lti.deiis.db.Record
import edu.cmu.cs.lti.deiis.evaluation.manual.Version
import edu.cmu.cs.lti.deiis.json.Data
import edu.cmu.cs.lti.deiis.json.Question
import groovy.util.logging.Slf4j
import org.lappsgrid.serialization.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

/**
 *
 */
@Slf4j('logger')
@Controller
class Main {

    enum RefType {
        gold('Gold Standard'), baseline('Baseline Comparison');

        String label;
        RefType(String label) {
            this.label = label
        }
    }

    @Autowired
    Database database

    Data reference
    Data candidate
    String version

    public Main() {
        URL url = this.class.getResource("/data/training5b.json")
        if (url == null) {
            logger.error("Unable to find the data file.")
        }
        else {
            reference = Serializer.parse(url.text, Data)
        }
    }

    @GetMapping(value="/", produces = "text/html")
    String root(Model model) {
        return "main"
    }

    @GetMapping(value="/gold/{type}", produces = "text/html")
    String gold(@PathVariable('type') String type, Model model) {
        logger.info("/gold/{}", type)
        List options = [
                [value: 1, label:'1 - Bad'],
                [value: 2, label:'2'],
                [value: 3, label:'3', selected: true],
                [value: 4, label:'4'],
                [value: 5, label:'5 - Good']
        ]
        updateModel(model, type, RefType.gold, options)
        return "rate"
    }

    @GetMapping(value="/baseline/{type}", produces = "text/html")
    String baseline(@PathVariable('type') String type, Model model) {
        logger.info("/baseline/{}", type)
        List options = [
                [ value:-1, label: 'The reference answer is better'],
                [ value:0, selected:true, label: 'They are the same'],
                [ value:1, label: 'The candidate answer is better']
        ]
        updateModel(model, type, RefType.baseline, options)
        return "rate"
    }

    @PostMapping(value="/save")
    String save(@RequestBody String body, Model model) {
        logger.info("Data was posted.")
        Authentication auth = SecurityContextHolder.context.authentication
        User user = auth.principal
        logger.debug("User: {}", user.username)
        model.addAttribute('user', user.username)
        Map data
        try {
            data = parse(body)
            data.each { k,v ->
                logger.debug("{} = {}", k, v)
//                model.addAttribute(k, v)
            }
            data.evaluator = user.username
            data.version = version
            database.save(new Record(data))
        }
        catch (Exception e) {
            logger.error("Unable to process the request body.")
        }
        model.addAttribute('data', data)
        model.addAttribute('link', "/${data.reference}/${data.type}")
        return "saved"
    }

    @PostMapping("/upload")
    String upload(@RequestParam('file') MultipartFile file,
                  @RequestParam('ref') String ref,
                  @RequestParam('type') String type,
                  Model model) {
        if (file.isEmpty()) {
            logger.warn("No file was provided")
            model.addAttribute('message', 'Please select a file to upload.')
            return 'status'
        }

        try {
            logger.debug("Parsing JSON")
            String json = new String(file.getBytes())
            candidate = Serializer.parse(json, Data)
            logger.trace("JSON has been parsed.")
            model.addAttribute('message', 'Uploaded ' + file.getOriginalFilename())
            model.addAttribute('href', "/${ref}/${type}")
            model.addAttribute('label', 'Continue')
            version = file.getOriginalFilename()
            logger.info("File received. Sending to the status page.")
            if (ref == 'baseline') {
                return baseline(type, model)
            }
            return gold(type, model)
//            return 'status'
        }
        catch (Exception e) {
            logger.error("Unable to upload file", e)
            while (e.cause) {
                logger.error(e.message)
                e = e.cause
            }
            model.addAttribute('message', e.message)
        }
        return 'status'
    }

    @GetMapping("/status")
    String status(Model model) {
        return 'status'
    }

    @GetMapping("/list")
    String list(Model model) {
        List<Map> data = []
        try
        {
            data = database.findAll()
            String message = "There are ${data.size()} records in the database."
            logger.info(message)
//            model.addAttribute('message', message)
        }
        catch (Throwable e) {
            logger.error("Unable to query the database.", e)
            model.addAttribute('message', e.message)
            model.addAttribute('heading', 'Error')
            model.addAttribute('href', '/baseline/summary')
            model.addAttribute('label', 'Back')
            return 'status'
        }
//        model.addAttribute('data', data)
        Map record = [
                id: '1',
                evaluator:'suderman',
                question:'1',
                reference:'gold',
                type:'summary',
                version:'submission',
                readability:'5',
                repetition:'5'
        ]
        model.addAttribute('data', data)
        return "test"
    }

    @GetMapping(value = "/raw", produces = 'text/plain')
    @ResponseBody String raw() {
        List<Record> data = database.findAll()
        logger.info("There are {} records in the database.", data.size())
        StringBuilder buffer = new StringBuilder()
        data.each { record ->
            buffer.append(record.toString())
            buffer.append('\n')
        }
        return buffer.toString()
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
        logger.debug("Getting question {}", id)
        Map response = [:]
        if (id == null || id == 'undefined') {
            String undefined = '<p>Undefined</p>'
            response.exact = undefined
            response.ideal = undefined
            response.candidateExact = undefined
            response.candidateIdeal = undefined
            return response
        }
        Question ref = reference.findById(id)
        Question cand = candidate.findById(id)
        response.candidate = 'This is the candidate answer.  Hopefully this was retreived from the BioASQ service directly.'
        if (ref) {
            logger.trace("Found the question.")
            response.exact = toHtml(ref.exact)
            response.ideal = toHtml(ref.ideal)
            response.candidateExact = toHtml(cand.ideal)
            response.candidateIdeal = toHtml(cand.exact)
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
            logger.trace("Null object passed to the toHtml() method.")
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

    void updateModel(Model model, String type, RefType ref, List options) {
        List candidates = candidate.findByType(type)
        List questions = []
        candidates.each { Question q ->
            questions << reference.findById(q.id)
        }
        logger.info("There are {} questions", questions.size())
        model.addAttribute('questions', questions)
        model.addAttribute('candidates', candidates)
        model.addAttribute('heading', ref.label)
        model.addAttribute('options', options)
        model.addAttribute('type', type)
        model.addAttribute('reference', ref.toString())
    }

    Map parse(String input) {
        logger.debug("Parsing input {}", input)
        Map result = [:]
        input.split('&').each { arg ->
            def parts = arg.split('=')
            result[parts[0]] = parts[1]
        }
        return result
    }
}
