package br.ufscar.dc.dsw.BugBountyClient.dto.programa;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramaRequestDTO(
        String titulo,
        String escopo,
        BigDecimal recompensaMaxima,
        LocalDate dataLimite,
        Long empresaId
) {}