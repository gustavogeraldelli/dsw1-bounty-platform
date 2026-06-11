package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IProgramaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class ProgramaService implements IProgramaService {

    @Autowired
    private IProgramaDAO programaDAO;

    @Override
    @Transactional(readOnly = true)
    public Programa buscarPorId(Long id) {
        return programaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Programa> buscarTodos() {
        return programaDAO.findAll();
    }

    @Override
    public void salvar(Programa programa) {
        programaDAO.save(programa);
    }

    @Override
    public boolean excluir(Long id) {
        try {
            programaDAO.deleteById(id);
            return true;
        }
        catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public List<Programa> buscarPorSetor(String setor) {
        return programaDAO.findByEmpresaSetorContainingIgnoreCase(setor);
    }

    @Override
    public void atualizar(Programa programaForm) {
        Programa programaBanco = this.buscarPorId(programaForm.getId());

        if (programaBanco != null) {
            programaBanco.setTitulo(programaForm.getTitulo());
            programaBanco.setEscopo(programaForm.getEscopo());
            programaBanco.setRecompensaMaxima(programaForm.getRecompensaMaxima());
            programaBanco.setDataLimite(programaForm.getDataLimite());
            programaBanco.setEmpresa(programaForm.getEmpresa()); // Caso a edição permita trocar a empresa

            programaDAO.save(programaBanco);
        }
    }
}