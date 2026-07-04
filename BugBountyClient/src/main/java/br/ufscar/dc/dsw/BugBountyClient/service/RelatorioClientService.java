package br.ufscar.dc.dsw.BugBountyClient.service;

import br.ufscar.dc.dsw.BugBountyClient.dto.relatorio.AvaliacaoRelatorioRequestDTO;
import br.ufscar.dc.dsw.BugBountyClient.dto.relatorio.RelatorioResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RelatorioClientService {

    private final RestClient restClient = RestClient.create("http://localhost:8080/api/relatorios");

    public void submeter(Long pesquisadorId, Long programaId, MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("pesquisadorId", pesquisadorId);
        body.add("programaId", programaId);
        body.add("file", file.getResource());

        restClient.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

    public void avaliar(Long id, AvaliacaoRelatorioRequestDTO dto) {
        restClient.put()
                .uri("/{id}/avaliar", id)
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }

    public List<RelatorioResponseDTO> listarTodos() {
        return restClient.get().retrieve().body(new ParameterizedTypeReference<List<RelatorioResponseDTO>>() {});
    }

    public RelatorioResponseDTO buscarPorId(Long id) {
        return restClient.get().uri("/{id}", id).retrieve().body(RelatorioResponseDTO.class);
    }
}