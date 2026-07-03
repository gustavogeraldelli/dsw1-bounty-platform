package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private IEmpresaService empresaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "empresa/lista";
    }

    @GetMapping("/cadastrar")
    public String formCadastro(Empresa empresa) {
        return "empresa/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Empresa empresa, BindingResult result, ModelMap model, RedirectAttributes attr) {
        if (result.hasErrors())
            return "empresa/form";

        if (usuarioService.buscarPorEmail(empresa.getEmail()) != null) {
            model.addAttribute("erro", getMessage("empresa.flash.email.inUse"));
            return "empresa/form";
        }

        try {
            empresaService.salvar(empresa);
            attr.addFlashAttribute("sucesso", getMessage("empresa.flash.create.success"));
            return "redirect:/empresas/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", getMessage("empresa.flash.create.error"));
            return "empresa/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/form";
    }

    @PostMapping("/editar")
    public String editar(@Valid Empresa empresa, BindingResult result, ModelMap model, RedirectAttributes attr) {
        if (result.hasErrors())
            return "empresa/form";

        Usuario usuarioExistente = usuarioService.buscarPorEmail(empresa.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(empresa.getId())) {
            model.addAttribute("erro", getMessage("empresa.flash.email.inUse"));
            return "empresa/form";
        }

        try {
            empresaService.atualizar(empresa);
            attr.addFlashAttribute("sucesso", getMessage("empresa.flash.update.success"));
            return "redirect:/empresas/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", getMessage("empresa.flash.update.error"));
            return "empresa/form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (empresaService.excluir(id))
            attr.addFlashAttribute("sucesso", getMessage("empresa.flash.delete.success"));
        else
            attr.addFlashAttribute("erro", getMessage("empresa.flash.delete.error"));

        return "redirect:/empresas/listar";
    }
}