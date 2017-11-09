package edu.cmu.cs.lti.deiis.json

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 */
class Snippet {
    @JsonProperty("offsetInBeginSection")
    int begin
    @JsonProperty('offsetInEndSection')
    int end
    String text
    String beginSection
    String endSection
    String document
}
