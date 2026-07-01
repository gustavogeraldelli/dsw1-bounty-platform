package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.programa;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgramaRequestDTO(
        String titulo,
        String escopo,
        BigDecimal recompensaMaxima,
        LocalDate dataLimite,
        Long empresaId
) {
    public Programa toEntity() {
        Programa programa = new Programa();
        programa.setTitulo(this.titulo());
        programa.setEscopo(this.escopo());
        programa.setRecompensaMaxima(this.recompensaMaxima());
        programa.setDataLimite(this.dataLimite());
        return programa;
    }
}