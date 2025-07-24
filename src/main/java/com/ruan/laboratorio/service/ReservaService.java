package com.ruan.laboratorio.service;

import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.entity.reserva.Reserva;
import com.ruan.laboratorio.entity.reserva.ReservaDTO;
import com.ruan.laboratorio.entity.users.User;
import com.ruan.laboratorio.repository.LabRepository;
import com.ruan.laboratorio.repository.ReservaRepository;
import com.ruan.laboratorio.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabRepository labRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReservaDTO criarReserva(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();

        LocalDate data = parseData(reservaDTO.getData());
        LocalTime horaInicio = parseHora(reservaDTO.getHoraInicio());
        LocalTime horaTermino = parseHora(reservaDTO.getHoraTermino());

        User user = userRepository.findById(reservaDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + reservaDTO.getUserId()));

        Lab lab = labRepository.findById(reservaDTO.getLabId())
                .orElseThrow(() -> new EntityNotFoundException("Laboratório não encontrado: " + reservaDTO.getLabId()));

        if (!validateDataHour(data, horaInicio, horaTermino, lab)) {
            throw new IllegalArgumentException("Já existe uma reserva nesse horário para este laboratório.");
        }

        reserva.setData(data);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraTermino(horaTermino);
        reserva.setUser(user);
        reserva.setLab(lab);

        Reserva reservaSalva = reservaRepository.save(reserva);
        return ReservaDTO.fromEntity(reservaSalva, dateFormatter, hourFormatter);
    }

    public ReservaDTO atualizarReserva(ReservaDTO reservaDTO, Integer id) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada: " + id));

        LocalDate data = parseData(reservaDTO.getData());
        LocalTime horaInicio = parseHora(reservaDTO.getHoraInicio());
        LocalTime horaTermino = parseHora(reservaDTO.getHoraTermino());

        if (data != null) reservaExistente.setData(data);
        if (horaInicio != null) reservaExistente.setHoraInicio(horaInicio);
        if (horaTermino != null) reservaExistente.setHoraTermino(horaTermino);

        if (reservaDTO.getUserId() != null) {
            User user = userRepository.findById(reservaDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + reservaDTO.getUserId()));
            reservaExistente.setUser(user);
        }

        Lab lab = reservaExistente.getLab();
        if (reservaDTO.getLabId() != null) {
            lab = labRepository.findById(reservaDTO.getLabId())
                    .orElseThrow(() -> new EntityNotFoundException("Laboratório não encontrado: " + reservaDTO.getLabId()));
            reservaExistente.setLab(lab);
        }

        if (!validateDataHour(data, horaInicio, horaTermino, lab)) {
            throw new IllegalArgumentException("Já existe uma reserva nesse horário para este laboratório.");
        }

        Reserva reservaAtualizada = reservaRepository.save(reservaExistente);
        return ReservaDTO.fromEntity(reservaAtualizada, dateFormatter, hourFormatter);
    }

    public ReservaDTO buscarPorId(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada: " + id));
        return ReservaDTO.fromEntity(reserva, dateFormatter, hourFormatter);
    }

    public List<ReservaDTO> buscarTodos() {
        List<Reserva> reservas = reservaRepository.findAll();
        return ReservaDTO.fromEntityList(reservas, dateFormatter, hourFormatter);
    }

    public void deletarReserva(Integer id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserva não encontrada: " + id);
        }
        reservaRepository.deleteById(id);
    }

    public boolean validateDataHour(LocalDate data, LocalTime horaInicio, LocalTime horaTermino, Lab lab) {
        if (data == null || horaInicio == null || horaTermino == null || lab == null) return true;
        return !reservaRepository.existsByDataAndLabAndHoraInicioLessThanAndHoraTerminoGreaterThan(data, lab, horaTermino, horaInicio);
    }

    private LocalDate parseData(String dataStr) {
        try {
            return (dataStr != null && !dataStr.isEmpty()) ? LocalDate.parse(dataStr, dateFormatter) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private LocalTime parseHora(String horaStr) {
        try {
            return (horaStr != null && !horaStr.isEmpty()) ? LocalTime.parse(horaStr, hourFormatter) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
