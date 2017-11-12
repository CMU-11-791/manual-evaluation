package edu.cmu.cs.lti.deiis.db

import org.springframework.data.repository.Repository

/**
 *
 */
interface Database extends Repository<Record,Long> {

    List<Record> findAll()
    Record findById(Long id)
    List<Record> findByEvaluator(String name)
    List<Record> findByQuestion(String id)
    List<Record> findByVersion(String version)

    List<Record> findByEvaluatorAndQuestionAndVersion(String name, String question, String version)
    List<Record> findByReference(String reference)
    List<Record> findByType(String type)
    List<Record> findByReferenceAndType(String reference, String type)

    void save(Record record)
    void delete(Record record)
    void deleteAll()
    void deleteById(Long id)

}