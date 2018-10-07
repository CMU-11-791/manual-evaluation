package edu.cmu.cs.lti.deiis.db

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

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
    List<Record> findByEvaluatorAndDatasetAndQuestionAndReferenceAndType(String name, String dataset, String id, String ref, String type)
    void save(Record record)

    @Modifying
    @Query('UPDATE Record r SET r.rating = :rating WHERE r.id = :id')
    void update(@Param('id') long id, @Param('rating') String rating)

    void delete(Record record)
    void deleteAll()
    void deleteById(Long id)
    void deleteByEvaluator(String name)
    void deleteByDataset(String dataset)
    void deleteByReference(String reference)
    void deleteByReferenceAndType(String reference, String type)
}