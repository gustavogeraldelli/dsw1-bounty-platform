package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public interface IRelatorioService {
    Relatorio buscarPorId(Long id);
    List<Relatorio> buscarTodos();
    void salvar(Relatorio relatorio);
    boolean excluir(Long id);
    Relatorio buscarPorPesquisadorEPrograma(Pesquisador pesquisador, Programa programa);
    void submeter(Long pesquisadorId, Long programaId, String nomeArquivo, InputStream arquivoStream);
    void avaliar(Long relatorioId, StatusRelatorio status, Severidade severidade, BigDecimal recompensa);
}