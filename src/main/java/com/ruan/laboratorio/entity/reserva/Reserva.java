package com.ruan.laboratorio.entity.reserva;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruan.laboratorio.entity.labs.Lab;
import com.ruan.laboratorio.entity.users.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reserva {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime horaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime horaTermino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Lab lab;

    public Reserva(){
    }

    public Reserva(String id, LocalDate data, LocalDateTime horaInicio, LocalDateTime horaTermino, User user, Lab lab) {
        this.id = id;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.user = user;
        this.lab = lab;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(LocalDateTime horaTermino) {
        this.horaTermino = horaTermino;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }
}
