package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import java.util.List;

public interface IRelatorioService {
    Relatorio buscarPorId(Long id);
    List<Relatorio> buscarTodos();
    void salvar(Relatorio relatorio);
    void excluir(Long id);
    Relatorio buscarPorPesquisadorEPrograma(Pesquisador pesquisador, Programa programa);
}