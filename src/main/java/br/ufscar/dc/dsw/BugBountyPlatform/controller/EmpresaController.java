package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IUsuarioService;
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

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "empresa/lista";
    }

    @GetMapping("/cadastrar")
    public String formCadastro(Empresa empresa) {
        return "form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Empresa empresa, ModelMap model, RedirectAttributes attr) {
        if (usuarioService.buscarPorEmail(empresa.getEmail()) != null) {
            model.addAttribute("erro", "Este e-mail já está em uso por outra conta.");
            return "form";
        }

        try {
            empresaService.salvar(empresa);
            attr.addFlashAttribute("sucesso", "Empresa cadastrada com sucesso.");
            return "redirect:/empresas/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", "Não foi possível cadastrar a empresa. Verifique se o CNPJ já está em uso.");
            return "form";
        }
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id));
        return "form";
    }

    @PostMapping("/editar")
    public String editar(Empresa empresa, ModelMap model, RedirectAttributes attr) {
        Usuario usuarioExistente = usuarioService.buscarPorEmail(empresa.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(empresa.getId())) {
            model.addAttribute("erro", "Este e-mail já está em uso por outra conta.");
            return "form";
        }

        try {
            empresaService.atualizar(empresa);
            attr.addFlashAttribute("sucesso", "Empresa atualizada com sucesso.");
            return "redirect:/empresas/listar";
        }
        catch (Exception e) {
            model.addAttribute("erro", "Erro ao atualizar. Verifique se o CNPJ informado já pertence a outra conta.");
            return "form";
        }
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