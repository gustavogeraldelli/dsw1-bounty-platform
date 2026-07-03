package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.relatorio;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RelatorioResponseDTO(
        Long id,
        StatusRelatorio status,
        LocalDateTime dataSubmissao,
        Severidade severidade,
        BigDecimal recompensa,
        String pesquisadorNome,
        String pesquisadorEmail,
        String programaTitulo,
        String empresaNome,
        String caminhoArquivoPoc
) {
    public static RelatorioResponseDTO from(Relatorio relatorio) {
        return new RelatorioResponseDTO(
                relatorio.getId(),
                relatorio.getStatus(),
                relatorio.getDataSubmissao(),
                relatorio.getSeveridade(),
                relatorio.getRecompensa(),
                relatorio.getPesquisador().getNome(),
                relatorio.getPesquisador().getEmail(),
                relatorio.getPrograma().getTitulo(),
                relatorio.getPrograma().getEmpresa().getNome(),
                relatorio.getCaminhoArquivoPoc()
        );
    }
}