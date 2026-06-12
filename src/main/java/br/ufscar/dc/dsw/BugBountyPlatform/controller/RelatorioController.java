package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
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

import java.math.BigDecimal;

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

    @PostMapping("/submeter")
    public String submeter(
            @RequestParam("file") MultipartFile file,
            @RequestParam("pesquisadorId") Long pesquisadorId,
            @RequestParam("programaId") Long programaId,
            RedirectAttributes attr) {

        if (file.isEmpty()) {
            attr.addFlashAttribute("erro", "Por favor, anexe o arquivo PDF contendo a Prova de Conceito.");
            return "redirect:/relatorios/cadastrar";
        }

        try {
            relatorioService.submeter(pesquisadorId, programaId,
                    file.getOriginalFilename(), file.getInputStream());

            attr.addFlashAttribute("sucesso", "Prova de Conceito anexada e enviada para triagem.");
            return "redirect:/relatorios/listar";

        }
        catch (IllegalStateException | IllegalArgumentException e) {
            attr.addFlashAttribute("erro", e.getMessage());
            return "redirect:/relatorios/cadastrar";
        }
        catch (Exception e) {
            attr.addFlashAttribute("erro", "Ocorreu um erro interno no servidor ao tentar processar o upload.");
            return "redirect:/relatorios/cadastrar";
        }
    }

    @PostMapping("/avaliar")
    public String avaliar(@RequestParam("id") Long id, @RequestParam("status") StatusRelatorio status,
            @RequestParam(value = "severidade", required = false) Severidade severidade,
            @RequestParam(value = "recompensa", required = false) BigDecimal recompensa,
            RedirectAttributes attr) {

        relatorioService.avaliar(id, status, severidade, recompensa);
        attr.addFlashAttribute("sucesso", "Avaliação do relatório registrada com sucesso.");

        return "redirect:/relatorios/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (relatorioService.excluir(id))
            attr.addFlashAttribute("sucesso", "Relatório excluído com sucesso.");
        else
            attr.addFlashAttribute("erro", "Não foi possível excluir o relatório devido a restrições de integridade.");

        return "redirect:/relatorios/listar";
    }
}