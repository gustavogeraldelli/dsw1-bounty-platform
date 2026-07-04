package br.ufscar.dc.dsw.BugBountyClient.dto.relatorio;

import java.math.BigDecimal;

public record AvaliacaoRelatorioRequestDTO(
        String status,
        String severidade,
        BigDecimal recompensa
) {}