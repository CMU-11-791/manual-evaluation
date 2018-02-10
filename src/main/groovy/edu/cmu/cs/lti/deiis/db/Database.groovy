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
    List<Record> findByDataset(String dataset)

    List<Record> findByEvaluatorAndDataset(String name, String dataset)
    List<Record> findByEvaluatorAndReferenceAndType(String name, String reference, String type)
    List<Record> findByReference(String reference)
    List<Record> findByType(String type)
    List<Record> findByReferenceAndType(String reference, String type)

    void save(Record record)

    void delete(Record record)
    void deleteAll()
    void deleteById(Long id)
    void deleteByEvaluator(String name)
    void deleteByDataset(String dataset)
    void deleteByReference(String reference)
    void deleteByReferenceAndType(String reference, String type)
}