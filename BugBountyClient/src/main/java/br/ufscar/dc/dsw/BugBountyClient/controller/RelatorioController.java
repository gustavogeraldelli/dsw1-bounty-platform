package br.ufscar.dc.dsw.BugBountyClient.controller;

import br.ufscar.dc.dsw.BugBountyClient.dto.relatorio.AvaliacaoRelatorioRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.service.PesquisadorClientService;
import br.ufscar.dc.dsw.BugBountyClient.service.ProgramaClientService;
import br.ufscar.dc.dsw.BugBountyClient.service.RelatorioClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioClientService service;

    @Autowired
    private ProgramaClientService programaService;

    @Autowired
    private PesquisadorClientService pesquisadorService;

    @GetMapping("/listar")
    public String listar(ModelMap model, RedirectAttributes attr) {
        try {
            model.addAttribute("relatorios", service.listarTodos());
        }
        catch (RestClientException e) {
            model.addAttribute("erro", "API indisponível. Não foi possível carregar os dados.");
            model.addAttribute("relatorios", List.of());
        }
        return "relatorio/lista";
    }


    @GetMapping("/submeter")
    public String exibirFormSubmissao(ModelMap model, RedirectAttributes attr) {
        try {
            model.addAttribute("programas", programaService.listarTodos());
            model.addAttribute("pesquisadores", pesquisadorService.listarTodos());
            return "relatorio/form";
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro ao buscar dados de dependência na API.");
            return "redirect:/relatorios/listar";
        }
    }

    @PostMapping("/submeter")
    public String submeter(@RequestParam("pesquisadorId") Long pesquisadorId,
                           @RequestParam("programaId") Long programaId,
                           @RequestParam("file") MultipartFile file,
                           RedirectAttributes attr) {
        try {
            service.submeter(pesquisadorId, programaId, file);
            attr.addFlashAttribute("sucesso", "Relatório e PoC submetidos com sucesso via API.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro de comunicação ao enviar arquivo para o backend.");
        }
        return "redirect:/relatorios/listar";
    }

    @PostMapping("/avaliar")
    public String avaliar(@RequestParam("id") Long id, @ModelAttribute AvaliacaoRelatorioRequestDTO dto, RedirectAttributes attr) {
        try {
            service.avaliar(id, dto);
            attr.addFlashAttribute("sucesso", "Avaliação registrada com sucesso na API.");
        }
        catch (HttpClientErrorException.BadRequest e) {
            attr.addFlashAttribute("erro", "Erro 400: Severidade e recompensa são obrigatórios para relatórios vulneráveis.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro de comunicação com o backend.");
        }
        return "redirect:/relatorios/listar";
    }
}