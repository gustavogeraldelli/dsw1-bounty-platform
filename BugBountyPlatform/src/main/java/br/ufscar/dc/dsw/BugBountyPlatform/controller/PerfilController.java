package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PerfilController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/perfil")
    public String formEdicaoPerfilPesquisador(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = usuarioService.buscarPorEmail(auth.getName());
        model.addAttribute("pesquisador", pesquisadorService.buscarPorId(user.getId()));
        return "pesquisador/form";
    }

    @PostMapping("/perfil/editar")
    public String editar(@Valid Pesquisador pesquisador, BindingResult result, ModelMap model, RedirectAttributes attr) {
        if (result.hasErrors())
            return "pesquisador/form";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario userLogado = usuarioService.buscarPorEmail(auth.getName());
        Pesquisador pesquisadorBanco = pesquisadorService.buscarPorId(userLogado.getId());

        pesquisador.setId(userLogado.getId());
        pesquisador.setCpf(pesquisadorBanco.getCpf());
        pesquisador.setDataNascimento(pesquisadorBanco.getDataNascimento());
        pesquisador.setSexo(pesquisadorBanco.getSexo());

        Usuario usuarioExistente = usuarioService.buscarPorEmail(pesquisador.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(userLogado.getId())) {
            model.addAttribute("erro", getMessage("pesquisador.flash.email.inUse"));
            return "pesquisador/form";
        }

        try {
            pesquisadorService.atualizar(pesquisador);
            attr.addFlashAttribute("sucesso", getMessage("pesquisador.flash.profile.update.success"));
            return "redirect:/perfil";
        }
        catch (Exception e) {
            model.addAttribute("erro", getMessage("pesquisador.flash.profile.update.error"));
            return "pesquisador/form";
        }
    }
}