package com.maminozolotce.dao;

import com.maminozolotce.entity.Record;
import com.maminozolotce.entity.RecordStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class RecordDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Record> findAllRecords() {
    Query query = entityManager.createQuery(
        "SELECT r FROM Record r ORDER BY " +
        "CASE WHEN r.status = :active THEN 0 ELSE 1 END, r.id ASC"
    );
    query.setParameter("active", RecordStatus.ACTIVE);
    return query.getResultList();
}


    public void saveRecord(Record record) {
        entityManager.persist(record);

    }

    public void deleteRecord(int id) {
        Query query = entityManager.createQuery("delete from Record where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void makeRecordDone(int id, RecordStatus newStatus) {
        Query query = entityManager.createQuery("update Record set status = :status where id = :id");
        query.setParameter("status", newStatus);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
