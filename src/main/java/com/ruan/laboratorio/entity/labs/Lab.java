package com.ruan.laboratorio.entity.labs;

import com.ruan.laboratorio.entity.reserva.Reserva;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private int capacidade;

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Lab(){
    }

    public Lab(int id, String nome, String descricao, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}

