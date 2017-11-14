package edu.cmu.cs.lti.deiis.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 *
 */
@Entity
class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String evaluator
    String question
    String reference
    String type
    String dataset
    int readability
    int repetition
    long timestamp

    Record() {
        timestamp = System.currentTimeMillis()
    }

    Record(Map data) {
        this()
        this.evaluator = data.evaluator
        this.question = data.question
        this.reference = data.reference
        this.type = data.type
        this.dataset = data.dataset
        this.readability = data.readability as int
        this.repetition = data.repetition as int
    }

    @Override
    String toString() {
        return String.format("%s,%s,%s,%s,%s,%d,%d,%d", evaluator, question, reference, type, dataset, repetition, readability, timestamp)
    }
}
