package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import java.util.List;

public interface IPesquisadorService {
    Pesquisador buscarPorId(Long id);
    List<Pesquisador> buscarTodos();
    void salvar(Pesquisador pesquisador);
    boolean excluir(Long id);
    void atualizar(Pesquisador pesquisador);
}
