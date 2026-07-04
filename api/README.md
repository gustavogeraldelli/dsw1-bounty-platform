# Testes da API REST

Este diretório contém a coleção de testes do Postman para a API desenvolvida na etapa T7. O arquivo JSON já possui as requisições configuradas com o endereço local na porta 8080, bastando importá-lo no Postman para executar as validações.

## Pesquisadores
### GET /api/pesquisadores
O teste `Listar todos os pesquisadores` realiza uma requisição de listagem geral para retornar todos os pesquisadores cadastrados.

### GET /api/pesquisadores/{id}
O teste `Buscar pesquisador por ID` solicita os dados de um único pesquisador. O teste `404 Buscar Pesquisador inexistente` valida o retorno de Not Found para IDs que não existem.

### POST /api/pesquisadores
O teste `Cadastrar novo pesquisador` envia os dados no corpo da requisição, esperando a persistência e o status de criado.

### PUT /api/pesquisadores/{id}
O teste `Atualizar pesquisador` altera os dados de um registro existente.

### DELETE /api/pesquisadores/{id}
O teste `Excluir pesquisador` remove um pesquisador sem dependências. O teste `409 Excluir Pesquisador com Relatório associado` tenta remover um pesquisador que já possui relatórios submetidos, validando o bloqueio da operação com o status Conflict.

---

## Empresas
### GET /api/empresas
O teste `Listar todas as empresas` realiza uma requisição de listagem geral para retornar todas as empresas cadastradas.

### GET /api/empresas/{id}
O teste `Buscar empresa por ID` solicita os dados de uma única empresa. O teste `404 Buscar Empresa inexistente` valida o retorno de Not Found para IDs que não existem.

### POST /api/empresas
O teste `Cadastrar nova empresa` envia os dados no corpo da requisição, esperando a persistência e o status de criado.

### PUT /api/empresas/{id}
O teste `Atualizar empresa` sobrescreve as informações de uma empresa existente.

### DELETE /api/empresas/{id}
O teste `Excluir empresa` remove uma empresa que não possui programas. O teste `409 Excluir Empresa com Programa associado` tenta remover uma empresa que possui programas vinculados, validando que a operação é bloqueada com o status Conflict.

---

## Programas
### GET /api/programas
O teste `Listar todos os programas` realiza uma requisição de listagem geral para confirmar o retorno de todos os registros cadastrados no banco de dados.

### GET /api/programas/{id}
O teste `Buscar programa por ID` solicita os dados de um único registro. Para validar o tratamento de erros, o teste `404 Buscar ID inexistente` faz uma requisição passando um ID que não está no banco, garantindo que a aplicação retorne o status Not Found.

### POST /api/programas
O teste `Cadastrar novo programa` envia os dados de um registro e o ID da empresa no corpo da requisição, esperando a persistência no banco e o status de criado. O teste `400 Cadastrar com Empresa inexistente` valida a integridade referencial ao enviar a requisição com o ID de empresa 999, confirmando o bloqueio da operação e o erro Bad Request.

### PUT /api/programas/{id}
O teste `Atualizar programa` altera um registro existente enviando as novas informações no corpo da requisição para sobrescrever os dados atuais.

### DELETE /api/programas/{id}
O teste `Excluir programa` remove um registro com sucesso, sem retornar conteúdo. Para validar a proteção de chaves estrangeiras, o teste `409 Excluir Programa com Relatório associado` tenta deletar um programa que já possui relatórios vinculados no banco, confirmando que a operação é bloqueada e retorna Conflict.

---

## Relatórios
### GET /api/relatorios
O teste `Relatório - Listar todos os relatórios` realiza uma requisição de listagem geral para recuperar o histórico completo de todas as submissões de vulnerabilidades cadastradas.

### GET /api/relatorios/{id}
O teste `Relatório - Buscar relatório por ID` solicita os dados detalhados de um relatório específico através do seu identificador único.

### POST /api/relatorios
O teste `Relatório - Submeter relatório` simula o envio de uma nova submissão de vulnerabilidade (PoC). Os dados são enviados no formato `multipart/form-data`, contendo o ID do pesquisador, o ID do programa e o arquivo físico (PDF), esperando a criação do registro e o status Created.

### PUT /api/relatorios/{id}/avaliar
O teste `Relatório - Avaliar relatório` altera o status de uma submissão existente. Envia os dados da avaliação (`status`, `severidade` e `recompensa`) no formato JSON no corpo da requisição para processar a aprovação (VULNERAVEL) ou a rejeição (REJEITADO/DUPLICADO) do relatório submetido.