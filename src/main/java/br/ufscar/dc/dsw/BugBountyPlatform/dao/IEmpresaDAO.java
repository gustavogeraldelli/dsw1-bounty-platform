package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEmpresaDAO extends CrudRepository<Empresa, Long> {
    Empresa findById(long id);
    List<Empresa> findAll();
    Empresa save(Empresa empresa);
    void deleteById(long id);
    List<Empresa> findBySetor(String setor);
}
