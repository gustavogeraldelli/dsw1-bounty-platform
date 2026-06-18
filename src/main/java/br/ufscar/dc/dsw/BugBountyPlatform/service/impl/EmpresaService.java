package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.dao.IProgramaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class EmpresaService implements IEmpresaService {

    @Autowired
    private IEmpresaDAO empresaDAO;

    @Autowired
    private IProgramaDAO programaDAO;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
        empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
        empresa.setRole("ROLE_EMPRESA");
        empresaDAO.save(empresa);
    }

    @Override
    public boolean excluir(Long id) {
        Empresa empresa = this.buscarPorId(id);
        if (empresa == null) return false;

        if (!programaDAO.findByEmpresa(empresa).isEmpty()) {
            return false;
        }

        empresaDAO.deleteById(id);
        return true;
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