package com.ruan.laboratorio.service;

import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.entity.reserva.Reserva;
import com.ruan.laboratorio.entity.reserva.ReservaDTO;
import com.ruan.laboratorio.entity.users.User;
import com.ruan.laboratorio.entity.users.UserDTO;
import com.ruan.laboratorio.repository.LabRepository;
import com.ruan.laboratorio.repository.ReservaRepository;
import com.ruan.laboratorio.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabRepository labRepository;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReservaDTO criarReserva(ReservaDTO reservaDTO){

        Reserva reserva = new Reserva();

        LocalDate data = LocalDate.parse(reservaDTO.getData(),dateFormatter);
        LocalTime horaInicio = LocalTime.parse(reservaDTO.getHoraInicio(),hourFormatter);
        LocalTime horaTermino = LocalTime.parse(reservaDTO.getHoraTermino(),hourFormatter);

        User user = userRepository.findById(reservaDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usario não encontrado: " + reservaDTO.getUserId()));

        Lab lab = labRepository.findById(reservaDTO.getLabId())
                .orElseThrow(() -> new EntityNotFoundException("Laboratorio não encontrado: " + reservaDTO.getLabId()));

        if (!validateDataHour(data, horaInicio, horaTermino, lab)) {
            throw new IllegalArgumentException("Já existe uma reserva nesse horário para este laboratório.");
        }

        reserva.setData(data);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraTermino(horaTermino);
        reserva.setUser(user);
        reserva.setLab(lab);

        Reserva resevaSalva = reservaRepository.save(reserva);

        return ReservaDTO.fromEntity(resevaSalva,dateFormatter,hourFormatter);
    }

    public ReservaDTO atualizarReserva(ReservaDTO reservaDTO, Integer id){

        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada: " + id));

        if(reservaDTO.getData() != null && !reservaDTO.getData().isEmpty()){
            reservaExistente.setData(LocalDate.parse(reservaDTO.getData(),dateFormatter));
        }

        if(reservaDTO.getHoraInicio() != null && !reservaDTO.getHoraInicio().isEmpty()){
            reservaExistente.setHoraInicio(LocalTime.parse(reservaDTO.getHoraInicio(),hourFormatter));
        }

        if(reservaDTO.getHoraTermino() != null && !reservaDTO.getHoraTermino().isEmpty()){
            reservaExistente.setHoraTermino(LocalTime.parse(reservaDTO.getHoraTermino(),hourFormatter));
        }

        if(reservaDTO.getUserId() != null){
            User user = userRepository.findById(reservaDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado " + reservaDTO.getUserId()));
            reservaExistente.setUser(user);
        }

        if(reservaDTO.getLabId() != null){
            Lab lab = labRepository.findById(reservaDTO.getLabId())
                    .orElseThrow(() -> new EntityNotFoundException("Laboratorio não encontrado " + reservaDTO.getLabId()));
            reservaExistente.setLab(lab);
        }

        Reserva reservaAtualizada = reservaRepository.save(reservaExistente);
        return ReservaDTO.fromEntity(reservaAtualizada,dateFormatter,hourFormatter);
    }

    public ReservaDTO buscarPorId(Integer id){
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        return ReservaDTO.fromEntity(reserva,dateFormatter,hourFormatter);
    }

    public List<ReservaDTO> buscarTodos(){
        List<Reserva> reservas = reservaRepository.findAll();
        return ReservaDTO.fromEntityList(reservas,dateFormatter,hourFormatter);
    }

    public void deletarReserva(Integer id){
        if(!reservaRepository.existsById(id)){
            throw new EntityNotFoundException("Reserva não encontrada " + id);
        }
        reservaRepository.deleteById(id);
    }

    public boolean validateDataHour(LocalDate data, LocalTime horaInicio, LocalTime horaTermino, Lab lab) {
        return !reservaRepository.existsByDataAndLabAndHoraInicioLessThanAndHoraTerminoGreaterThan(data, lab, horaTermino, horaInicio);
    }
}
