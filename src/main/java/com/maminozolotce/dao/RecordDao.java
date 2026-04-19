package com.maminozolotce.dao;

import com.maminozolotce.entity.Record;
import com.maminozolotce.entity.RecordStatus;
import com.maminozolotce.service.RecordService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RecordDao {

    private final EntityManagerFactory entityManagerFactory;
    @Autowired
    public RecordDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }

    public List<Record> findAllRecords(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT r FROM  Record r");
            List<Record> records = query.getResultList();

            entityManager.getTransaction().commit();
            return records;
        }catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Collections.emptyList();
        }finally {
            entityManager.close();
        }
    }


    public void saveRecord(Record record){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(record);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }

    public void deleteRecord(int id){
         EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("delete from Record where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }

    public void makeRecordDone(int id, RecordStatus newStatus){
       EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("update Record set status = :status where id = :id");
            query.setParameter("status", newStatus);
            query.setParameter("id", id);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }
}
