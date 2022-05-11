package com.doruk.ollert.controller;

import com.doruk.ollert.dto.ChangePartPositionDTO;
import com.doruk.ollert.dto.ChangeTaskPositionDTO;
import com.doruk.ollert.dto.SheetAccessDTO;
import com.doruk.ollert.dto.SheetViewDTO;
import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.service.SheetPartService;
import com.doruk.ollert.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("sheet")
public class SheetController {

    SheetService sheetService;
    SheetPartService sheetPartService;

    @Autowired
    public SheetController(SheetService sheetService, SheetPartService sheetPartService) {
        this.sheetService = sheetService;
        this.sheetPartService = sheetPartService;
    }

    @GetMapping(value = "/all")
    public @ResponseBody
    List<SheetViewDTO> getSheetsByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return sheetService.findAllByUsername(username);
    }

    @GetMapping(value = "/{sheet_id}")
    public @ResponseBody
    Sheet getSheet(@PathVariable Long sheet_id, HttpServletResponse res) {
        try {
            return sheetService.findById(sheet_id);
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(400);
            return null;
        }
    }

    @PutMapping(value = "/{sheet_id}/task", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfTask(@PathVariable Long sheet_id, @RequestBody ChangeTaskPositionDTO dto) {
        try {
            sheetPartService.changeTaskPosition(dto, sheet_id);
            return HttpStatus.OK;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }

    }

    @PutMapping(value = "/{sheet_id}/part", consumes = "application/json")
    public @ResponseBody
    HttpStatus changePositionOfSheetPart(@PathVariable Long sheet_id, @RequestBody ChangePartPositionDTO dto) {
        sheetPartService.changePartPosition(dto, sheet_id);
        return HttpStatus.OK;
//        try {
//
//        }catch (Exception e){
//
//            System.out.println(e.getStackTrace().toString());
//            return HttpStatus.BAD_REQUEST;
//        }

    }

    @PostMapping(value = "/{sheet_id}")
    public @ResponseBody
    HttpStatus createNewSheetPart(@PathVariable Long sheet_id) {
        try {
            sheetPartService.newSheetPart(sheet_id);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping(value = "/{sheet_id}")
    public @ResponseBody
    HttpStatus changeSheetName(@PathVariable Long sheet_id, @RequestParam String name) {
        try {
            sheetService.changeSheetName(sheet_id, name);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/{sheet_id}")
    public @ResponseBody
    HttpStatus deleteSheet(@PathVariable Long sheet_id) {
        try {
            sheetService.deleteById(sheet_id);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }


    @PostMapping(value = "/all")
    public @ResponseBody
    HttpStatus newSheet(@RequestParam String name) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        try {
            sheetService.newSheet(name, username);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

    }

    @GetMapping(value = "/{sheet_id}/users")
    public @ResponseBody
    SheetAccessDTO sheetAccessUsers(@PathVariable Long sheet_id, HttpServletResponse res){
        try {
            return sheetService.sheetAccess(sheet_id);
        }catch (Exception e){
            res.setStatus(400);
            return null;
        }
    }

    @PutMapping(value = "/{sheet_id}/users")
    public @ResponseBody HttpStatus sheetAccessAdd(@PathVariable Long sheet_id, @RequestParam String username) {
        try {
            sheetService.sheetAccessAdd(sheet_id, username);
            return HttpStatus.OK;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/{sheet_id}/users")
    public @ResponseBody HttpStatus sheetAccessRemove(@PathVariable Long sheet_id, @RequestParam String username) {
        try {
            sheetService.sheetAccessRemove(sheet_id, username);
            return HttpStatus.OK;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}
