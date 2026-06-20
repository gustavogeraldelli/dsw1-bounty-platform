package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/cadastrar")
    public String formCadastroPesquisador(ModelMap model) {
        model.addAttribute("pesquisador", new Pesquisador());
        return "registro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarPesquisador(@Valid Pesquisador pesquisador, BindingResult result, ModelMap model, RedirectAttributes attr) {
        if (result.hasErrors())
            return "registro";

        if (usuarioService.buscarPorEmail(pesquisador.getEmail()) != null) {
            model.addAttribute("erro", getMessage("pesquisador.flash.email.inUse"));
            return "registro";
        }

        try {
            pesquisadorService.salvar(pesquisador);
            attr.addFlashAttribute("sucesso", getMessage("pesquisador.flash.create.success"));
            return "redirect:/login";
        }
        catch (Exception e) {
            model.addAttribute("erro", getMessage("pesquisador.flash.cpf.error"));
            return "registro";
        }
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }
}