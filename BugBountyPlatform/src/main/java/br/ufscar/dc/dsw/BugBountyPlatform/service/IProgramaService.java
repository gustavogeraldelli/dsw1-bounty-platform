package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;

import java.util.List;

public interface IProgramaService {
    Programa buscarPorId(Long id);
    List<Programa> buscarTodos();
    void salvar(Programa programa);
    boolean excluir(Long id);
    List<Programa> buscarPorFiltro(String termo);
    void atualizar(Programa programa);
    List<Programa> buscarPorEmpresa(Empresa empresa);
}