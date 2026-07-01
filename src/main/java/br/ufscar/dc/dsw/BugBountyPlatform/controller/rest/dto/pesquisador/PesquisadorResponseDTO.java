package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.pesquisador;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import java.time.LocalDate;

public record PesquisadorResponseDTO(
        Long id,
        String email,
        String cpf,
        String nome,
        String telefone,
        String sexo,
        LocalDate dataNascimento
) {
    public static PesquisadorResponseDTO from(Pesquisador pesquisador) {
        return new PesquisadorResponseDTO(
                pesquisador.getId(),
                pesquisador.getEmail(),
                pesquisador.getCpf(),
                pesquisador.getNome(),
                pesquisador.getTelefone(),
                pesquisador.getSexo(),
                pesquisador.getDataNascimento()
        );
    }
}