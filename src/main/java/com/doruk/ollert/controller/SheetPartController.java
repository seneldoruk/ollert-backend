package com.doruk.ollert.controller;

import com.doruk.ollert.service.SheetPartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
@RequestMapping("sheetparts")
public class SheetPartController {
    SheetPartService sheetPartService;

    public SheetPartController(SheetPartService sheetPartService) {
        this.sheetPartService = sheetPartService;
    }

    @DeleteMapping(value = "/{sheetpart_id}")
    public @ResponseBody
    HttpStatus deleteSheetPart(@PathVariable Long sheetpart_id) {
        try {
            sheetPartService.deleteSheetPart(sheetpart_id);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

    }

    @PutMapping(value = "/{sheetpart_id}")
    public @ResponseBody
    HttpStatus changeSheetPartName(@PathVariable Long sheetpart_id, @RequestParam String name) {
        try {
            sheetPartService.changeSheetPartName(sheetpart_id, name);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/{sheetpart_id}/task")
    public @ResponseBody
    HttpStatus deleteTask(@PathVariable Long sheetpart_id, @RequestParam String task) {
        try {
            sheetPartService.deleteTask(sheetpart_id, task);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping(value = "/{sheetpart_id}/task")
    public @ResponseBody
    HttpStatus addTask(@PathVariable Long sheetpart_id, @RequestParam String task) {
        try {
            sheetPartService.addTask(sheetpart_id, task);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

}
