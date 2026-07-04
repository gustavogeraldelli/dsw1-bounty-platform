package br.ufscar.dc.dsw.BugBountyClient.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RelatorioResponseDTO(
        Long id,
        String status,
        LocalDateTime dataSubmissao,
        String severidade,
        BigDecimal recompensa,
        String pesquisadorNome,
        String pesquisadorEmail,
        String programaTitulo,
        String empresaNome,
        String caminhoArquivoPoc
) {}