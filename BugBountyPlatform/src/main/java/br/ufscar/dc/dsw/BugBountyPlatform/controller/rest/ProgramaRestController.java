package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest;

import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.programa.ProgramaRequestDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.programa.ProgramaResponseDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programas")
public class ProgramaRestController {

    @Autowired
    private IProgramaService programaService;

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<ProgramaResponseDTO>> listarTodos() {
        List<Programa> programas = programaService.buscarTodos();
        if (programas.isEmpty())
            return ResponseEntity.noContent().build();
        List<ProgramaResponseDTO> dtoList = programas.stream()
                .map(ProgramaResponseDTO::from)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaResponseDTO> buscarPorId(@PathVariable Long id) {
        Programa programa = programaService.buscarPorId(id);
        if (programa == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProgramaResponseDTO.from(programa));
    }

    @PostMapping
    public ResponseEntity<ProgramaResponseDTO> cadastrar(@RequestBody ProgramaRequestDTO request) {
        Empresa empresa = empresaService.buscarPorId(request.empresaId());
        if (empresa == null)
            return ResponseEntity.badRequest().build();

        Programa programa = request.toEntity();
        programa.setEmpresa(empresa);
        programaService.salvar(programa);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProgramaResponseDTO.from(programa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProgramaRequestDTO request) {
        Programa existente = programaService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        Empresa empresa = empresaService.buscarPorId(request.empresaId());
        if (empresa == null)
            return ResponseEntity.badRequest().build();

        Programa programaAtualizado = request.toEntity();
        programaAtualizado.setId(id);
        programaAtualizado.setEmpresa(empresa);

        programaService.atualizar(programaAtualizado);
        return ResponseEntity.ok(ProgramaResponseDTO.from(programaAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Programa existente = programaService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        if (!programaService.excluir(id))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.noContent().build();
    }
}