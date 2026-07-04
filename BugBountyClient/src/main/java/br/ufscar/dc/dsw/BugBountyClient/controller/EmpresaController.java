package br.ufscar.dc.dsw.BugBountyClient.controller;

import br.ufscar.dc.dsw.BugBountyClient.dto.empresa.EmpresaRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.empresa.EmpresaResponseDTO;
import br.ufscar.dc.dsw.BugBountyClient.service.EmpresaClientService;
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
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaClientService service;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        try {
            model.addAttribute("empresas", service.listarTodos());
        }
        catch (RestClientException e) {
            model.addAttribute("erro", "API indisponível. Não foi possível carregar os dados.");
            model.addAttribute("empresas", List.of());
        }
        return "empresa/lista";
    }

    @GetMapping("/cadastrar")
    public String formCadastro(ModelMap model) {
        model.addAttribute("empresa", new EmpresaRequestDTO(null, null, null, null, null, null));
        return "empresa/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute EmpresaRequestDTO empresa, RedirectAttributes attr) {
        try {
            service.cadastrar(empresa);
            attr.addFlashAttribute("sucesso", "Empresa cadastrada via API.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Falha ao cadastrar no backend.");
        }
        return "redirect:/empresas/listar";
    }

    @GetMapping("/editar/{id}")
    public String formEdicao(@PathVariable Long id, ModelMap model, RedirectAttributes attr) {
        try {
            EmpresaResponseDTO response = service.buscarPorId(id);
            EmpresaRequestDTO request = new EmpresaRequestDTO(response.email(), null, response.nome(), response.cnpj(), response.setor(), response.descricao());
            model.addAttribute("empresa", request);
            model.addAttribute("id", id);
            return "empresa/form";
        }
        catch (HttpClientErrorException.NotFound e) {
            attr.addFlashAttribute("erro", "Empresa não encontrada.");
            return "redirect:/empresas/listar";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute EmpresaRequestDTO empresa, RedirectAttributes attr) {
        try {
            service.atualizar(id, empresa);
            attr.addFlashAttribute("sucesso", "Empresa atualizada.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro ao atualizar.");
        }
        return "redirect:/empresas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            service.excluir(id);
            attr.addFlashAttribute("sucesso", "Empresa excluída.");
        }
        catch (HttpClientErrorException.Conflict e) {
            attr.addFlashAttribute("erro", "Erro 409: Empresa possui programas vinculados.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro interno no backend.");
        }
        return "redirect:/empresas/listar";
    }
}