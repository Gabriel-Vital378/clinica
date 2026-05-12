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