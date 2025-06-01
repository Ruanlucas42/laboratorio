package com.ruan.laboratorio.service;


import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.repository.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    public void saveLab(Lab lab){
        labRepository.save(lab);
    }

    public void updateLab(Lab lab, Integer id){
        Lab labAtualizado = labRepository.findById(id).orElseThrow();
        labAtualizado.setNome(lab.getNome());
        labAtualizado.setDescricao(lab.getDescricao());
        labRepository.save(labAtualizado);
    }

    public List<Lab> getLabs(){
        return labRepository.findAll();
    }

    public Lab getLabId(Integer id){
        return labRepository.findById(id).orElseThrow();
    }

    public void deleteById(Integer id){
        labRepository.deleteById(id);
    }
}
