package com.ruan.laboratorio.controller;

import com.ruan.laboratorio.entity.reserva.Reserva;
import com.ruan.laboratorio.entity.reserva.ReservaDTO;
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

    @PostMapping("criar")
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody ReservaDTO reservaDTO){
        ReservaDTO novaReserva = reservaService.criarReserva(reservaDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @GetMapping("buscar")
    public ResponseEntity<List<ReservaDTO>> getAllReserva(){
        List<ReservaDTO> reservas = reservaService.buscarTodos();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getById(@PathVariable Integer id){
        ReservaDTO reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizaReserva(@RequestBody ReservaDTO reservaDTO, @PathVariable Integer id){
        reservaService.atualizarReserva(reservaDTO,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaReserva(@PathVariable Integer id){
        reservaService.deletarReserva(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
