# Plataforma Bug Bounty

Sistema Web desenvolvido para a disciplina de Desenvolvimento de Sistemas Web (DSW1). A aplicação gerencia programas de recompensas por vulnerabilidades (Bug Bounty), contemplando o cadastro de empresas, pesquisadores de segurança, programas de testes e a submissão de relatórios de Prova de Conceito (PoC).

* Java 17
* Spring Boot 4
* Spring MVC e Thymeleaf
* Spring Data JPA / Hibernate
* MySQL 8.0

## Configuração e Execução do Banco de Dados

A infraestrutura de persistência está configurada para operar com o parâmetro `createDatabaseIfNotExist=true` na string de conexão JDBC e `spring.jpa.hibernate.ddl-auto=create`. Isso assegura que o banco de dados e toda a sua estrutura de tabelas sejam gerados e recriados automaticamente a cada inicialização da aplicação, provendo um ambiente de testes sempre limpo para avaliação.

A alocação do banco de dados pode ser feita de duas maneiras:

### Opção 1: Docker Compose
Navegue até o diretório onde o arquivo `docker-compose.yml` está localizado (diretório `/db`) e suba o container:
```bash
docker compose up -d
```
O serviço MySQL será exposto na porta 3306.

### Opção 2: Instalação Local
Utilize uma instância local do MySQL operando na porta 3306. Certifique-se de que as credenciais definidas em `spring.datasource.username` e `spring.datasource.password` no arquivo `application.properties` correspondam ao seu ambiente. Não é necessário executar comandos DDL para criar o schema manualmente.

## Dados Iniciais
Para facilitar a validação da atividade, a aplicação implementa a interface `CommandLineRunner` do Spring Boot. Logo após a criação das tabelas pelo Hibernate, a rotina de inicialização popula o banco de dados utilizando os próprios componentes da camada de persistência (DAOs).

**Empresas (`ROLE_EMPRESA`):**

* `security@techcorp.com` (TechCorp S.A.)
* `app@finbank.com` (FinBank)

**Pesquisadores (`ROLE_PESQUISADOR`):**

* `hacker@hunter.com` (João Silva)
* `alice@sec.com` (Alice Martins)

O rotina também registra automaticamente dois Programas atrelados às empresas e um Relatório de vulnerabilidade (PoC) com o status de operação `EM_TRIAGEM`.

## Estrutura de Módulos e Views

A interface visual utiliza o padrão de herança de templates do Thymeleaf para reaproveitamento do layout base e importações (Bootstrap e scripts).

* **Empresas (`/empresas`):** Módulo para gerenciamento de entidades corporativas. Contém listagem geral com validação visual de ausência de registros, além de formulário dinâmico para cadastro e edição.
* **Pesquisadores (`/pesquisadores`):** Gerenciamento de perfil dos usuários pesquisadores. A view de cadastro aplica máscaras via JavaScript para formatação de inputs (CPF, Telefone) no lado do cliente.
* **Programas (`/programas`):** Painel para definição de alvos e recompensas. A interface de cadastro extrai e injeta dinamicamente as empresas disponíveis no banco para mapear a associação relacional do programa.
* **Relatórios (`/relatorios`):** Módulo de submissão de vulnerabilidades. A view de cadastro expõe um formulário `multipart/form-data`, exigindo o envio físico de um arquivo PDF de evidência e bloqueando submissões em caso de falha nas regras de negócio da triagem.
