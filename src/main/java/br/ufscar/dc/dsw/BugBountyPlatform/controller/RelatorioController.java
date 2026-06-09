package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private IRelatorioService relatorioService;
    @Autowired
    private IPesquisadorService pesquisadorService;
    @Autowired
    private IProgramaService programaService;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("relatorios", relatorioService.buscarTodos());
        return "relatorio/lista";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(ModelMap model) {
        model.addAttribute("pesquisadores", pesquisadorService.buscarTodos());
        model.addAttribute("programas", programaService.buscarTodos());
        return "relatorio/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("pesquisadorId") Long pesquisadorId,
            @RequestParam("programaId") Long programaId,
            RedirectAttributes attr) {

        Pesquisador pesquisador = pesquisadorService.buscarPorId(pesquisadorId);
        Programa programa = programaService.buscarPorId(programaId);

        Relatorio existente = relatorioService.buscarPorPesquisadorEPrograma(pesquisador, programa);
        if (existente != null && existente.getStatus() == StatusRelatorio.EM_TRIAGEM) {
            attr.addFlashAttribute("erro", "Relatório em triagem já existente para este programa.");
            return "redirect:/relatorios/cadastrar";
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path caminhoCompleto = uploadPath.resolve(nomeArquivo);
            Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            Relatorio relatorio = new Relatorio();
            relatorio.setCaminhoArquivoPoc(caminhoCompleto.toString());
            relatorio.setStatus(StatusRelatorio.EM_TRIAGEM);
            relatorio.setDataSubmissao(LocalDateTime.now());
            relatorio.setPesquisador(pesquisador);
            relatorio.setPrograma(programa);

            relatorioService.salvar(relatorio);
            attr.addFlashAttribute("sucesso", "Prova de Conceito anexada.");

        } catch (IOException e) {
            attr.addFlashAttribute("erro", "Falha de IO no servidor ao processar o arquivo.");
            return "redirect:/relatorios/cadastrar";
        }

        return "redirect:/relatorios/listar";
    }
}