package com.clinica.apresentacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoConsole;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoCsv;
import com.clinica.infraestrutura.adaptador.persistencia.AnimalRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.ConsultaRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.VeterinarioRepositorioMemoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── Repositórios compartilhados ──
        PortaAnimalRepositorio animais     = new AnimalRepositorioMemoria();
        PortaVeterinarioRepositorio vets   = new VeterinarioRepositorioMemoria();
        PortaConsultaRepositorio consultas = new ConsultaRepositorioMemoria();

        // ── Cadastro de 2 animais ──
        Animal thor = new Animal(1L, "Thor", "Cachorro", "Labrador",
                LocalDate.of(2020, 5, 10), "João Silva");
        Animal luna = new Animal(2L, "Luna", "Gato", "Siamês",
                LocalDate.of(2021, 8, 22), "Maria Souza");
        animais.salvar(thor);
        animais.salvar(luna);

        // ── Cadastro de 2 veterinários ──
        Veterinario dra = new Veterinario(1L, "Dra. Beatriz", "CRMV-1001", "Clínica Geral");
        Veterinario dr  = new Veterinario(2L, "Dr. Marcos",   "CRMV-1002", "Ortopedia");
        vets.salvar(dra);
        vets.salvar(dr);

        // ── Passo 1: Agendar ROTINA com Console ──
        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(
                animais, vets, consultas, new NotificacaoConsole());

        Consulta c1 = agenda.agendarConsulta(
                1L, 1L,
                LocalDate.of(2025, 7, 15), LocalTime.of(14, 30),
                TipoConsulta.ROTINA);

        // ── Passo 2: Agendar EMERGENCIA com CSV — troca só o adaptador de notificação ──
        // O mesmo serviço é recriado apenas para demonstrar a troca; repositórios são os mesmos
        PortaAgendaConsulta agendaCsv = new ServicoAgendaConsulta(
                animais, vets, consultas, new NotificacaoCsv("notificacoes.csv"));

        Consulta c2 = agendaCsv.agendarConsulta(
                2L, 2L,
                LocalDate.of(2025, 7, 16), LocalTime.of(9, 0),
                TipoConsulta.EMERGENCIA);

        System.out.println("[AGENDAMENTO via CSV] Tutor: " + luna.getTutor()
                + " | Animal: " + luna.getNome()
                + " | Vet.: " + dr.getNome()
                + " | Data: " + c2.getData() + " às " + c2.getHora()
                + " | Tipo: " + c2.getTipo());

        // ── Passo 3: Realizar primeira consulta ──
        agenda.realizarConsulta(c1.getId(), "Exame de rotina sem alterações.");
        System.out.println("[CONSULTA REALIZADA] Animal: " + thor.getNome()
                + " | Obs.: Exame de rotina sem alterações.");

        // ── Passo 4: Cancelar segunda consulta ──
        agendaCsv.cancelarConsulta(c2.getId());

        // ── Passo 5: Histórico do primeiro animal ──
        System.out.println("\n=== Histórico de " + thor.getNome() + " ===");
        List<Consulta> historico = agenda.obterHistoricoAnimal(1L);
        for (Consulta c : historico) {
            System.out.println("Consulta #" + c.getId() + " — " + c.getData() + " "
                    + c.getHora() + " | " + c.getTipo() + " | " + c.getSituacao());
        }

        // ── Passo 6: Agenda da primeira veterinária ──
        System.out.println("\n=== Agenda de " + dra.getNome() + " ===");
        List<Consulta> agendaVet = agenda.obterAgendaVeterinario(1L);
        for (Consulta c : agendaVet) {
            System.out.println("Consulta #" + c.getId() + " — " + c.getData() + " "
                    + c.getHora() + " | " + c.getTipo() + " | " + c.getSituacao());
        }
    }
}