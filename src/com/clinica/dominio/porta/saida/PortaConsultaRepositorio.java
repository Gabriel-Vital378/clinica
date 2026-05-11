package com.clinica.dominio.porta.saida;

import com.clinica.dominio.modelo.Consulta;
import java.util.List;
import java.util.Optional;

public interface PortaConsultaRepositorio {
    Consulta salvar(Consulta consulta);
    Optional<Consulta> buscarPorId(Long id);
    List<Consulta> buscarPorAnimal(Long animalId);
    List<Consulta> buscarPorVeterinario(Long veterinarioId);
    List<Consulta> listarAgendadas();
}