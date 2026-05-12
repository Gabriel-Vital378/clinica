package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnimalRepositorioMemoria implements PortaAnimalRepositorio {

    private final Map<Long, Animal> store = new HashMap<>();

    @Override
    public void salvar(Animal animal) {
        store.put(animal.getId(), animal);
    }

    @Override
    public Optional<Animal> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Animal> listarPorTutor(String tutor) {
        return store.values().stream()
                .filter(a -> a.getTutor().equalsIgnoreCase(tutor))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> listarTodos() {
        return store.values().stream().collect(Collectors.toList());
    }

    @Override
    public void remover(Long id) {
        store.remove(id);
    }
}