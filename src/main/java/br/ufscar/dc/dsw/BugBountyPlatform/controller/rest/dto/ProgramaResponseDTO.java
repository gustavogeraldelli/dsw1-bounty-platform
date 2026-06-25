package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramaResponseDTO(
        Long id,
        String titulo,
        String escopo,
        BigDecimal recompensaMaxima,
        LocalDate dataLimite,
        String nomeEmpresa
) {
    public static ProgramaResponseDTO from(Programa programa) {
        return new ProgramaResponseDTO(
                programa.getId(),
                programa.getTitulo(),
                programa.getEscopo(),
                programa.getRecompensaMaxima(),
                programa.getDataLimite(),
                programa.getEmpresa() != null ? programa.getEmpresa().getNome() : null
        );
    }
}