package br.ufscar.dc.dsw.BugBountyClient.controller;

import br.ufscar.dc.dsw.BugBountyClient.dto.programa.ProgramaRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.programa.ProgramaResponseDTO;
import br.ufscar.dc.dsw.BugBountyClient.service.ProgramaClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaClientService service;

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        try {
            List<ProgramaResponseDTO> programas = service.listarTodos();
            LocalDate hoje = LocalDate.now();
            List<ProgramaResponseDTO> programasOrdenados = programas.stream()
                    .sorted(Comparator.comparing((ProgramaResponseDTO p) -> p.dataLimite().isBefore(hoje))
                            .thenComparing(ProgramaResponseDTO::dataLimite))
                    .toList();
            model.addAttribute("programas", programasOrdenados);
        }
        catch (RestClientException e) {
            model.addAttribute("erro", "API indisponível. Não foi possível carregar os dados.");
            model.addAttribute("programas", List.of());
        }
        return "programa/lista";
    }

    @GetMapping("/cadastrar")
    public String exibirFormCadastro(ModelMap model) {
        model.addAttribute("programa", new ProgramaRequestDTO(null, null, null, null, null));
        return "programa/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute ProgramaRequestDTO programa, RedirectAttributes attr) {
        try {
            service.cadastrar(programa);
            attr.addFlashAttribute("sucesso", "Programa cadastrado com sucesso via API.");
        }
        catch (HttpClientErrorException e) {
            attr.addFlashAttribute("erro", "Erro de validação na API: Verifique os dados e o ID da Empresa.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Falha na comunicação com o backend.");
        }
        return "redirect:/programas/listar";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormEdicao(@PathVariable Long id, ModelMap model, RedirectAttributes attr) {
        try {
            ProgramaResponseDTO response = service.buscarPorId(id);

            ProgramaRequestDTO request = new ProgramaRequestDTO(
                    response.titulo(),
                    response.escopo(),
                    response.recompensaMaxima(),
                    response.dataLimite(),
                    response.empresaId()
            );
            model.addAttribute("programa", request);
            model.addAttribute("id", id);

            return "programa/form";
        }
        catch (HttpClientErrorException.NotFound e) {
            attr.addFlashAttribute("erro", "Programa não encontrado na API.");
            return "redirect:/programas/listar";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute ProgramaRequestDTO programa, RedirectAttributes attr) {
        try {
            service.atualizar(id, programa);
            attr.addFlashAttribute("sucesso", "Programa atualizado com sucesso via API.");
        }
        catch (HttpClientErrorException e) {
            attr.addFlashAttribute("erro", "Erro ao atualizar. Verifique os dados enviados.");
        }
        return "redirect:/programas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        try {
            service.excluir(id);
            attr.addFlashAttribute("sucesso", "Programa excluído com sucesso via API.");
        }
        catch (HttpClientErrorException.Conflict e) {
            attr.addFlashAttribute("erro", "A API bloqueou a exclusão (Status 409): O programa possui relatórios vinculados.");
        }
        catch (RestClientResponseException e) {
            attr.addFlashAttribute("erro", "Erro ao tentar excluir no backend.");
        }
        return "redirect:/programas/listar";
    }
}