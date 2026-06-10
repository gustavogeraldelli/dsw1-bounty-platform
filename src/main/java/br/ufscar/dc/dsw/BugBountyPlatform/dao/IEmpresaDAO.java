package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEmpresaDAO extends CrudRepository<Empresa, Long> {
    List<Empresa> findAll();
    List<Empresa> findBySetor(String setor);
    List<Empresa> findBySetorContainingIgnoreCase(String setor);
}
