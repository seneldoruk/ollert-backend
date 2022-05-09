package com.doruk.ollert.controller;

import com.doruk.ollert.dto.ChangePartPositionDTO;
import com.doruk.ollert.dto.ChangeTaskPositionDTO;
import com.doruk.ollert.dto.SheetViewDTO;
import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.service.SheetPartService;
import com.doruk.ollert.service.SheetService;
import com.doruk.ollert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class SheetPartController {
    UserService userService;
    SheetService sheetService;
    SheetPartService sheetPartService;

    @Autowired
    public SheetPartController(UserService userService, SheetService sheetService, SheetPartService sheetPartService) {
        this.userService = userService;
        this.sheetService = sheetService;
        this.sheetPartService = sheetPartService;
    }

    @DeleteMapping(value = "/sheetparts/{sheetpart_id}")
    public @ResponseBody HttpStatus deleteSheetPart(@PathVariable Long sheetpart_id){
        sheetPartService.deleteSheetPart(sheetpart_id);
        return HttpStatus.OK;
    }

    @PutMapping(value = "/sheetparts/{sheetpart_id}")
    public @ResponseBody HttpStatus changeSheetPartName(@PathVariable Long sheetpart_id, @RequestParam String name){
        sheetPartService.changeSheetPartName(sheetpart_id, name);
        return HttpStatus.OK;
    }
    @DeleteMapping(value = "/sheetparts/{sheetpart_id}/task")
    public @ResponseBody HttpStatus deleteTask(@PathVariable Long sheetpart_id, @RequestParam String task){
        sheetPartService.deleteTask(sheetpart_id, task);
        return HttpStatus.OK;
    }
    @PostMapping(value = "/ sheetparts/{sheetpart_id}/task")
    public @ResponseBody HttpStatus addTask(@PathVariable Long sheetpart_id, @RequestParam String task){
        sheetPartService.addTask(sheetpart_id, task);
        return HttpStatus.OK;
    }

}
