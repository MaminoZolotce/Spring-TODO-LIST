package com.maminozolotce.service;

import com.maminozolotce.dao.RecordDao;
import com.maminozolotce.entity.DTO.RecordContainerDto;
import com.maminozolotce.entity.Record;
import com.maminozolotce.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordDao recordDao;


    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordContainerDto findAllRecords(String filterMode){
        List<Record> records = recordDao.findAllRecords();
        int numberOfActiveRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.ACTIVE)
                .count();
        int numberOfDoneRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.DONE)
                .count();
        if(filterMode == null || filterMode.isBlank()){
            return new RecordContainerDto(records , numberOfDoneRecords , numberOfDoneRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        if(allowedFilterModes.contains(filterModeInUpperCase)){
              List<Record> filteredRecords= records.stream()
                        .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                        .collect(Collectors.toList());
              return new RecordContainerDto(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        }else{
            return new RecordContainerDto(records , numberOfDoneRecords , numberOfDoneRecords);
        }
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
        recordDao.makeRecordDone(id , RecordStatus.DONE);
    }
}
