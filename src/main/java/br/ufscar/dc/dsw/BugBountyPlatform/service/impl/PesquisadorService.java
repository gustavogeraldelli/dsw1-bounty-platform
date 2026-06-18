package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IPesquisadorDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.dao.IRelatorioDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class PesquisadorService implements IPesquisadorService {

    @Autowired
    private IPesquisadorDAO pesquisadorDAO;

    @Autowired
    private IRelatorioDAO relatorioDAO;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
        pesquisador.setSenha(passwordEncoder.encode(pesquisador.getSenha()));
        pesquisador.setRole("ROLE_PESQUISADOR");
        pesquisadorDAO.save(pesquisador);
    }

    @Override
    public boolean excluir(Long id) {
        Pesquisador pesquisador = this.buscarPorId(id);
        if (pesquisador == null) return false;

        if (!relatorioDAO.findByPesquisador(pesquisador).isEmpty()) {
            return false;
        }

        pesquisadorDAO.deleteById(id);
        return true;
    }

    @Override
    public void atualizar(Pesquisador pesquisadorForm) {
        Pesquisador pesquisadorBanco = this.buscarPorId(pesquisadorForm.getId());
        if (pesquisadorBanco != null) {
            pesquisadorBanco.setNome(pesquisadorForm.getNome());
            pesquisadorBanco.setCpf(pesquisadorForm.getCpf());
            pesquisadorBanco.setEmail(pesquisadorForm.getEmail());
            pesquisadorBanco.setTelefone(pesquisadorForm.getTelefone());
            pesquisadorBanco.setSexo(pesquisadorForm.getSexo());
            pesquisadorBanco.setDataNascimento(pesquisadorForm.getDataNascimento());
            pesquisadorDAO.save(pesquisadorBanco);
        }
    }
}