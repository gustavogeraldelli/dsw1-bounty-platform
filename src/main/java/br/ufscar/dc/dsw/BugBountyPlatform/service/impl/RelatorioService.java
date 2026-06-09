package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IRelatorioDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class RelatorioService implements IRelatorioService {

    @Autowired
    private IRelatorioDAO relatorioDAO;

    @Override
    @Transactional(readOnly = true)
    public Relatorio buscarPorId(Long id) {
        return relatorioDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Relatorio> buscarTodos() {
        return relatorioDAO.findAll();
    }

    @Override
    public void salvar(Relatorio relatorio) {
        relatorioDAO.save(relatorio);
    }

    @Override
    public void excluir(Long id) {
        relatorioDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Relatorio buscarPorPesquisadorEPrograma(Pesquisador pesquisador, Programa programa) {
        return relatorioDAO.findByPesquisadorAndPrograma(pesquisador, programa);
    }
}