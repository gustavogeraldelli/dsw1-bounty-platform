package br.ufscar.dc.dsw.BugBountyClient.dto.programa;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramaResponseDTO(
        Long id,
        String titulo,
        String escopo,
        BigDecimal recompensaMaxima,
        LocalDate dataLimite,
        Long empresaId,
        String nomeEmpresa
) {}