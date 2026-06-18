package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProgramaDAO extends CrudRepository<Programa, Long> {
    List<Programa> findAll();
    List<Programa> findByEmpresa(Empresa empresa);
    // SELECT p.* FROM programa p JOIN empresa e ON p.empresa_id = e.id WHERE LOWER(e.setor) LIKE LOWER('%texto%');
    List<Programa> findByEmpresaNomeContainingIgnoreCaseOrEmpresaSetorContainingIgnoreCase(String nome, String setor);
}
