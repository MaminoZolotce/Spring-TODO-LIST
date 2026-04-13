package com.maminozolotce.config.dao;

import com.maminozolotce.config.entity.Record;
import com.maminozolotce.config.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record("Buy groceries", RecordStatus.ACTIVE),
                    new Record("Do homework", RecordStatus.DONE),
                    new Record("Go to gym", RecordStatus.DONE),
                    new Record("Make charger", RecordStatus.ACTIVE)
            )
    );

    public List<Record> findAllRecords(){
        return new ArrayList<>(records);
    }

    public void saveRecord(Record record){
        records.add(record);
    }

    public void deleteRecord(int id){
        records.removeIf(record -> record.getId() == id);
    }

    public void makeRecordDone(int id){

        records.stream()
                .filter(record -> record.getId()==id)
                .findFirst()
                .ifPresent(record ->{
                    if(record.getStatus() == RecordStatus.ACTIVE){
                        record.setStatus(RecordStatus.DONE);
                    }else{
                        record.setStatus(RecordStatus.ACTIVE);
                    }
                });
    }
}
