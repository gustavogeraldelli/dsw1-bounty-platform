package br.ufscar.dc.dsw.BugBountyPlatform.controller;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IRelatorioDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final IRelatorioDAO relatorioDAO;

    public RelatorioController(IRelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    

}
