# Bug Bounty Client (T8)

Aplicação cliente construída com Spring Web e Thymeleaf. Este serviço atua exclusivamente como consumidor da API REST exposta pelo projeto principal `BugBountyPlatform` (T7).

O objetivo deste projeto é reproduzir a funcionalidade completa da plataforma original de forma desacoplada. O cliente não possui banco de dados próprio; todo o estado e regras de negócio residem no backend. Desta forma, é possível realizar o gerenciamento de empresas, pesquisadores, programas e relatórios com a mesma experiência da aplicação original, delegando toda a persistência e lógica de processamento para a API. O foco aqui é a interface de usuário e a comunicação HTTP utilizando o `RestClient` do Spring.

## Detalhes de Implementação

* **Integração REST:** Uso do `RestClient` para realizar requisições HTTP para a plataforma principal, mapeando o tráfego JSON diretamente para Records do Java (DTOs).
* **Upload de Arquivos:** Envio de relatórios contendo arquivos físicos (PDF) utilizando o padrão `multipart/form-data`.
* **Tratamento de Falhas de Rede:** O controlador intercepta quedas de conexão (ex: backend offline) e previne que a aplicação quebre (Erro 500), garantindo a renderização das telas com alertas de indisponibilidade.
* **Lógica de Apresentação:** Enquanto a API entrega os dados brutos, os controladores do cliente assumem a responsabilidade de ordenar e processar as listas antes de injetá-las no Thymeleaf.

## Execução

1. Certifique-se de que a API do projeto `BugBountyPlatform` (T7) esteja rodando localmente na porta `8080`.
2. Inicie esta aplicação. Por padrão, a configuração `server.port` está definida para `8081` para evitar conflitos.
3. Acesse `http://localhost:8081` no navegador.