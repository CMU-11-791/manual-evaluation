package edu.cmu.cs.lti.deiis.json

import edu.cmu.cs.lti.deiis.controllers.Main
import org.junit.Ignore
import org.junit.Test
import org.lappsgrid.serialization.Serializer

/**
 *
 */
class ParsingTest {

    static final List CANDIDATES = ['original', 'ordered', 'fusion', 'ordered_fusion']
    static final List ALL_FILES = ['bioasq4b_gold' ] + CANDIDATES
//    static final List ALL_FILES = ['bioasq4b_gold', 'original', 'ordered', 'fusion', 'ordered_fusion' ]

    @Test
    void testParse() {
//        URL url = this.class.classLoader.getResource("data/training5b.json")
//        assert null != url
//        ObjectMapper parser = new ObjectMapper()
//        Data data = parser.readValue(url, Data)
        CANDIDATES.each { name ->
            Data data = load("/data/${name}.json")
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
    }

    @Test
    void parseAll() {
        Data data = load("/data/bioasq4b_gold.json")
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
        Data baseline = load("/data/bioasq4b_gold.json")
//        Data baseline = load("/data/original.json")
        boolean passed = true
        CANDIDATES.each { name ->
            println "Checking $name"
            Data submission = load("/data/${name}.json")
            submission.questions.each { Question q1 ->
                Question q2 = baseline.findById(q1.id)
                if (q2 == null) {
                    println "Submission: ${q1.id}"
                    passed = false
                }
            }
            baseline.questions.each { Question q1 ->
                Question q2 = submission.findById(q1.id)
                if (q2 == null) {
                    println "Baseline: ${q1.id}"
                    passed = false
                }
            }

        }
        assert passed
    }

    @Test
    void checkForDuplicateID() {
        boolean passed = true
        CANDIDATES.each { String name ->
            println "Checking $name"
            Set seen = new HashSet()
            Data data = load("/data/${name}.json")
            data.questions.each { q ->
                if (seen.contains(q.id)) {
                    println "Duplicate ID: ${q.id}"
                    passed = false
                }
            }
        }
        assert passed
    }

    @Test
    void whichQuestions() {
        List present = []
        List missing = []
        Data gold = load("/data/bioasq4b_gold.json")
        Data original = load("/data/original.json")
        gold.questions.each { Question q1 ->
            Question q2 = original.findById(q1.id)
            if (q2) {
                present << q1.id

            }
            else {
                missing << q1.id
            }
        }
        println "Present: ${present.size()}"
        println "Missing: ${missing.size()}"
        missing.each { println it }
    }

    @Test
    void fileSizes() {
        ['bioasq4b_gold', 'original', 'ordered', 'fusion', 'ordered_fusion'].each { name ->
            Data data = load("/data/${name}.json")
            println "$name\t${data.questions.size()}"
            assert 100 == data.questions.size()
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
