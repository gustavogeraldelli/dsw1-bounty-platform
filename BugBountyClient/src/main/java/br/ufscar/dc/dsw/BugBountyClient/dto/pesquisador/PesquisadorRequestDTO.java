package br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador;

import java.time.LocalDate;

public record PesquisadorRequestDTO(
        String email,
        String senha,
        String cpf,
        String nome,
        String telefone,
        String sexo,
        LocalDate dataNascimento
) {}