package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Animal {
    private final Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final LocalDate dataNascimento;
    private final String tutor;

    public Animal(Long id, String nome, String especie, String raca, LocalDate dataNascimento, String tutor) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (especie == null || especie.isBlank()) throw new IllegalArgumentException("Espécie não pode ser nula ou vazia.");
        if (tutor == null || tutor.isBlank()) throw new IllegalArgumentException("Tutor não pode ser nulo ou vazio.");
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public int calcularIdadeEmAnos() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
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