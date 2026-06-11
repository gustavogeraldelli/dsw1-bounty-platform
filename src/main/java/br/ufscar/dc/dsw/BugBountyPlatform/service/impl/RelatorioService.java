package br.ufscar.dc.dsw.BugBountyPlatform.service.impl;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IRelatorioDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class RelatorioService implements IRelatorioService {

    @Autowired
    private IRelatorioDAO relatorioDAO;

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IProgramaService programaService;

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
    public boolean excluir(Long id) {
        Relatorio relatorio = this.buscarPorId(id);
        if (relatorio == null) {
            return false;
        }

        String caminhoArquivo = relatorio.getCaminhoArquivoPoc();
        try {
            relatorioDAO.deleteById(id);

            if (caminhoArquivo != null && !caminhoArquivo.trim().isEmpty()) {
                Path path = Paths.get(caminhoArquivo);
                Files.deleteIfExists(path);
            }

            return true;
        }
        catch (DataIntegrityViolationException e) {
            return false;
        }
        catch (IOException e) {
            System.err.println("Erro na remoção do documento: " + e.getMessage());
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Relatorio buscarPorPesquisadorEPrograma(Pesquisador pesquisador, Programa programa) {
        return relatorioDAO.findByPesquisadorAndPrograma(pesquisador, programa);
    }

    @Override
    public void submeter(Long pesquisadorId, Long programaId, String nomeArquivo, InputStream arquivoStream) {
        Pesquisador pesquisador = pesquisadorService.buscarPorId(pesquisadorId);
        Programa programa = programaService.buscarPorId(programaId);
        if (pesquisador == null || programa == null) {
            throw new IllegalArgumentException("Pesquisador ou Programa não localizado no sistema.");
        }

        Relatorio existente = relatorioDAO.findByPesquisadorAndPrograma(pesquisador, programa);
        if (existente != null && existente.getStatus() == StatusRelatorio.EM_TRIAGEM) {
            throw new IllegalStateException("Relatório em triagem já existente para este programa.");
        }

        try {
            Path uploadPath = Paths.get("uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomeArquivoFinal = System.currentTimeMillis() + "_" + nomeArquivo;
            Path caminhoCompleto = uploadPath.resolve(nomeArquivoFinal);

            Files.copy(arquivoStream, caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            Relatorio relatorio = new Relatorio();
            relatorio.setCaminhoArquivoPoc(caminhoCompleto.toString());
            relatorio.setStatus(StatusRelatorio.EM_TRIAGEM);
            relatorio.setDataSubmissao(LocalDateTime.now());
            relatorio.setPesquisador(pesquisador);
            relatorio.setPrograma(programa);

            relatorioDAO.save(relatorio);

        }
        catch (IOException e) {
            throw new RuntimeException("Falha de IO no servidor ao processar o arquivo", e);
        }
    }
}