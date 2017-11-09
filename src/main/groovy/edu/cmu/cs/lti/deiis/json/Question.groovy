package edu.cmu.cs.lti.deiis.json

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 */
class Question {
    String id
    String type
    String body
    List<String> concepts
    List<Triple> triples
    List<String> documents
    List<Snippet> snippets
    @JsonProperty('exact_answer')
    Object exact
    @JsonProperty('ideal_answer')
    Object ideal
}
