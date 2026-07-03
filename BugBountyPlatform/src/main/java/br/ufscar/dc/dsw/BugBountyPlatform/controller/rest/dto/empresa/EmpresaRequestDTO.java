package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.empresa;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;

public record EmpresaRequestDTO(
        String email,
        String senha,
        String nome,
        String cnpj,
        String setor,
        String descricao
) {
    public Empresa toEntity() {
        Empresa empresa = new Empresa();
        empresa.setEmail(this.email());
        empresa.setSenha(this.senha());
        empresa.setNome(this.nome());
        empresa.setCnpj(this.cnpj());
        empresa.setSetor(this.setor());
        empresa.setDescricao(this.descricao());
        return empresa;
    }
}