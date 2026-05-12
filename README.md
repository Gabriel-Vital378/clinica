# Clínica Veterinária — Arquitetura Hexagonal

## Compilação e Execução

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out com.clinica.apresentacao.Main
```

## Estrutura de Pacotes
com.clinica/
├── dominio/
│   ├── modelo/
│   ├── excecao/
│   ├── porta/entrada/
│   ├── porta/saida/
│   └── servico/
└── infraestrutura/adaptador/
├── persistencia/
└── notificacao/

## Decisões Arquiteturais

- Portas de entrada definem os casos de uso do sistema.
- Portas de saída definem os contratos com repositórios e notificações.
- Adaptadores implementam as portas sem que o domínio saiba da sua existência.
- A troca de NotificacaoConsole por NotificacaoCsv ocorre exclusivamente na Main,
  sem alterar nenhuma linha do domínio.
  É só preencher com as portas que foram implementadas. Substitui no README.md:
markdown## Portas Implementadas

### Portas de Entrada
- **PortaAgendaConsulta** — define os casos de uso: agendar, realizar e cancelar consulta, além de consultar histórico e agenda

### Portas de Saída
- **PortaAnimalRepositorio** — contrato para persistência de animais
- **PortaVeterinarioRepositorio** — contrato para persistência de veterinários
- **PortaConsultaRepositorio** — contrato para persistência de consultas
- **PortaNotificacaoTutor** — contrato para envio de notificações ao tutor

### Adaptadores Implementados
- **AnimalRepositorioMemoria** — implementa PortaAnimalRepositorio usando HashMap
- **VeterinarioRepositorioMemoria** — implementa PortaVeterinarioRepositorio usando HashMap
- **ConsultaRepositorioMemoria** — implementa PortaConsultaRepositorio usando HashMap
- **NotificacaoConsole** — implementa PortaNotificacaoTutor exibindo no terminal
- **NotificacaoCsv** — implementa PortaNotificacaoTutor gravando em notificacoes.csv