package com.ruan.laboratorio.controller;

import com.ruan.laboratorio.entity.reserva.Reserva;
import com.ruan.laboratorio.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva){
        Reserva nova = reservaService.saveReserva(reserva);
        return new ResponseEntity<>(nova, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReserva(){
        List<Reserva> reservas = reservaService.buscaTodos();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getById(@PathVariable Integer id){
        Reserva reserva = reservaService.buscarPorId(id);
        return  ResponseEntity.ok(reserva);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizaReserva(@RequestBody Reserva reserva, @PathVariable Integer id){
        reservaService.atualizaReserva(reserva,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaReserva(@PathVariable Integer id){
        reservaService.deletarReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
