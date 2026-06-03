package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IProgramaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/programas")
public class ProgramaController {

    private final IProgramaDAO programaDAO;

    public ProgramaController(IProgramaDAO programaDAO) {
        this.programaDAO = programaDAO;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("programas", programaDAO.findAll());
        return "programa/lista";
    }

}
