package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IPesquisadorDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class PesquisadorService implements IPesquisadorService {

    @Autowired
    private IPesquisadorDAO pesquisadorDAO;

    @Override
    @Transactional(readOnly = true)
    public Pesquisador buscarPorId(Long id) {
        return pesquisadorDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pesquisador> buscarTodos() {
        return pesquisadorDAO.findAll();
    }

    @Override
    public void salvar(Pesquisador pesquisador) {
        pesquisadorDAO.save(pesquisador);
    }

    @Override
    public void excluir(Long id) {
        pesquisadorDAO.deleteById(id);
    }
}