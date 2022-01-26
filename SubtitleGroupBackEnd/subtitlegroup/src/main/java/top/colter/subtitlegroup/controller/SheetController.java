package top.colter.subtitlegroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.colter.subtitlegroup.entity.Sheet;
import top.colter.subtitlegroup.service.SheetService;

import java.util.List;

@RestController
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @PostMapping("/insert")
    public String insertSheet(@RequestBody Sheet sheet){
        try {
            sheetService.saveSheet(sheet);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return "1";
    }

    @GetMapping("/query/{organ}/{group}")
    public List<Sheet> querySheet(@PathVariable("organ") String organ, @PathVariable("group") String group){
        try {
            return sheetService.findSheetByPertain("/"+organ+"/"+group);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/update")
    public String updateSheet(@RequestBody Sheet sheet){
        try {
            sheetService.updateSheet(sheet);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return "1";
    }

}
