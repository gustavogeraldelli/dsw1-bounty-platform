package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pesquisadores")
public class PesquisadorController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("pesquisadores", pesquisadorService.buscarTodos());
        return "pesquisador/lista";
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model) {
        model.addAttribute("pesquisador", pesquisadorService.buscarPorId(id));
        return "form";
    }

    @PostMapping("/editar")
    public String editar(Pesquisador pesquisador, ModelMap model, RedirectAttributes attr) {
        Usuario usuarioExistente = usuarioService.buscarPorEmail(pesquisador.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(pesquisador.getId())) {
            model.addAttribute("erro", "Este e-mail já está em uso por outra conta.");
            return "form";
        }

        try {
            pesquisadorService.atualizar(pesquisador);
            attr.addFlashAttribute("sucesso", "Pesquisador atualizado com sucesso.");
            return "redirect:/pesquisadores/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", "Erro ao atualizar. Verifique se o CPF informado já pertence a outra conta.");
            return "form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (pesquisadorService.excluir(id))
            attr.addFlashAttribute("sucesso", "Pesquisador excluído com sucesso.");
        else
            attr.addFlashAttribute("erro", "Não é possível excluir este pesquisador, pois ele possui relatórios (PoCs) submetidos.");

        return "redirect:/pesquisadores/listar";
    }
}