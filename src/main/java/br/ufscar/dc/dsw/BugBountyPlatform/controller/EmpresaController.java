package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "empresa/cadastro";
    }

    @PostMapping("/editar")
    public String editar(Empresa empresa) {
        empresaService.salvar(empresa);
        return "redirect:/empresas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id) {
        empresaService.excluir(id);
        return "redirect:/empresas/listar";
    }
}