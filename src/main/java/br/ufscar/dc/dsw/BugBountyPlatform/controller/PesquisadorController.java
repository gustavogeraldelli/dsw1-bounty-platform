package br.ufscar.dc.dsw.BugBountyPlatform.controller;


import br.ufscar.dc.dsw.BugBountyPlatform.dao.IPesquisadorDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pesquisadores")
public class PesquisadorController {

    private final IPesquisadorDAO pesquisadorDAO;

    public PesquisadorController(IPesquisadorDAO pesquisadorDAO) {
        this.pesquisadorDAO = pesquisadorDAO;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("pesquisadores", pesquisadorDAO.findAll());
        return "pesquisador/lista";
    }

}
