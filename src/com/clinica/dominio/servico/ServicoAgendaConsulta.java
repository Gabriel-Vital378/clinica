package com.clinica.dominio.servico;

import com.clinica.dominio.excecao.AnimalNaoEncontradoException;
import com.clinica.dominio.excecao.VeterinarioIndisponivelException;
import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoAgendaConsulta implements PortaAgendaConsulta {

    private final PortaAnimalRepositorio animalRepositorio;
    private final PortaVeterinarioRepositorio veterinarioRepositorio;
    private final PortaConsultaRepositorio consultaRepositorio;
    private final PortaNotificacaoTutor notificacaoTutor;
    private final AtomicLong idGerador = new AtomicLong(1);

    public ServicoAgendaConsulta(
            PortaAnimalRepositorio animalRepositorio,
            PortaVeterinarioRepositorio veterinarioRepositorio,
            PortaConsultaRepositorio consultaRepositorio,
            PortaNotificacaoTutor notificacaoTutor) {
        this.animalRepositorio = animalRepositorio;
        this.veterinarioRepositorio = veterinarioRepositorio;
        this.consultaRepositorio = consultaRepositorio;
        this.notificacaoTutor = notificacaoTutor;
    }

    @Override
    public Consulta agendarConsulta(Long animalId, Long veterinarioId, LocalDate data, LocalTime hora, TipoConsulta tipo) {
        Animal animal = animalRepositorio.buscarPorId(animalId)
                .orElseThrow(() -> new AnimalNaoEncontradoException(animalId));

        Veterinario veterinario = veterinarioRepositorio.buscarPorId(veterinarioId)
                .orElseThrow(() -> new VeterinarioIndisponivelException(veterinarioId));

        if (!veterinario.isDisponivel()) {
            throw new VeterinarioIndisponivelException(veterinarioId);
        }

        Consulta consulta = new Consulta(idGerador.getAndIncrement(), animal, veterinario, data, hora, tipo);
        veterinario.setSituacao(Veterinario.SituacaoVeterinario.OCUPADO);
        veterinarioRepositorio.salvar(veterinario);

        Consulta consultaSalva = consultaRepositorio.salvar(consulta);
        notificacaoTutor.notificarAgendamento(consultaSalva);
        return consultaSalva;
    }

    @Override
    public Consulta realizarConsulta(Long consultaId, String observacoes) {
        Consulta consulta = consultaRepositorio.buscarPorId(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada: " + consultaId));

        consulta.setSituacao(SituacaoConsulta.REALIZADA);
        consulta.setObservacoes(observacoes);

        consulta.getVeterinario().setSituacao(Veterinario.SituacaoVeterinario.DISPONIVEL);
        veterinarioRepositorio.salvar(consulta.getVeterinario());

        return consultaRepositorio.salvar(consulta);
    }

    @Override
    public Consulta cancelarConsulta(Long consultaId) {
        Consulta consulta = consultaRepositorio.buscarPorId(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada: " + consultaId));

        consulta.setSituacao(SituacaoConsulta.CANCELADA);

        consulta.getVeterinario().setSituacao(Veterinario.SituacaoVeterinario.DISPONIVEL);
        veterinarioRepositorio.salvar(consulta.getVeterinario());

        Consulta consultaSalva = consultaRepositorio.salvar(consulta);
        notificacaoTutor.notificarCancelamento(consultaSalva);
        return consultaSalva;
    }

    @Override
    public List<Consulta> obterHistoricoAnimal(Long animalId) {
        return consultaRepositorio.buscarPorAnimal(animalId);
    }

    @Override
    public List<Consulta> obterAgendaVeterinario(Long veterinarioId) {
        return consultaRepositorio.buscarPorVeterinario(veterinarioId);
    }
}