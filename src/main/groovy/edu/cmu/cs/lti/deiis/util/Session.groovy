package edu.cmu.cs.lti.deiis.util

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

/**
 *
 */
@Component
@Scope(value = 'session', proxyMode = ScopedProxyMode.TARGET_CLASS)
class Session {
    String reference
    String type
    String dataset
    String next
    List<String> questions
    int index

    void updateIndex(String id) {
        for (int i=0; i< questions.size(); ++i) {
            if (questions[i] == id) {
                index = i
                break
            }
        }
    }
}
