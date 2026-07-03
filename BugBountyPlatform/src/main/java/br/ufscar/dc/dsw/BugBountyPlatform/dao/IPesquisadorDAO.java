package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPesquisadorDAO extends CrudRepository<Pesquisador, Long> {
    List<Pesquisador> findAll();
}
