package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRelatorioDAO extends CrudRepository<Relatorio, Long> {
    List<Relatorio> findAll();
    List<Relatorio> findByPesquisador(Pesquisador pesquisador);
    List<Relatorio> findByPrograma(Programa programa);
    Relatorio findByPesquisadorAndPrograma(Pesquisador pesquisador, Programa programa);
}
