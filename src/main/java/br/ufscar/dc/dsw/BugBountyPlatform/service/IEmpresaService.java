package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import java.util.List;

public interface IEmpresaService {
    Empresa buscarPorId(Long id);
    List<Empresa> buscarTodos();
    void salvar(Empresa empresa);
    void excluir(Long id);
}
