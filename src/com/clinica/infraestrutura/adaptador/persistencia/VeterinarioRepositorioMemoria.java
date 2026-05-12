package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {

    private final Map<Long, Veterinario> store = new HashMap<>();

    @Override
    public void salvar(Veterinario vet) {
        store.put(vet.getId(), vet);
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {
        return store.values().stream()
                .filter(Veterinario::estaDisponivel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        return store.values().stream()
                .filter(v -> v.getEspecialidade().equalsIgnoreCase(especialidade))
                .collect(Collectors.toList());
    }
}