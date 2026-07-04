package br.ufscar.dc.dsw.BugBountyClient.dto.empresa;

public record EmpresaRequestDTO(
        String email,
        String senha,
        String nome,
        String cnpj,
        String setor,
        String descricao
) {}