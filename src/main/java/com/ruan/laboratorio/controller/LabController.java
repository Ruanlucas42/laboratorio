package com.ruan.laboratorio.controller;


import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/laboratorios")
public class LabController {

    @Autowired
    private LabService labService;

    @PostMapping
    public ResponseEntity<Lab> saveLab(@RequestBody Lab lab){
        labService.saveLab(lab);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Lab>> getAllLab(){
        List<Lab> labs = labService.getLabs();
        return ResponseEntity.ok(labs);
    }

    @GetMapping("{id}")
    public ResponseEntity<Lab> getLab(@PathVariable Integer id){
        Lab lab = labService.getLabId(id);
        return ResponseEntity.ok(lab);
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@RequestBody Lab lab, @PathVariable Integer id){
        labService.updateLab(lab,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        labService.deleteById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
