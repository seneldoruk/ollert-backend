package com.doruk.ollert.controller;

import com.doruk.ollert.service.SheetPartService;
import com.doruk.ollert.service.SheetService;
import com.doruk.ollert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
@RequestMapping("sheetparts")
public class SheetPartController {
    SheetPartService sheetPartService;

    @Autowired
    public SheetPartController(SheetPartService sheetPartService) {
        this.sheetPartService = sheetPartService;
    }

    @DeleteMapping(value = "/{sheetpart_id}")
    public @ResponseBody HttpStatus deleteSheetPart(@PathVariable Long sheetpart_id){
        sheetPartService.deleteSheetPart(sheetpart_id);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/{sheetpart_id}")
    public @ResponseBody HttpStatus changeSheetPartName(@PathVariable Long sheetpart_id, @RequestParam String name){
        sheetPartService.changeSheetPartName(sheetpart_id, name);
        return HttpStatus.OK;
    }
    @DeleteMapping(value = "/{sheetpart_id}/task")
    public @ResponseBody HttpStatus deleteTask(@PathVariable Long sheetpart_id, @RequestParam String task){
        sheetPartService.deleteTask(sheetpart_id, task);
        return HttpStatus.OK;
    }
    @PostMapping(value = "/{sheetpart_id}/task")
    public @ResponseBody HttpStatus addTask(@PathVariable Long sheetpart_id, @RequestParam String task){
        sheetPartService.addTask(sheetpart_id, task);
        return HttpStatus.OK;
    }

}
