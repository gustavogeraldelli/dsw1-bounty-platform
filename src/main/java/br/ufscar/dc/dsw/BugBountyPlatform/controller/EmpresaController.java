package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "empresa/lista";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Empresa empresa) {
        return "empresa/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(Empresa empresa) {
        empresa.setRole("ROLE_EMPRESA");
        empresaService.salvar(empresa);
        return "redirect:/empresas/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable Long id, ModelMap model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/cadastro";
    }

    @PostMapping("/editar")
    public String editar(Empresa empresa, RedirectAttributes attr) {
        empresaService.atualizar(empresa);
        attr.addFlashAttribute("sucesso", "Empresa atualizada com sucesso.");
        return "redirect:/empresas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        if (empresaService.excluir(id))
            attr.addFlashAttribute("sucesso", "Empresa excluída com sucesso.");
        else
            attr.addFlashAttribute("erro", "Não é possível excluir esta empresa pois ela possui programas vinculados.");

        return "redirect:/empresas/listar";
    }
}