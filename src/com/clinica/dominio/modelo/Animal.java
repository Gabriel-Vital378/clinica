package com.clinica.dominio.modelo;

import java.time.LocalDate;

public class Animal {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private String tutor;

    public Animal(Long id, String nome, String especie, String raca, LocalDate dataNascimento, String tutor) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTutor() { return tutor; }

    @Override
    public String toString() {
        return "Animal{id=" + id + ", nome='" + nome + "', especie='" + especie + "', tutor='" + tutor + "'}";
    }
}