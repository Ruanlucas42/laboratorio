package com.ruan.laboratorio.repository;

import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.entity.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    boolean existsByDataAndLabAndHoraInicioLessThanAndHoraTerminoGreaterThan(
            LocalDate data,
            Lab lab,
            LocalTime horaTermino,
            LocalTime horaInicio
    );
}
