# Plataforma Bug Bounty

Repositório central das entregas da disciplina de Desenvolvimento de Software para Web 1. A documentação do sistema está dividida por etapas de entrega. Clique nos títulos abaixo para expandir os detalhes técnicos e as instruções de execução.

<details>
<summary><h3>Relatório Entrega T6 (Spring MVC e JPA)</h3></summary>

#### Visão Geral
Sistema desenvolvido para a disciplina de Desenvolvimento de Sistemas Web (DSW1). A aplicação gerencia programas de recompensas por vulnerabilidades, estabelecendo o fluxo de submissão e avaliação de relatórios entre empresas e pesquisadores de segurança.

#### Tecnologias Utilizadas
* Linguagem: Java 17
* Framework: Spring Boot 3
* Arquitetura: MVC com Spring Web MVC e Thymeleaf
* Persistência: Spring Data JPA e Hibernate
* Banco de Dados: MySQL 8.0
* Segurança: Spring Security (Criptografia BCrypt e controle de acesso por roles)
* Validação: Jakarta Bean Validation (JSR 380)

#### Modelo de Domínio e Relacionamentos
A arquitetura de banco de dados utiliza a estratégia de herança `JOINED` para unificar a autenticação na superclasse, mantendo os dados específicos isolados em suas respectivas tabelas.

1. **Usuario** (Superclasse)
   Classe base abstrata de autenticação. Implementa a interface `UserDetails` do Spring Security. Responsável por armazenar `email`, `senha` e `role`.
2. **Subclasses** (Admin, Empresa e Pesquisador)
   * Admin: Usuário com permissões globais no sistema.
   * Empresa: Organização que publica os testes. Contém `nome`, `cnpj`, `setor` e `descricao`. Relacionamento: Uma empresa gerencia múltiplos objetos `Programa`.
   * Pesquisador: Usuário que realiza as análises. Contém `cpf`, `telefone`, `dataNascimento` e `sexo`. Relacionamento: Um pesquisador submete múltiplos objetos `Relatorio`.

3. **Programa**
   Define o escopo de teste autorizado pela empresa.
   * Possui `dataLimite`. O sistema impõe bloqueios lógicos para edições ou novas submissões após o vencimento, mantendo a integridade de relatórios passados.
   * Contém a `recompensaMaxima` e o `escopo` (alvos permitidos).
   * Relacionamento: Pertence a uma `Empresa` e recebe múltiplos objetos `Relatorio`.

4. **Relatorio** (Entidade Associativa)
   Entidade transacional que mapeia a submissão de uma Prova de Conceito (PoC), unindo um `Pesquisador` a um `Programa`.
   * Armazena o `caminhoArquivoPoc` referente ao arquivo PDF carregado.
   * O ciclo de vida é controlado de forma estrita pelo enum `StatusRelatorio` (EM_TRIAGEM, VULNERAVEL, REJEITADO, DUPLICADO).
   * A transição para o status VULNERAVEL exige mandatoriamente o registro do enum `Severidade` e do valor numérico da `recompensa`.

#### Fluxo Geral de Negócio
1. Uma entidade do tipo `Empresa` realiza o cadastro de um `Programa`, definindo regras de alvo e estipulando um prazo limite.
2. Um `Pesquisador` autenticado consulta a listagem de programas ativos.
3. O `Pesquisador` submete um `Relatorio` referente a um programa, enviando um arquivo PDF. O status de entrada padrão é EM_TRIAGEM.
4. A `Empresa` responsável realiza o download da evidência e conclui a avaliação. A aprovação exige a definição de severidade e pagamento, enquanto avaliações negativas alteram o status para rejeitado ou duplicado.

#### Execução e Configuração
O esquema de banco de dados é gerado dinamicamente através do parâmetro `spring.jpa.hibernate.ddl-auto=create`.

1. Inicie o serviço do MySQL operando na porta 3306.
2. Certifique-se de que os valores `spring.datasource.username` e `spring.datasource.password` no arquivo `application.properties` correspondam às credenciais da sua instância local. A aplicação requer a pré-existência de um schema vazio chamado `dsw1_bounty`.
3. Para iniciar a aplicação, você pode compilar e executar a classe principal `BugBountyPlatformApplication` através de sua IDE, ou utilizar o terminal na raiz do projeto executando o comando Maven `mvn spring-boot:run`

#### Dados de Inicialização
A aplicação utiliza a interface `CommandLineRunner` para executar operações de DML e injetar dados padronizados no banco após a criação das tabelas, viabilizando o ambiente de teste.

**Contas Administrativas (ROLE_ADMIN)**
* admin@admin.com (senha: admin)

**Contas de Empresa (ROLE_EMPRESA)**
* sec@tech.com (senha: 123) - Possui vínculo com um programa ativo e um encerrado.
* app@finbank.com (senha: 123) - Possui vínculo exclusivo com um programa ativo.
* sec@startup.com (senha: 123) - Entidade livre de chaves estrangeiras.

**Contas de Pesquisador (ROLE_PESQUISADOR)**
* hack@hack.com (senha: 123) - Possui relatórios em status de triagem e rejeitados.
* alice@sec.com (senha: 123) - Possui relatórios validados com atribuição de recompensa.
* carlos@hacker.com (senha: 123) - Cadastro limpo, sem submissões.

#### Detalhes de Implementação
* **Internacionalização (i18n):** O sistema possui integração de idioma para Português (PT) e Inglês (EN). A camada de propriedades abrange o frontend (Thymeleaf), respostas do controlador via Flash Attributes e anotações do Bean Validation. O gerenciamento de estado da linguagem é assegurado por `SessionLocaleResolver`.
* **Controle de Acesso:** Os endpoints que processam modificações de estado (PUT/DELETE lógicos) implementam validações na camada de controlador utilizando o `SecurityContextHolder`, rejeitando operações se a identidade logada não corresponder ao proprietário do recurso.
* **Processamento de Inputs:** O frontend emprega rotinas JavaScript para aplicar máscaras de formatação em formulários. Eventos de *submit* são interceptados para higienizar os campos, submetendo apenas cadeias numéricas puras ao backend, garantindo que as validações de algoritmos matemáticos do domínio operem corretamente.

</details>

<details>
<summary><h3>Relatório Entrega T7 (API REST)</h3></summary>

A etapa T7 introduz a camada de serviços web ao projeto, disponibilizando uma API REST para a entidade Programa sob a rota principal `/api/programas`.

O controlador responsável foi isolado no pacote `controller.rest`, separando o tráfego de dados via JSON dos controladores tradicionais que renderizam as views do Thymeleaf. Para otimizar a comunicação e não enviar dados desnecessários pela rede, a aplicação utiliza Records do Java como Data Transfer Objects (DTOs), formatando apenas as informações exatas que o cliente precisa receber ou enviar.

Além disso, o filtro do Spring Security foi ajustado para liberar o tráfego e desabilitar a proteção CSRF especificamente para o escopo desta API.

Para a execução e avaliação dos endpoints, acesse o **[diretório /api](./api)** na raiz deste repositório. Lá você encontrará:
* A coleção do Postman (`.json`) configurada para a validação automatizada das requisições.
* O arquivo `README.md` interno detalhando o mapeamento dos testes, que cobrem o fluxo padrão de CRUD e as respostas de exceç

</details>