package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest;

import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.empresa.EmpresaRequestDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.empresa.EmpresaResponseDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaRestController {

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodos() {
        List<Empresa> empresas = empresaService.buscarTodos();
        if (empresas.isEmpty())
            return ResponseEntity.noContent().build();

        List<EmpresaResponseDTO> dtoList = empresas.stream()
                .map(EmpresaResponseDTO::from)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        if (empresa == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(EmpresaResponseDTO.from(empresa));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> cadastrar(@RequestBody EmpresaRequestDTO request) {
        Empresa empresa = request.toEntity();
        empresaService.salvar(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmpresaResponseDTO.from(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Long id, @RequestBody EmpresaRequestDTO request) {
        Empresa existente = empresaService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        Empresa empresaAtualizada = request.toEntity();
        empresaAtualizada.setId(id);
        empresaService.atualizar(empresaAtualizada);
        return ResponseEntity.ok(EmpresaResponseDTO.from(empresaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Empresa existente = empresaService.buscarPorId(id);
        if (existente == null)
            return ResponseEntity.notFound().build();

        if (!empresaService.excluir(id))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.noContent().build();
    }
}