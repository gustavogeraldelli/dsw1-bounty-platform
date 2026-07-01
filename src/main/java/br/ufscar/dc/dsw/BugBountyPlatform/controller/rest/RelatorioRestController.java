package br.ufscar.dc.dsw.BugBountyPlatform.controller.rest;

import br.ufscar.dc.dsw.BugBountyPlatform.controller.rest.dto.relatorio.AvaliacaoRelatorioRequestDTO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.service.IRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioRestController {

    @Autowired
    private IRelatorioService relatorioService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> submeter(
            @RequestParam("pesquisadorId") Long pesquisadorId,
            @RequestParam("programaId") Long programaId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty())
            return ResponseEntity.badRequest().body("O arquivo PDF é obrigatório.");

        try {
            relatorioService.submeter(pesquisadorId, programaId, file.getOriginalFilename(), file.getInputStream());
            return ResponseEntity.status(HttpStatus.CREATED).body("Relatório submetido com sucesso.");
        }
        catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro no processamento do arquivo.");
        }
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<String> avaliarRelatorio(
            @PathVariable Long id,
            @RequestBody AvaliacaoRelatorioRequestDTO request) {

        if (request.status() == StatusRelatorio.VULNERAVEL &&
                (request.severidade() == null || request.recompensa() == null))
            return ResponseEntity.badRequest().body("Severidade e recompensa são obrigatórios para relatórios vulneráveis.");

        relatorioService.avaliar(id, request.status(), request.severidade(), request.recompensa());
        return ResponseEntity.ok("Avaliação registrada com sucesso.");
    }
}