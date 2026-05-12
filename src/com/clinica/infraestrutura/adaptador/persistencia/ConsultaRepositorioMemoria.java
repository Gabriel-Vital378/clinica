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

    private final Map<Long, Consulta> armazenamento = new HashMap<>();

    @Override
    public Consulta salvar(Consulta consulta) {
        armazenamento.put(consulta.getId(), consulta);
        return consulta;
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return Optional.ofNullable(armazenamento.get(id));
    }

    @Override
    public List<Consulta> buscarPorAnimal(Long animalId) {
        return armazenamento.values().stream()
                .filter(c -> c.getAnimal().getId().equals(animalId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorVeterinario(Long veterinarioId) {
        return armazenamento.values().stream()
                .filter(c -> c.getVeterinario().getId().equals(veterinarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> listarAgendadas() {
        return armazenamento.values().stream()
                .filter(c -> c.getSituacao() == SituacaoConsulta.AGENDADA)
                .collect(Collectors.toList());
    }
}