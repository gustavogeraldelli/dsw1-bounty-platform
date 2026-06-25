# Testes da API REST

Este diretório contém a coleção de testes do Postman para a API desenvolvida na etapa T7. O arquivo JSON já possui as requisições configuradas com o endereço local na porta 8080, bastando importá-lo no Postman para executar as validações.

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