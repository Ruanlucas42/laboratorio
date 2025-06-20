package com.ruan.laboratorio.service;

import com.ruan.laboratorio.entity.reserva.Reserva;
import com.ruan.laboratorio.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva saveReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    public Reserva atualizaReserva(Reserva reserva, Integer id) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada " + id));

        Optional.ofNullable(reserva.getData()).ifPresent(reservaExistente::setData);
        Optional.ofNullable(reserva.getHoraInicio()).ifPresent(reservaExistente::setHoraInicio);
        Optional.ofNullable(reserva.getHoraTermino()).ifPresent(reservaExistente::setHoraTermino);

        return reservaRepository.save(reservaExistente);
    }

    public Reserva buscarPorId(Integer id){
        return reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontra" + id));
    }

    public List<Reserva> buscaTodos(){
        return reservaRepository.findAll();
    }

    public void deletarReserva(Integer id){
        if(!reservaRepository.existsById(id)){
            throw new EntityNotFoundException("Reserva não encontrada" + id);
        }
        reservaRepository.deleteById(id);
    }
}
