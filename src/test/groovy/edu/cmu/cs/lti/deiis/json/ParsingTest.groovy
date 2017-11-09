package edu.cmu.cs.lti.deiis.json

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

/**
 *
 */
class ParsingTest {

    @Test
    void testParse() {
        URL url = this.class.classLoader.getResource("data/training5b.json")
        assert null != url
        ObjectMapper parser = new ObjectMapper()
        Data data = parser.readValue(url, Data)
        assert data != null
        assert data.questions != null
        println data.questions.size()
        Set types = [] as HashSet<String>
        data.questions.each { Question q ->
            types << q.type
        }
        println "Types: ${types.size()}"
        types.each { println it }

        List<Question> summary = data.findByType('summary')
        assert summary != null
        println "Summaries: " + summary.size()
    }
}
