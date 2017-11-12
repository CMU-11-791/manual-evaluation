package edu.cmu.cs.lti.deiis.json

import com.fasterxml.jackson.databind.ObjectMapper
import edu.cmu.cs.lti.deiis.controllers.Main
import org.junit.Ignore
import org.junit.Test
import org.lappsgrid.serialization.Serializer

/**
 *
 */
class ParsingTest {

    @Test
    void testParse() {
//        URL url = this.class.classLoader.getResource("data/training5b.json")
//        assert null != url
//        ObjectMapper parser = new ObjectMapper()
//        Data data = parser.readValue(url, Data)
        Data data = load("/data/training5b.json")
        assert data != null
        assert data.questions != null
        println data.questions.size()
        Set types = [] as HashSet<String>
        data.questions.each { Question q ->
            types << q.type
        }
        println "Types: ${types.size()}"
        types.each { println it }

        ['summary', 'factoid', 'list', 'yesno'].each { String type ->
            List<Question> questions = data.findByType('summary')
            assert questions != null
            println type + ": " + questions.size()
        }
    }

    @Test
    void parseSubmission() {
//        URL url = this.class.classLoader.getResource("data/submission.json")
//        assert null != url
//        ObjectMapper parser = new ObjectMapper()
//        Data data = parser.readValue(url, Data)
        Data data = load("/data/submission.json")
        assert data != null
        assert data.questions != null
        println data.questions.size()
        Set types = [] as HashSet<String>
        data.questions.each { Question q ->
            types << q.type
        }
        println "Types: ${types.size()}"
        types.each { println it }

        ['summary', 'factoid', 'list', 'yesno'].each { String type ->
            List<Question> questions = data.findByType('summary')
            assert questions != null
            println type + ": " + questions.size()
        }
    }

    @Test
    void checkIdValues() {
        Data baseline = load("/data/training5b.json")
        Data submission = load("/data/submission.json")
        submission.questions.each { Question q1 ->
            Question q2 = baseline.findById(q1.id)
            assert null != q2
        }
    }

    @Test
    void parseFormInput() {
        String body = 'question=56bf3a79ef6e39474100000f&readability=0&redundancy=0&type=summary&reference=baseline'
        Main main = new Main()
        Map map = main.parse(body)
        assert map != null
        assert 5 == map.size()
        map.each { k,v ->
            println "$k = $v"
        }
    }

    Data load(String path) {
        URL url = this.class.getResource(path)
        assert null != url
        return Serializer.parse(url.text, Data)
    }
}
