package br.ufscar.dc.dsw.BugBountyClient.service;

import br.ufscar.dc.dsw.BugBountyClient.dto.empresa.EmpresaRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.empresa.EmpresaResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmpresaClientService {
    private final RestClient restClient = RestClient.create("http://localhost:8080/api/empresas");

    public List<EmpresaResponseDTO> listarTodos() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<EmpresaResponseDTO>>() {});
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(EmpresaResponseDTO.class);
    }

    public void cadastrar(EmpresaRequestDTO dto) {
        restClient.post()
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

    public void atualizar(Long id, EmpresaRequestDTO dto) {
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