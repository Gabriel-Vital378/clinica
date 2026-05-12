package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultaRepositorioMemoria implements PortaConsultaRepositorio {

    private final Map<Long, Consulta> store = new HashMap<>();

    @Override
    public void salvar(Consulta consulta) {
        store.put(consulta.getId(), consulta);
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Consulta> buscarPorAnimal(Long animalId) {
        return store.values().stream()
                .filter(c -> c.getAnimal().getId().equals(animalId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorVeterinario(Long vetId) {
        return store.values().stream()
                .filter(c -> c.getVeterinario().getId().equals(vetId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> listarAgendadas() {
        return store.values().stream()
                .filter(c -> c.getSituacao() == SituacaoConsulta.AGENDADA)
                .collect(Collectors.toList());
    }
}