package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

public class NotificacaoConsole implements PortaNotificacaoTutor {

    @Override
    public void notificarAgendamento(Consulta consulta) {
        System.out.println("[CONSOLE] Agendamento confirmado!");
        System.out.println("  Tutor   : " + consulta.getAnimal().getTutor());
        System.out.println("  Animal  : " + consulta.getAnimal().getNome());
        System.out.println("  Vet     : " + consulta.getVeterinario().getNome());
        System.out.println("  Data    : " + consulta.getData() + " às " + consulta.getHora());
        System.out.println("  Tipo    : " + consulta.getTipo());
        System.out.println("----------------------------------------");
    }

    @Override
    public void notificarCancelamento(Consulta consulta) {
        System.out.println("[CONSOLE] Consulta cancelada.");
        System.out.println("  Tutor   : " + consulta.getAnimal().getTutor());
        System.out.println("  Animal  : " + consulta.getAnimal().getNome());
        System.out.println("  Data    : " + consulta.getData() + " às " + consulta.getHora());
        System.out.println("----------------------------------------");
    }
}