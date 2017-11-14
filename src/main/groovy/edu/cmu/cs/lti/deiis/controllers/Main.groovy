package edu.cmu.cs.lti.deiis.controllers

import edu.cmu.cs.lti.deiis.db.Database
import edu.cmu.cs.lti.deiis.db.Record
import edu.cmu.cs.lti.deiis.evaluation.manual.Version
import edu.cmu.cs.lti.deiis.json.Data
import edu.cmu.cs.lti.deiis.json.Question
import edu.cmu.cs.lti.deiis.services.RepositoryService
import edu.cmu.cs.lti.deiis.services.UserService
import edu.cmu.cs.lti.deiis.util.Session
import groovy.util.logging.Slf4j
import org.lappsgrid.serialization.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
    Session session

    @Autowired
    Database database

    @Autowired
    RepositoryService repository

    @Autowired
    UserService user

    Data gold
    Data baseline

    Data reference

//    Data candidate
//    String dataset

    public Main() {
        URL url = this.class.getResource("/data/training5b.json")
        if (url == null) {
            logger.error("Unable to find the data file.")
        }
        else {
            gold = Serializer.parse(url.text, Data)
            baseline = gold
            reference = gold
        }
    }

    @GetMapping(value="/", produces = "text/html")
    String root(Model model) {
        model.addAttribute('filelist', repository.list())
        return "main"
    }

    /*
     * Called from the admin/repostory page to select a new dataset.
     */
    @GetMapping('/select')
    String select(@RequestParam String id, Model model) {
        logger.info('GET /select, id = {}', id)
        session.dataset = id

        //TODO Ideally the index will be set to the next question that needs evaluating.
        session.index = 0

        String ref = session.reference ?: 'gold'
        String type = session.type ?: 'summary'
        logger.debug('Reference {}', ref)
        logger.debug('Type {}', type)
        logger.debug('Data {}', id)
        return "redirect:/$ref/$type"
    }

    /*
     * Called from the main page (main.tpl) to initialize an evaluation session.
     */
    @PostMapping('/select')
    String selectPost(@RequestBody String body, Model model) {
        logger.info('POST /select')
        Map params = parse(body)
        String ref = params.ref
        String type = params.type
        session.dataset = params.file
        session.index = 0
        return "redirect:/$ref/$type"
    }

    @GetMapping('/goto/{name}')
    String goTo(@PathVariable String name, Model model) {
        String reference = session.reference
        String type = session.type
        switch (name) {
            case ['gold','baseline']:
                reference = name
                 break
            case ['summary','factoid','list','yesno']:
                type = name
            default:
                model.addAttribute('error', "Invalid target: $name")
                break
        }
        return "redirect:/$reference/$type"
    }

    @GetMapping(value="/gold/{type}", produces = "text/html")
    String gold(@PathVariable('type') String type, @RequestParam(value = 'id', required = false) String id, Model model) {
        logger.info("/gold/{}", type)

        reference = gold
        List options = [
                [value: 1, label:'1 - Bad'],
                [value: 2, label:'2'],
                [value: 3, label:'3', selected: true],
                [value: 4, label:'4'],
                [value: 5, label:'5 - Good']
        ]
        updateModel(model, RefType.gold, type, id, options)
        return "rate"
    }

    @GetMapping(value="/baseline/{type}", produces = "text/html")
    String baseline(@PathVariable('type') String type, @RequestParam(value='id', required = false) String id, Model model) {
        logger.info("/baseline/{}", type)
        reference = baseline
        List options = [
                [ value:-1, label: 'The reference answer is better'],
                [ value:0, selected:true, label: 'They are the same'],
                [ value:1, label: 'The candidate answer is better']
        ]
        updateModel(model, RefType.baseline, type, id, options)
        return "rate"
    }

    @PostMapping(value="/save")
    String save(@RequestBody String body, Model model) {
        logger.info("/save")
        String username = user.getName()
        model.addAttribute('user', username)
        Map data
        try {
            data = parse(body)
            data.each { k,v ->
                logger.debug("{} = {}", k, v)
            }
            data.evaluator = username
            data.dataset = session.dataset
            database.save(new Record(data))
            logger.info("{} evaluated {}", username, data.question)
        }
        catch (Exception e) {
            logger.error("Unable to process the request body.")
        }
        session.index = session.index + 1
        logger.debug("Session index is now {}", session.index)
        String reference = session.reference
        String type = session.type
        return "redirect:/$reference/$type"
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
            if (repository.save(file.originalFilename, json)) {
                model.addAttribute('message', 'Uploaded ' + file.getOriginalFilename())
            }
            else {
                model.addAttribute('error', 'Unable to save the file to the repository.')
            }
            logger.trace("JSON has been parsed.")
            model.addAttribute('href', "/${ref}/${type}")
            model.addAttribute('label', 'Continue')
            session.dataset = file.getOriginalFilename()
            logger.info("{} uploaded dataset {}", user.name, file.originalFilename)
            if (ref == 'baseline') {
                return baseline(type, model)
            }
            return gold(type, model)
        }
        catch (Exception e) {
            logger.error("Unable to upload file", e)
            while (e.cause) {
                logger.error(e.message)
                e = e.cause
            }
            model.addAttribute('error', e.message)
        }
        return 'status'
    }

    @GetMapping("/status")
    String status(Model model) {
        return 'status'
    }

    @GetMapping("/list")
    String list(Model model) {
        logger.info('/list')
        String ref = session.reference
        String type = session.type
        String back = "/$ref/$type"
        List<Record> data
        try
        {
            data = database.findAll()
            String message = "There are ${data.size()} records in the database."
            logger.info(message)
        }
        catch (Throwable e) {
            logger.error("Unable to query the database.", e)
            model.addAttribute('message', e.message)
            model.addAttribute('heading', 'Error')
            model.addAttribute('href', back)
            model.addAttribute('label', 'Back')
            return 'status'
        }
        model.addAttribute('data', data)
        model.addAttribute('link', back)
        return "list"
    }

    @GetMapping('/evaluated')
    String evaluated(Model model) {
        String ref = session.reference
        String type = session.type
        List<Record> records = database.findByEvaluatorAndReferenceAndType(user.name, ref, type)
        model.addAttribute('link', "/$ref/$type")
        model.addAttribute('data', records)
        return "list"
    }

    @GetMapping('/remaining')
    String remaining(Model model) {
        String ref = session.reference
        String type = session.type
        List<String> unmarked = []
        List<Record> records = database.findByEvaluatorAndReferenceAndType(user.name, ref, type)
        Data data = repository.load(session.dataset)
        session.questions.each { id ->
            Record record = records.find { it.question == id }
            if (record == null) {
                unmarked << data.findById(id)
            }
        }
        model.addAttribute('data', unmarked)
        model.addAttribute('link', "/$ref/$type")
        return 'remaining'
    }


    @GetMapping(value = "/raw", produces = 'text/plain')
    @ResponseBody String raw() {
        logger.info('/raw')
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
        session.index = 0
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
        Data candidates = repository.load(session.dataset)
        Question ref = reference.findById(id)
        Question cand = candidates.findById(id)
        session.updateIndex(id)
        response.candidate = 'This is the candidate answer.  Hopefully this was retreived from the BioASQ service directly.'
        if (ref) {
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

    void updateSession(RefType reference, String type) {
        if (session.reference == reference.toString() && session.type == type) {
            logger.debug("Maintaining session state")
            return
        }
        logger.debug("Updating the session to {}/{}", reference.toString(), type)
        session.reference = reference.toString()
        session.type = type
        session.index = 0
        session.questions = []
        Data candidates = repository.load(session.dataset)
        List<Question> questions = candidates.findByType(type)
        questions.each { q ->
            session.questions << q.id
        }
    }

    void updateModel(Model model, RefType ref, String type, String id, List options) {
        logger.debug("Updating model")
        updateSession(ref, type)
        List<Question> candidates = []
        List<Question> questions = []
        Data data = repository.load(session.dataset)
        session.questions.each { qid ->
            candidates.add(data.findById(qid))
            questions.add(reference.findById(qid))
        }
        int index = 0
        if (id != null) {
            index = questions.findIndexOf { it.id == id }
            session.index = index
        }
        else {
            if (session.index < session.questions.size()) {
                index = session.index
            }
            else {
                session.index = 0
            }
        }
        String selected = session.questions[index]

        logger.debug("There are {} questions", questions.size())
        logger.debug("Session index: {}", session.index)
        logger.debug("Actual index: {}", index)
        logger.debug("Selected ID: {}", selected)
        model.addAttribute('questions', questions)
        model.addAttribute('candidates', candidates)
        model.addAttribute('heading', ref.label)
        model.addAttribute('options', options)
        model.addAttribute('type', type)
        model.addAttribute('reference', ref.toString())
        model.addAttribute('selected', selected)
        model.addAttribute('index', session.index)
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
