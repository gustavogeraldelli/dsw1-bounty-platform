package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
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

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("pesquisadores", pesquisadorService.buscarTodos());
        return "pesquisador/lista";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Pesquisador pesquisador) {
        return "pesquisador/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Pesquisador pesquisador) {
        pesquisador.setRole("ROLE_PESQUISADOR");
        pesquisadorService.salvar(pesquisador);
        return "redirect:/pesquisadores/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable Long id, ModelMap model) {
        model.addAttribute("pesquisador", pesquisadorService.buscarPorId(id));
        return "pesquisador/cadastro";
    }

    @PostMapping("/editar")
    public String editar(Pesquisador pesquisador, RedirectAttributes attr) {
        pesquisadorService.atualizar(pesquisador);
        attr.addFlashAttribute("sucesso", "Pesquisador atualizado com sucesso.");
        return "redirect:/pesquisadores/listar";
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