package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.empresa;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;

public record EmpresaResponseDTO(
        Long id,
        String email,
        String nome,
        String cnpj,
        String setor,
        String descricao
) {
    public static EmpresaResponseDTO from(Empresa empresa) {
        return new EmpresaResponseDTO(
                empresa.getId(),
                empresa.getEmail(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getSetor(),
                empresa.getDescricao()
        );
    }
}