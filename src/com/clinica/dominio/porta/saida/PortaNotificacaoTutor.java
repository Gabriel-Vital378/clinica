package com.clinica.dominio.porta.saida;

import com.clinica.dominio.modelo.Consulta;

public interface PortaNotificacaoTutor {
    void notificarAgendamento(Consulta consulta);
    void notificarCancelamento(Consulta consulta);
}