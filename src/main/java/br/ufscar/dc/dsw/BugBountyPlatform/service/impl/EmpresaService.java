package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public boolean excluir(Long id) {
        try {
            empresaDAO.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e) { // falha de FK
            return false;
        }
    }

    @Override
    public void atualizar(Empresa empresaForm) {
        Empresa empresaBanco = this.buscarPorId(empresaForm.getId());

        if (empresaBanco != null) {
            empresaBanco.setNome(empresaForm.getNome());
            empresaBanco.setCnpj(empresaForm.getCnpj());
            empresaBanco.setEmail(empresaForm.getEmail());
            empresaBanco.setDescricao(empresaForm.getDescricao());
            empresaBanco.setSetor(empresaForm.getSetor());

            empresaDAO.save(empresaBanco);
        }
    }
}