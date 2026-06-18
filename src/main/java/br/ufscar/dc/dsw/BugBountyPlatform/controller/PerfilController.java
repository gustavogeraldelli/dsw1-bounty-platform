package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PerfilController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/perfil")
    public String formEdicao(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = usuarioService.buscarPorEmail(auth.getName());
        model.addAttribute("pesquisador", pesquisadorService.buscarPorId(user.getId()));
        return "pesquisador/form";
    }

    @PostMapping("/perfil/editar")
    public String editar(Pesquisador pesquisador, ModelMap model, RedirectAttributes attr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario userLogado = usuarioService.buscarPorEmail(auth.getName());
        Pesquisador pesquisadorBanco = pesquisadorService.buscarPorId(userLogado.getId());

        pesquisador.setId(userLogado.getId());
        pesquisador.setCpf(pesquisadorBanco.getCpf());
        pesquisador.setDataNascimento(pesquisadorBanco.getDataNascimento());
        pesquisador.setSexo(pesquisadorBanco.getSexo());

        Usuario usuarioExistente = usuarioService.buscarPorEmail(pesquisador.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(userLogado.getId())) {
            model.addAttribute("erro", "Este e-mail já está em uso por outra conta.");
            return "pesquisador/form";
        }

        try {
            pesquisadorService.atualizar(pesquisador);
            attr.addFlashAttribute("sucesso", "Perfil atualizado com sucesso. (Lembre-se de utilizar o novo e-mail no próximo login).");
            return "redirect:/perfil";
        }
        catch (Exception e) {
            model.addAttribute("erro", "Erro ao atualizar o perfil. Operação cancelada.");
            return "pesquisador/form";
        }
    }
}