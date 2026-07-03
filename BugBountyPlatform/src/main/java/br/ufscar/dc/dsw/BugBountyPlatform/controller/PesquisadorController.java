package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
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
@RequestMapping("/pesquisadores")
public class PesquisadorController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("pesquisadores", pesquisadorService.buscarTodos());
        return "pesquisador/lista";
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model) {
        model.addAttribute("pesquisador", pesquisadorService.buscarPorId(id));
        return "pesquisador/form";
    }

    @PostMapping("/editar")
    public String editar(@Valid Pesquisador pesquisador, BindingResult result, ModelMap model, RedirectAttributes attr) {
        if (result.hasErrors())
            return "pesquisador/form";

        Usuario usuarioExistente = usuarioService.buscarPorEmail(pesquisador.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(pesquisador.getId())) {
            model.addAttribute("erro", getMessage("pesquisador.flash.email.inUse"));
            return "pesquisador/form";
        }

        try {
            pesquisadorService.atualizar(pesquisador);
            attr.addFlashAttribute("sucesso", getMessage("pesquisador.flash.update.success"));
            return "redirect:/pesquisadores/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", getMessage("pesquisador.flash.cpf.error"));
            return "pesquisador/form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (pesquisadorService.excluir(id))
            attr.addFlashAttribute("sucesso", getMessage("pesquisador.flash.delete.success"));
        else
            attr.addFlashAttribute("erro", getMessage("pesquisador.flash.delete.error"));
        return "redirect:/pesquisadores/listar";
    }
}