package br.ufscar.dc.dsw.BugBountyClient.service;

import br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador.PesquisadorRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.pesquisador.PesquisadorResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PesquisadorClientService {
    private final RestClient restClient = RestClient.create("http://localhost:8080/api/pesquisadores");

    public List<PesquisadorResponseDTO> listarTodos() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<PesquisadorResponseDTO>>() {});
    }

    public PesquisadorResponseDTO buscarPorId(Long id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(PesquisadorResponseDTO.class);
    }

    public void cadastrar(PesquisadorRequestDTO dto) {
        restClient.post()
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

    public void atualizar(Long id, PesquisadorRequestDTO dto) {
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