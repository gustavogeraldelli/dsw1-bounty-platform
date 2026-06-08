package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class EmpresaService implements IEmpresaService {

    @Autowired
    private IEmpresaDAO empresaDAO;

    @Override
    @Transactional(readOnly = true)
    public Empresa buscarPorId(Long id) {
        return empresaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> buscarTodos() {
        return empresaDAO.findAll();
    }

    @Override
    public void salvar(Empresa empresa) {
        empresaDAO.save(empresa);
    }

    @Override
    public void excluir(Long id) {
        empresaDAO.deleteById(id);
    }
}