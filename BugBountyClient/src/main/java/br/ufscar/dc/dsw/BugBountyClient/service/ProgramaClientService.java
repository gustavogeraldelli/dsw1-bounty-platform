package br.ufscar.dc.dsw.BugBountyClient.service;

import br.ufscar.dc.dsw.BugBountyClient.dto.programa.ProgramaRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.programa.ProgramaResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ProgramaClientService {

    private final RestClient restClient = RestClient.create("http://localhost:8080/api/programas");

    public List<ProgramaResponseDTO> listarTodos() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<ProgramaResponseDTO>>() {});
    }

    public ProgramaResponseDTO buscarPorId(Long id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(ProgramaResponseDTO.class);
    }

    public ProgramaResponseDTO cadastrar(ProgramaRequestDTO dto) {
        return restClient.post()
                .body(dto)
                .retrieve()
                .body(ProgramaResponseDTO.class);
    }

    public void atualizar(Long id, ProgramaRequestDTO dto) {
        restClient.put()
                .uri("/{id}", id)
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

    public void excluir(Long id) {
        restClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}