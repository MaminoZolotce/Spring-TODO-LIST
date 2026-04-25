package com.maminozolotce.controllers;

import com.maminozolotce.entity.DTO.RecordContainerDto;
import com.maminozolotce.entity.RecordStatus;
import com.maminozolotce.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getMainPage(Model model , @RequestParam(name="filter", required = false) String filterMode){
        RecordContainerDto records = recordService.findAllRecords(filterMode);
        model.addAttribute("records", records.getRecords());
        model.addAttribute("numberOfActiveRecords", records.getNumberOfActiveRecords());
        model.addAttribute("numberOfDoneRecords", records.getNumberOfDoneRecords());
        return "main-page";
    }

    /*@RequestMapping("/home")
    public ModelAndView getMainPage(Model model , @RequestParam(name="filter", required = false) String filterMode){
        ModelAndView modelAndView = new ModelAndView();
        RecordContainerDto records = recordService.findAllRecords(filterMode);
        modelAndView.setViewName("main-page");
        modelAndView.getModelMap().addAttribute("records", records.getRecords());
        modelAndView.getModelMap().addAttribute("numberOfActiveRecords", records.getNumberOfActiveRecords());
        modelAndView.getModelMap().addAttribute("numberOfDoneRecords", records.getNumberOfDoneRecords());
        return modelAndView;
    }*/


    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam("title") String title ){
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/remove-record",method = RequestMethod.POST)
    public String removeRecord(@RequestParam("id") int id,
                               @RequestParam(name = "filterMode", required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter="+filterMode : "");
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam("id") int id,
                                 @RequestParam(name="filter", required = false) String filterMode,
                                 @RequestParam("status")RecordStatus status){
        recordService.makeRecordDone(id, status);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter="+filterMode : "");
    }
}
