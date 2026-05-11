package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private Long id;
    private Animal animal;
    private Veterinario veterinario;
    private LocalDate data;
    private LocalTime hora;
    private TipoConsulta tipo;
    private SituacaoConsulta situacao;
    private String observacoes;

    public Consulta(Long id, Animal animal, Veterinario veterinario, LocalDate data, LocalTime hora, TipoConsulta tipo) {
        this.id = id;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.situacao = SituacaoConsulta.AGENDADA;
        this.observacoes = "";
    }

    public Long getId() { return id; }
    public Animal getAnimal() { return animal; }
    public Veterinario getVeterinario() { return veterinario; }
    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
    public TipoConsulta getTipo() { return tipo; }
    public SituacaoConsulta getSituacao() { return situacao; }
    public String getObservacoes() { return observacoes; }

    public void setSituacao(SituacaoConsulta situacao) { this.situacao = situacao; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", animal=" + animal.getNome() + ", veterinario=" + veterinario.getNome()
                + ", data=" + data + ", hora=" + hora + ", tipo=" + tipo + ", situacao=" + situacao + "}";
    }
}