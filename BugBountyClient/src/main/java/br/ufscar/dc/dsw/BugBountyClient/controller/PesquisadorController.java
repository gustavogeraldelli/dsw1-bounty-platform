package br.ufscar.dc.dsw.BugBountyClient.controller;

import br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador.PesquisadorRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador.PesquisadorResponseDTO;
import br.ufscar.dc.dsw.BugBountyClient.service.PesquisadorClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pesquisadores")
public class PesquisadorController {

    @Autowired
    private PesquisadorClientService service;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        try {
            model.addAttribute("pesquisadores", service.listarTodos());
        }
        catch (RestClientException e) {
            model.addAttribute("erro", "API indisponível. Não foi possível carregar os dados.");
            model.addAttribute("pesquisadores", List.of());
        }
        return "pesquisador/lista";
    }

    @GetMapping("/cadastrar")
    public String formCadastro(ModelMap model) {
        model.addAttribute("pesquisador", new PesquisadorRequestDTO(null, null, null, null, null, null, null));
        return "pesquisador/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute PesquisadorRequestDTO pesquisador, RedirectAttributes attr) {
        try {
            service.cadastrar(pesquisador);
            attr.addFlashAttribute("sucesso", "Pesquisador cadastrado via API.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Falha ao cadastrar.");
        }
        return "redirect:/pesquisadores/listar";
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model, RedirectAttributes attr) {
        try {
            PesquisadorResponseDTO response = service.buscarPorId(id);
            PesquisadorRequestDTO request = new PesquisadorRequestDTO(response.email(), null, response.cpf(), response.nome(), response.telefone(), response.sexo(), response.dataNascimento());
            model.addAttribute("pesquisador", request);
            model.addAttribute("id", id);
            return "pesquisador/form";
        }
        catch (HttpClientErrorException.NotFound e) {
            attr.addFlashAttribute("erro", "Pesquisador não encontrado.");
            return "redirect:/pesquisadores/listar";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute PesquisadorRequestDTO pesquisador, RedirectAttributes attr) {
        try {
            service.atualizar(id, pesquisador);
            attr.addFlashAttribute("sucesso", "Pesquisador atualizado.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro ao atualizar.");
        }
        return "redirect:/pesquisadores/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            service.excluir(id);
            attr.addFlashAttribute("sucesso", "Pesquisador excluído.");
        }
        catch (HttpClientErrorException.Conflict e) {
            attr.addFlashAttribute("erro", "Erro 409: Pesquisador possui relatórios vinculados.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro interno no backend.");
        }
        return "redirect:/pesquisadores/listar";
    }
}