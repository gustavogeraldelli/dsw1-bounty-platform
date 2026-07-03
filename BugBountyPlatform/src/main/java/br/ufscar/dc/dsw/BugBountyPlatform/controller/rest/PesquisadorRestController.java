package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest;

import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.pesquisador.PesquisadorRequestDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.pesquisador.PesquisadorResponseDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IPesquisadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pesquisadores")
public class PesquisadorRestController {

    @Autowired
    private IPesquisadorService pesquisadorService;

    @GetMapping
    public ResponseEntity<List<PesquisadorResponseDTO>> listarTodos() {
        List<Pesquisador> pesquisadores = pesquisadorService.buscarTodos();
        if (pesquisadores.isEmpty())
            return ResponseEntity.noContent().build();

        List<PesquisadorResponseDTO> dtoList = pesquisadores.stream()
                .map(PesquisadorResponseDTO::from)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PesquisadorResponseDTO> buscarPorId(@PathVariable Long id) {
        Pesquisador pesquisador = pesquisadorService.buscarPorId(id);
        if (pesquisador == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(PesquisadorResponseDTO.from(pesquisador));
    }

    @PostMapping
    public ResponseEntity<PesquisadorResponseDTO> cadastrar(@RequestBody PesquisadorRequestDTO request) {
        Pesquisador pesquisador = request.toEntity();
        pesquisadorService.salvar(pesquisador);
        return ResponseEntity.status(HttpStatus.CREATED).body(PesquisadorResponseDTO.from(pesquisador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PesquisadorResponseDTO> atualizar(@PathVariable Long id, @RequestBody PesquisadorRequestDTO request) {
        Pesquisador existente = pesquisadorService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        Pesquisador pesquisadorAtualizado = request.toEntity();
        pesquisadorAtualizado.setId(id);
        pesquisadorService.atualizar(pesquisadorAtualizado);
        return ResponseEntity.ok(PesquisadorResponseDTO.from(pesquisadorAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Pesquisador existente = pesquisadorService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        if (!pesquisadorService.excluir(id))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.noContent().build();
    }
}