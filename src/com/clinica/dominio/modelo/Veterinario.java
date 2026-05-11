package com.clinica.dominio.modelo;

public class Veterinario {
    private Long id;
    private String nome;
    private String crmv;
    private String especialidade;
    private SituacaoVeterinario situacao;

    public enum SituacaoVeterinario {
        DISPONIVEL,
        OCUPADO
    }

    public Veterinario(Long id, String nome, String crmv, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.situacao = SituacaoVeterinario.DISPONIVEL;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCrmv() { return crmv; }
    public String getEspecialidade() { return especialidade; }
    public SituacaoVeterinario getSituacao() { return situacao; }
    public void setSituacao(SituacaoVeterinario situacao) { this.situacao = situacao; }

    public boolean isDisponivel() {
        return this.situacao == SituacaoVeterinario.DISPONIVEL;
    }

    @Override
    public String toString() {
        return "Veterinario{id=" + id + ", nome='" + nome + "', crmv='" + crmv + "', situacao=" + situacao + "}";
    }
}