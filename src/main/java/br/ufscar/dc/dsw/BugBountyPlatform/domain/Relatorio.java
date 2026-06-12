package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Relatorio")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caminhoArquivoPoc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusRelatorio status;

    @Column(nullable = false)
    private LocalDateTime dataSubmissao;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @ManyToOne
    @JoinColumn(name = "programa_id", nullable = false)
    private Programa programa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Severidade severidade;

    @Column(nullable = true)
    private BigDecimal recompensa;

    public Relatorio() {
    }

    public Relatorio(String caminhoArquivoPoc, StatusRelatorio status, LocalDateTime dataSubmissao, Pesquisador pesquisador, Programa programa) {
        this.caminhoArquivoPoc = caminhoArquivoPoc;
        this.status = status;
        this.dataSubmissao = dataSubmissao;
        this.pesquisador = pesquisador;
        this.programa = programa;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoArquivoPoc() {
        return caminhoArquivoPoc;
    }

    public void setCaminhoArquivoPoc(String caminhoArquivoPoc) {
        this.caminhoArquivoPoc = caminhoArquivoPoc;
    }

    public StatusRelatorio getStatus() {
        return status;
    }

    public void setStatus(StatusRelatorio status) {
        this.status = status;
    }

    public LocalDateTime getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(LocalDateTime dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public BigDecimal getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(BigDecimal recompensa) {
        this.recompensa = recompensa;
    }

    public Severidade getSeveridade() {
        return severidade;
    }

    public void setSeveridade(Severidade severidade) {
        this.severidade = severidade;
    }
}
