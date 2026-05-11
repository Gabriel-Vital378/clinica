package com.clinica.dominio.porta.saida;

import com.clinica.dominio.modelo.Veterinario;
import java.util.List;
import java.util.Optional;

public interface PortaVeterinarioRepositorio {
    Veterinario salvar(Veterinario veterinario);
    Optional<Veterinario> buscarPorId(Long id);
    List<Veterinario> buscarDisponiveis();
    List<Veterinario> buscarPorEspecialidade(String especialidade);
    List<Veterinario> listarTodos();
}