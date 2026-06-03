package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IEmpresaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private final IEmpresaDAO empresaDAO;

    public EmpresaController(IEmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("empresas", empresaDAO.findAll());
        return "empresa/lista";
    }

}
