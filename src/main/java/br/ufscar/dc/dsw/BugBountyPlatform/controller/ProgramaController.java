package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private IProgramaService programaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private Empresa getEmpresaLogada() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            Usuario user = usuarioService.buscarPorEmail(auth.getName());
            if (user != null && user.getRole().equals("ROLE_EMPRESA"))
                return (Empresa) user;
        }
        return null;
    }

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "busca", required = false) String busca, ModelMap model) {
        List<Programa> todos;
        Empresa empresaLogada = getEmpresaLogada();

        if (empresaLogada != null) {
            todos = programaService.buscarPorEmpresa(empresaLogada);
        }
        else {
            todos = (busca != null && !busca.trim().isEmpty())
                    ? programaService.buscarPorFiltro(busca)
                    : programaService.buscarTodos();
        }

        LocalDate hoje = LocalDate.now();
        todos.sort(Comparator.comparing((Programa p) -> p.getDataLimite().isBefore(hoje))
                .thenComparing(Programa::getDataLimite));

        model.addAttribute("programas", todos);
        return "programa/lista";
    }

    @GetMapping("/cadastrar")
    public String exibirFormCadastro(Programa programa, ModelMap model) {
        return "programa/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Programa programa, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors())
            return "programa/form";

        Empresa empresaLogada = getEmpresaLogada();
        if (empresaLogada != null) {
            programa.setEmpresa(empresaLogada);
            programaService.salvar(programa);
            attr.addFlashAttribute("sucesso", getMessage("programa.flash.create.success"));
        }
        else {
            attr.addFlashAttribute("erro", getMessage("programa.flash.access.denied"));
        }
        return "redirect:/programas/listar";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormEdicao(@PathVariable Long id, ModelMap model, RedirectAttributes attr) {
        Programa programa = programaService.buscarPorId(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Empresa empresaLogada = getEmpresaLogada();

        if (!isAdmin && (empresaLogada == null || !programa.getEmpresa().getId().equals(empresaLogada.getId()))) {
            attr.addFlashAttribute("erro", getMessage("programa.flash.access.denied.owner"));
            return "redirect:/programas/listar";
        }

        if (programa.getDataLimite().isBefore(LocalDate.now())) {
            attr.addFlashAttribute("erro", getMessage("programa.flash.edit.closed"));
            return "redirect:/programas/listar";
        }

        model.addAttribute("programa", programa);
        return "programa/form";
    }

    @PostMapping("/editar")
    public String editar(@Valid Programa programa, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors())
            return "programa/form";

        Programa progBanco = programaService.buscarPorId(programa.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Empresa empresaLogada = getEmpresaLogada();

        if (progBanco.getDataLimite().isBefore(LocalDate.now())) {
            attr.addFlashAttribute("erro", getMessage("programa.flash.update.closed"));
            return "redirect:/programas/listar";
        }

        if (isAdmin || (empresaLogada != null && progBanco.getEmpresa().getId().equals(empresaLogada.getId()))) {
            programa.setEmpresa(progBanco.getEmpresa());
            programaService.atualizar(programa);
            attr.addFlashAttribute("sucesso", getMessage("programa.flash.update.success"));
        }
        else {
            attr.addFlashAttribute("erro", getMessage("programa.flash.access.denied"));
        }
        return "redirect:/programas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        Programa programa = programaService.buscarPorId(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Empresa empresaLogada = getEmpresaLogada();

        if (!isAdmin && (empresaLogada == null || !programa.getEmpresa().getId().equals(empresaLogada.getId()))) {
            attr.addFlashAttribute("erro", getMessage("programa.flash.access.denied"));
            return "redirect:/programas/listar";
        }

        if (programaService.excluir(id))
            attr.addFlashAttribute("sucesso", getMessage("programa.flash.delete.success"));
        else
            attr.addFlashAttribute("erro", getMessage("programa.flash.delete.error"));

        return "redirect:/programas/listar";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, ModelMap model, RedirectAttributes attr) {
        Programa programa = programaService.buscarPorId(id);
        if (programa == null) {
            attr.addFlashAttribute("erro", getMessage("programa.flash.not.found"));
            return "redirect:/programas/listar";
        }
        model.addAttribute("programa", programa);
        model.addAttribute("encerrado", programa.getDataLimite().isBefore(LocalDate.now()));
        return "programa/detalhes";
    }
}