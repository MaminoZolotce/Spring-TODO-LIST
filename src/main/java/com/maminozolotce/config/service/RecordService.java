package com.maminozolotce.config.service;

import com.maminozolotce.config.dao.RecordDao;
import com.maminozolotce.config.entity.Record;
import com.maminozolotce.config.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    private final RecordDao recordDao;


    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public List<Record> findAllRecords(){
        return recordDao.findAllRecords();
    }

    public void saveRecord(String title){
         if(title != null && !title.isBlank()) {
        recordDao.saveRecord(new Record(title.trim()));
        }
    }

    public void deleteRecord(int id){
        recordDao.deleteRecord(id);
    }

    public void makeRecordDone(int id){
        recordDao.makeRecordDone(id);
    }
}
