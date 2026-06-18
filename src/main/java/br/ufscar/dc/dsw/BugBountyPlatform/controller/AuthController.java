package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/cadastrar")
    public String formCadastro(ModelMap model) {
        model.addAttribute("pesquisador", new Pesquisador());
        return "registro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Pesquisador pesquisador, ModelMap model, RedirectAttributes attr) {
        if (usuarioService.buscarPorEmail(pesquisador.getEmail()) != null) {
            model.addAttribute("erro", "Este e-mail já está em uso por outra conta.");
            return "registro";
        }

        try {
            pesquisadorService.salvar(pesquisador);
            attr.addFlashAttribute("sucesso", "Conta criada com sucesso. Agora você pode entrar.");
            return "redirect:/login";
        }
        catch (Exception e) {
            model.addAttribute("erro", "Não foi possível criar a conta. Verifique se o seu CPF já está cadastrado.");
            return "registro";
        }
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }
}