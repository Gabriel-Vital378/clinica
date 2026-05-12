package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class NotificacaoCsv implements PortaNotificacaoTutor {

    private final String arquivo;

    public NotificacaoCsv(String arquivo) {
        this.arquivo = arquivo;
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {
            pw.println("timestamp,tipo_evento,tutor,animal,veterinario,data_consulta");
        } catch (IOException e) {
            System.err.println("Erro ao inicializar CSV: " + e.getMessage());
        }
    }

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
            pw.printf("%s,AGENDAMENTO,%s,%s,%s,%sT%s%n",
                    LocalDateTime.now(),
                    tutor,
                    animal.getNome(),
                    consulta.getVeterinario().getNome(),
                    consulta.getData(),
                    consulta.getHora());
        } catch (IOException e) {
            System.err.println("Erro ao gravar CSV: " + e.getMessage());
        }
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
            pw.printf("%s,CANCELAMENTO,%s,%s,,%s%n",
                    LocalDateTime.now(),
                    tutor,
                    animal.getNome(),
                    motivo);
        } catch (IOException e) {
            System.err.println("Erro ao gravar CSV: " + e.getMessage());
        }
    }
}