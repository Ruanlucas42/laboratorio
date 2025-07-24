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
        dto.setData(reserva.getData() != null ? reserva.getData().format(dateFormatter) : null);
        dto.setHoraInicio(reserva.getHoraInicio() != null ? reserva.getHoraInicio().format(timeFormatter) : null);
        dto.setHoraTermino(reserva.getHoraTermino() != null ? reserva.getHoraTermino().format(timeFormatter) : null);

        if (reserva.getUser() != null) {
            dto.setUserId(reserva.getUser().getId());
        }

        if (reserva.getLab() != null) {
            dto.setLabId(reserva.getLab().getId());
            dto.setLabNome(reserva.getLab().getNome());
        }
        return dto;
    }

    public static List<ReservaDTO> fromEntityList(List<Reserva> reservas, DateTimeFormatter dateFormatter, DateTimeFormatter hourFormatter) {
        return reservas.stream()
                .map(reserva -> fromEntity(reserva, dateFormatter, hourFormatter))
                .collect(Collectors.toList());
    }




}
