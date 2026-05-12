package com.clinica.apresentacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoConsole;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoCsv;
import com.clinica.infraestrutura.adaptador.persistencia.AnimalRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.ConsultaRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.VeterinarioRepositorioMemoria;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        var animalRepo      = new AnimalRepositorioMemoria();
        var veterinarioRepo = new VeterinarioRepositorioMemoria();
        var consultaRepo    = new ConsultaRepositorioMemoria();

        var animal = new Animal(1L, "Rex", "Cachorro", "Labrador",
                LocalDate.of(2020, 3, 10), "Carlos Silva");
        var vet = new Veterinario(1L, "Dra. Ana Lima", "CRMV-12345", "Clínica Geral");

        animalRepo.salvar(animal);
        veterinarioRepo.salvar(vet);

        // ── Adaptador Console ──
        System.out.println("========================================");
        System.out.println("  ADAPTADOR: NotificacaoConsole");
        System.out.println("========================================");

        PortaAgendaConsulta servicoConsole = new ServicoAgendaConsulta(
                animalRepo, veterinarioRepo, consultaRepo, new NotificacaoConsole()
        );

        var consulta1 = servicoConsole.agendarConsulta(
                1L, 1L, LocalDate.of(2026, 6, 15), LocalTime.of(10, 30), TipoConsulta.ROTINA
        );
        servicoConsole.realizarConsulta(consulta1.getId(), "Animal saudável, vacinação em dia.");

        // ── Troca para CSV — domínio intocado ──
        System.out.println("\n========================================");
        System.out.println("  ADAPTADOR: NotificacaoCsv");
        System.out.println("========================================");

        PortaAgendaConsulta servicoCsv = new ServicoAgendaConsulta(
                animalRepo, veterinarioRepo, consultaRepo, new NotificacaoCsv()
        );

        var consulta2 = servicoCsv.agendarConsulta(
                1L, 1L, LocalDate.of(2026, 6, 20), LocalTime.of(14, 0), TipoConsulta.RETORNO
        );
        servicoCsv.cancelarConsulta(consulta2.getId());

        System.out.println("\nNotificações gravadas em: notificacoes.csv");

        // ── Histórico ──
        System.out.println("\n========================================");
        System.out.println("  HISTÓRICO DO ANIMAL: " + animal.getNome());
        System.out.println("========================================");
        consultaRepo.buscarPorAnimal(1L).forEach(c -> System.out.println("  " + c));
    }
}