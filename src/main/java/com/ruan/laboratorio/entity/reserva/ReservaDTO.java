package com.ruan.laboratorio.entity.reserva;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReservaDTO {

    private Integer id;
    private String data;
    private String horaInicio;
    private String horaTermino;
    private Integer userId;
    private String labNome;
    private Integer labId;

    public static ReservaDTO fromEntity(Reserva reserva, DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setData(reserva.getData().format(dateFormatter));
        dto.setHoraInicio(reserva.getHoraInicio().format(timeFormatter));
        dto.setHoraTermino(reserva.getHoraTermino().format(timeFormatter));
        dto.setUserId(reserva.getUser().getId());
        dto.setLabId(reserva.getLab().getId());
        dto.setLabNome(reserva.getLab().getNome());
        return dto;
    }

    public static List<ReservaDTO> fromEntityList(List<Reserva> reservas, DateTimeFormatter dateFormatter, DateTimeFormatter hourFormatter) {
        return reservas.stream()
                .map(reserva -> fromEntity(reserva, dateFormatter, hourFormatter))
                .collect(Collectors.toList());
    }



}
