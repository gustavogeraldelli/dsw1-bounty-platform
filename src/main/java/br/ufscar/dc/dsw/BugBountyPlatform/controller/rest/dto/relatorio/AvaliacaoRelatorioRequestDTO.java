package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.relatorio;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import java.math.BigDecimal;

public record AvaliacaoRelatorioRequestDTO(
        StatusRelatorio status,
        Severidade severidade,
        BigDecimal recompensa
) {}