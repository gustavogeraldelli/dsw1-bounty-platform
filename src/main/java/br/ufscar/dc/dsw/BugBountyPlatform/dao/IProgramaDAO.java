package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProgramaDAO extends CrudRepository<Programa, Long> {
    Programa findById(long id);
    List<Programa> findAll();
    Programa save(Programa programa);
    void deleteById(long id);
    List<Programa> findByEmpresaId(long empresaId);
    List<Programa> findByEmpresaSetor(String setor);
}
