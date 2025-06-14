package com.ruan.laboratorio.repository;

import com.ruan.laboratorio.entity.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
