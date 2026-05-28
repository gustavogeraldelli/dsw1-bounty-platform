package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRelatorioDAO extends CrudRepository<Relatorio, Long> {
    Relatorio findById(long id);
    List<Relatorio> findAll();
    Relatorio save(Relatorio relatorio);
    void deleteById(long id);
    List<Relatorio> findByPesquisadorId(long pesquisadorId);
    List<Relatorio> findByProgramaId(long programaId);
    Relatorio findByPesquisadorIdAndProgramaId(long pesquisadorId, long programaId);
}
