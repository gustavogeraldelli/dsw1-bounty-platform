package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.pesquisador;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import java.time.LocalDate;

public record PesquisadorRequestDTO(
        String email,
        String senha,
        String cpf,
        String nome,
        String telefone,
        String sexo,
        LocalDate dataNascimento
) {
    public Pesquisador toEntity() {
        Pesquisador pesquisador = new Pesquisador();
        pesquisador.setEmail(this.email());
        pesquisador.setSenha(this.senha());
        pesquisador.setCpf(this.cpf());
        pesquisador.setNome(this.nome());
        pesquisador.setTelefone(this.telefone());
        pesquisador.setSexo(this.sexo());
        pesquisador.setDataNascimento(this.dataNascimento());
        return pesquisador;
    }
}