package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;

import java.util.List;

public interface IProgramaService {
    Programa buscarPorId(Long id);
    List<Programa> buscarTodos();
    void salvar(Programa programa);
    void excluir(Long id);
    List<Programa> buscarPorSetor(String setor);
}