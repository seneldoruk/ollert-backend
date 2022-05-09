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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class RestController {
    UserService userService;
    SheetService sheetService;
    SheetPartService sheetPartService;

    @Autowired
    public RestController(UserService userService, SheetService sheetService, SheetPartService sheetPartService) {
        this.userService = userService;
        this.sheetService = sheetService;
        this.sheetPartService = sheetPartService;
    }

    @GetMapping(value = "/tables")
    public @ResponseBody
    List<SheetViewDTO> getSheetsByUser(){
        return sheetService.findAll();
    }

    @GetMapping(value = "/table/{table_id}")
    public @ResponseBody
    Sheet getTable(@PathVariable Long table_id){
        return sheetService.findById(table_id);
    }

    @PutMapping(value = "/table/{table_id}/task", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfTask(@PathVariable Long table_id, @RequestBody ChangeTaskPositionDTO dto){
        try {
            sheetPartService.changeTaskPosition(dto, table_id);
            return HttpStatus.ACCEPTED;
        }catch (Exception e){
            System.err.println(e);
            return HttpStatus.BAD_REQUEST;
        }

    }

    @PutMapping(value = "/table/{table_id}/part", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfSheetPart(@PathVariable Long table_id, @RequestBody ChangePartPositionDTO dto){
        sheetPartService.changePartPosition(dto, table_id);
        return HttpStatus.ACCEPTED;
//        try {
//
//        }catch (Exception e){
//
//            System.out.println(e.getStackTrace().toString());
//            return HttpStatus.BAD_REQUEST;
//        }

    }
    @PostMapping(value="/table/{table_id}")
    public @ResponseBody HttpStatus createNewSheetPart(@PathVariable Long table_id){
        sheetPartService.newSheetPart(table_id);
        return  HttpStatus.OK;
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
    @PutMapping(value="/table/{table_id}")
    public @ResponseBody HttpStatus changeTableName(@PathVariable Long table_id, @RequestParam String name){
        sheetService.changeSheetName(table_id, name);
        return HttpStatus.OK;
    }
    @DeleteMapping(value="/table/{table_id}")
    public @ResponseBody HttpStatus deleteTable(@PathVariable Long table_id){
        sheetService.deleteById(table_id);
        return HttpStatus.OK;
    }
    @PostMapping(value = "/tables")
    public @ResponseBody HttpStatus newTable(@RequestParam String name){
        sheetService.newTable(name);
        return HttpStatus.OK;
    }
}
