package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NotificacaoCsv implements PortaNotificacaoTutor {

    private static final String ARQUIVO = "notificacoes.csv";

    public NotificacaoCsv() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            pw.println("tipo,tutor,animal,veterinario,data,hora,tipoConsulta");
        } catch (IOException e) {
            System.err.println("Erro ao inicializar CSV: " + e.getMessage());
        }
    }

    @Override
    public void notificarAgendamento(Consulta consulta) {
        escreverLinha("AGENDAMENTO", consulta);
    }

    @Override
    public void notificarCancelamento(Consulta consulta) {
        escreverLinha("CANCELAMENTO", consulta);
    }

    private void escreverLinha(String tipo, Consulta consulta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, true))) {
            pw.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    tipo,
                    consulta.getAnimal().getTutor(),
                    consulta.getAnimal().getNome(),
                    consulta.getVeterinario().getNome(),
                    consulta.getData(),
                    consulta.getHora(),
                    consulta.getTipo());
        } catch (IOException e) {
            System.err.println("Erro ao gravar CSV: " + e.getMessage());
        }
    }
}