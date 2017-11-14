package edu.cmu.cs.lti.deiis.json

import com.fasterxml.jackson.annotation.JsonIgnore
import jp.go.nict.langrid.repackaged.net.arnx.jsonic.JSON

/**
 *
 */
class Data {
    /*
     * The index maps question ids to the Question objects. The
     * index should never be serialized to JSON
     */
    @JsonIgnore
    Map<String,Question> index = [:]

    List<Question> questions

    List<Question> findByType(String type) {
        return questions.findAll { it.type == type }
    }

    Question findById(String id) {
        return index[id]
    }

//    @JsonIgnore
//    String getNext(String id) {
//        Question q = findById(id)
//        if (q == null) {
//            return null
//        }
//        return getNext(q)
//    }
//
//    @JsonIgnore
//    String getNext(Question question) {
//        for (int i = 0; i < questions.size(); ++i) {
//            if (question.id == questions[i].id) {
//                if (i >= questions.size()) {
//                    return null
//                }
//                return questions[i+1].id
//            }
//        }
//        return null
//    }
//
//    @JsonIgnore
//    String getSecond() {
//        if (questions.size() > 1) {
//            return questions[1].id
//        }
//        return null
//    }

    /*
     * The set method will be called by Jackson after the question list
     * has been parsed. We will use this opportunity to create the
     * question index.
     */
    void setQuestions(List<Question> questions) {
        this.questions = questions
        index.clear()
        this.questions.each { Question q ->
            index.put(q.id, q)
        }
    }
}
