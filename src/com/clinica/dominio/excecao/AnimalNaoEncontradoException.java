package com.clinica.dominio.excecao;

public class AnimalNaoEncontradoException extends RuntimeException {
    public AnimalNaoEncontradoException(Long id) {
        super("Animal não encontrado com o ID: " + id);
    }
}