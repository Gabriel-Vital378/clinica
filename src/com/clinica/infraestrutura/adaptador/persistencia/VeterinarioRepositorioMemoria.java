package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {

    private final Map<Long, Veterinario> armazenamento = new HashMap<>();

    @Override
    public Veterinario salvar(Veterinario veterinario) {
        armazenamento.put(veterinario.getId(), veterinario);
        return veterinario;
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        return Optional.ofNullable(armazenamento.get(id));
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {
        return armazenamento.values().stream()
                .filter(Veterinario::isDisponivel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        return armazenamento.values().stream()
                .filter(v -> v.getEspecialidade().equalsIgnoreCase(especialidade))
                .collect(Collectors.toList());
    }

    @Override
    public List<Veterinario> listarTodos() {
        return new ArrayList<>(armazenamento.values());
    }
}