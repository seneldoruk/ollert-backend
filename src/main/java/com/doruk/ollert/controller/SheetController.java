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
public class SheetController {

    UserService userService;
    SheetService sheetService;
    SheetPartService sheetPartService;

    @Autowired
    public SheetController(UserService userService, SheetService sheetService, SheetPartService sheetPartService) {
        this.userService = userService;
        this.sheetService = sheetService;
        this.sheetPartService = sheetPartService;
    }

    @GetMapping(value = "/sheet/all")
    public @ResponseBody
    List<SheetViewDTO> getSheetsByUser(){

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return sheetService.findAll();
    }

    @GetMapping(value = "/sheet/{sheet_id}")
    public @ResponseBody
    Sheet getSheet(@PathVariable Long sheet_id){
        return sheetService.findById(sheet_id);
    }

    @PutMapping(value = "/sheet/{sheet_id}/task", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfTask(@PathVariable Long sheet_id, @RequestBody ChangeTaskPositionDTO dto){
        try {
            sheetPartService.changeTaskPosition(dto, sheet_id);
            return HttpStatus.ACCEPTED;
        }catch (Exception e){
            System.err.println(e);
            return HttpStatus.BAD_REQUEST;
        }

    }

    @PutMapping(value = "/sheet/{sheet_id}/part", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfSheetPart(@PathVariable Long sheet_id, @RequestBody ChangePartPositionDTO dto){
        sheetPartService.changePartPosition(dto, sheet_id);
        return HttpStatus.ACCEPTED;
//        try {
//
//        }catch (Exception e){
//
//            System.out.println(e.getStackTrace().toString());
//            return HttpStatus.BAD_REQUEST;
//        }

    }
    @PostMapping(value= "/sheet/{sheet_id}")
    public @ResponseBody HttpStatus createNewSheetPart(@PathVariable Long sheet_id){
        sheetPartService.newSheetPart(sheet_id);
        return  HttpStatus.OK;
    }

    @PutMapping(value= "/sheet/{sheet_id}")
    public @ResponseBody HttpStatus changeSheetName(@PathVariable Long sheet_id, @RequestParam String name){
        sheetService.changeSheetName(sheet_id, name);
        return HttpStatus.OK;
    }
    @DeleteMapping(value= "/sheet/{sheet_id}")
    public @ResponseBody HttpStatus deleteSheet(@PathVariable Long sheet_id){
        sheetService.deleteById(sheet_id);
        return HttpStatus.OK;
    }
    @PostMapping(value = "/sheet/all")
    public @ResponseBody HttpStatus newSheet(@RequestParam String name){
        sheetService.newSheet(name);
        return HttpStatus.OK;
    }
}
