package br.ufscar.dc.dsw.BugBountyClient.dto.empresa;

public record EmpresaResponseDTO(
        Long id,
        String email,
        String nome,
        String cnpj,
        String setor,
        String descricao
) {}