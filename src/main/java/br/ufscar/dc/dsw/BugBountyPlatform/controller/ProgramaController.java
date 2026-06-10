package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private IProgramaService programaService;

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "setor", required = false) String setor, ModelMap model) {
        if (setor != null && !setor.trim().isEmpty())
            model.addAttribute("programas", programaService.buscarPorSetor(setor));
        else
            model.addAttribute("programas", programaService.buscarTodos());

        return "programa/lista";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Programa programa, ModelMap model) {
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "programa/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Programa programa) {
        programaService.salvar(programa);
        return "redirect:/programas/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable Long id, ModelMap model) {
        model.addAttribute("programa", programaService.buscarPorId(id));
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "programa/cadastro";
    }

    @PostMapping("/editar")
    public String editar(Programa programa) {
        programaService.salvar(programa);
        return "redirect:/programas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        programaService.excluir(id);
        return "redirect:/programas/listar";
    }
}