package com.maminozolotce.config.controllers;

import com.maminozolotce.config.entity.Record;
import com.maminozolotce.config.entity.RecordStatus;
import com.maminozolotce.config.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getMainPage(Model model){
        List<Record> records = recordService.findAllRecords();
        int numberOfActiveRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.ACTIVE)
                .count();
        int numberOfDoneRecords = (int) records.stream()
                .filter(record -> record.getStatus() == RecordStatus.DONE)
                .count();
        model.addAttribute("records", records);
        model.addAttribute("numberOfActiveRecords", numberOfActiveRecords);
        model.addAttribute("numberOfDoneRecords", numberOfDoneRecords);
        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam("title") String title ){
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/remove-record",method = RequestMethod.POST)
    public String removeRecord(@RequestParam("id") int id){
        recordService.deleteRecord(id);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam("id") int id){
        recordService.makeRecordDone(id);
        return "redirect:/home";
    }
}
