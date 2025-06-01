package com.ruan.laboratorio.repository;


import com.ruan.laboratorio.entity.labs.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepository extends JpaRepository<Lab, Integer> {
}
