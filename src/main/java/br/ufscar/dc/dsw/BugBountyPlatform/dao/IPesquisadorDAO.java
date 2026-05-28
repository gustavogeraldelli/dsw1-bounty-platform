package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPesquisadorDAO extends CrudRepository<Pesquisador, Long> {
    Pesquisador findById(long id);
    List<Pesquisador> findAll();
    Pesquisador save(Pesquisador pesquisador);
    void deleteById(long id);
}
