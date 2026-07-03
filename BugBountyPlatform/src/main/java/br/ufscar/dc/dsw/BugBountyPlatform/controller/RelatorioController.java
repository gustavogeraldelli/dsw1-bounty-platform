package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IRelatorioService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private IRelatorioService relatorioService;

    @Autowired
    private IProgramaService programaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser"))
            return usuarioService.buscarPorEmail(auth.getName());
        return null;
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        Usuario usuarioLogado = getUsuarioLogado();
        if (usuarioLogado != null) {
            if (usuarioLogado.getRole().equals("ROLE_PESQUISADOR"))
                model.addAttribute("relatorios", relatorioService.buscarPorPesquisador((Pesquisador) usuarioLogado));
            else if (usuarioLogado.getRole().equals("ROLE_EMPRESA"))
                model.addAttribute("relatorios", relatorioService.buscarPorEmpresa((Empresa) usuarioLogado));
            else
                model.addAttribute("relatorios", relatorioService.buscarTodos());
        }
        return "relatorio/lista";
    }

    @GetMapping("/cadastrar")
    public String exibirFormCadastro(@RequestParam(name = "programaId", required = false) Long programaId, ModelMap model, RedirectAttributes attr) {
        if (programaId == null) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.select.program"));
            return "redirect:/programas/listar";
        }

        Programa programa = programaService.buscarPorId(programaId);
        if (programa == null) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.program.notFound"));
            return "redirect:/programas/listar";
        }

        if (programa.getDataLimite().isBefore(LocalDate.now())) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.program.closed"));
            return "redirect:/programas/detalhes/" + programa.getId();
        }

        model.addAttribute("programa", programa);
        return "relatorio/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam("file") MultipartFile file,
                            @RequestParam("programaId") Long programaId, RedirectAttributes attr) {

        Usuario usuarioLogado = getUsuarioLogado();
        if (usuarioLogado == null || !usuarioLogado.getRole().equals("ROLE_PESQUISADOR")) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.access.denied"));
            return "redirect:/relatorios/listar";
        }

        if (file.isEmpty()) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.file.empty"));
            return "redirect:/relatorios/cadastrar?programaId=" + programaId;
        }

        try {
            relatorioService.submeter(usuarioLogado.getId(), programaId, file.getOriginalFilename(), file.getInputStream());
            attr.addFlashAttribute("sucesso", getMessage("relatorio.flash.create.success"));
            return "redirect:/relatorios/listar";
        }
        catch (IllegalStateException | IllegalArgumentException e) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.create.validation"));
            return "redirect:/relatorios/cadastrar?programaId=" + programaId;
        }
        catch (Exception e) {
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.create.error"));
            return "redirect:/relatorios/cadastrar?programaId=" + programaId;
        }
    }

    @PostMapping("/avaliar")
    public String avaliar(@RequestParam("id") Long id, @RequestParam("status") StatusRelatorio status,
                          @RequestParam(value = "severidade", required = false) Severidade severidade,
                          @RequestParam(value = "recompensa", required = false) BigDecimal recompensa,
                          RedirectAttributes attr) {

        if (status == StatusRelatorio.VULNERAVEL) {
            if (severidade == null || recompensa == null || recompensa.compareTo(BigDecimal.ZERO) < 0) {
                attr.addFlashAttribute("erro", getMessage("relatorio.flash.eval.invalid"));
                return "redirect:/relatorios/listar";
            }
        }

        relatorioService.avaliar(id, status, severidade, recompensa);
        attr.addFlashAttribute("sucesso", getMessage("relatorio.flash.eval.success"));
        return "redirect:/relatorios/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (relatorioService.excluir(id))
            attr.addFlashAttribute("sucesso", getMessage("relatorio.flash.delete.success"));
        else
            attr.addFlashAttribute("erro", getMessage("relatorio.flash.delete.error"));

        return "redirect:/relatorios/listar";
    }
}