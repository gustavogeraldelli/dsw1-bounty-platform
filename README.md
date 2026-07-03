# Plataforma Bug Bounty

Repositório central contendo os projetos desenvolvidos para a disciplina de Desenvolvimento de Software para Web 1. A arquitetura está dividida em módulos independentes para separar a API do cliente que a consome.

Abaixo está o índice de navegação mapeado pelas etapas de entrega da disciplina:

### Entregas T6 e T7: Backend e API REST [[diretório BugBountyPlatform]](./BugBountyPlatform)
Este módulo contém a aplicação principal, gerenciando o banco de dados e expondo os serviços web.
* **T6 (Spring MVC e JPA):** Implementação inicial contendo o modelo de domínio, persistência com Hibernate/MySQL, segurança com Spring Security e interface web MVC básica.
* **T7 (API REST):** Disponibilização dos endpoints REST para as entidades do sistema (Programa, Empresa, Pesquisador e Relatório), isolando o tráfego de dados via JSON e implementando upload de arquivos (PoC).

A documentação completa da arquitetura, modelo de domínio e regras de negócio está no [README interno da plataforma](./BugBountyPlatform/README.md).

---

### Testes da API REST (Complemento T7) [[diretório api/]](./api)
Este diretório contém os artefatos de teste para a API desenvolvida na etapa T7.
* Coleção do Postman (`.json`) para validação automatizada dos endpoints.
* Documentação mapeando os cenários de sucesso (CRUD padrão) e tratamento de exceções (erros 400, 404 e 409).

---

### Entrega T8: Cliente REST API [[diretório BugBountyClient/]](./BugBountyClient)
Aplicação web construída para atuar exclusivamente como cliente consumidor da API REST desenvolvida na T7.
* **T8 (Cliente REST):** Implementado com Spring Web, RestClient e Thymeleaf. O projeto opera de forma independente (na porta 8081) e não possui conexão direta com o banco de dados, realizando as operações de CRUD das entidades via chamadas HTTP para o backend principal.