# Bug Bounty Client (T8)

Aplicação cliente construída com Spring Web e Thymeleaf. Este serviço opera independentemente da base de dados, atuando exclusivamente como consumidor da API REST exposta pelo projeto `BugBountyPlatform`.

A comunicação HTTP é gerenciada utilizando a API nativa RestClient do Spring.

## Execução
1. Certifique-se de que a `BugBountyPlatform` (T7) esteja rodando localmente na porta `8080`.
2. Inicie esta aplicação. Por padrão, ela ocupará a porta `8081` para evitar conflitos de rede.
3. Acesse `http://localhost:8081` no navegador.