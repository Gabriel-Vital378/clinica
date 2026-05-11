package com.clinica.dominio.excecao;

public class VeterinarioIndisponivelException extends RuntimeException {
    public VeterinarioIndisponivelException(Long id) {
        super("Veterinário indisponível com o ID: " + id);
    }
}