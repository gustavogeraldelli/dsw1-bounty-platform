package br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador;

import java.time.LocalDate;

public record PesquisadorResponseDTO(
        Long id,
        String email,
        String cpf,
        String nome,
        String telefone,
        String sexo,
        LocalDate dataNascimento
) {}
