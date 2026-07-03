package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Programa")
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{programa.validation.titulo.notBlank}")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "{programa.validation.escopo.notBlank}")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String escopo;

    @NotNull(message = "{programa.validation.recompensa.notNull}")
    @Column(nullable = false)
    private BigDecimal recompensaMaxima;

    @NotNull(message = "{programa.validation.dataLimite.notNull}")
    @Column(nullable = false)
    private LocalDate dataLimite;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "programa")
    private List<Relatorio> relatorios;

    public Programa() {
    }

    public Programa(String titulo, String escopo, BigDecimal recompensaMaxima, LocalDate dataLimite, Empresa empresa) {
        this.titulo = titulo;
        this.escopo = escopo;
        this.recompensaMaxima = recompensaMaxima;
        this.dataLimite = dataLimite;
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public BigDecimal getRecompensaMaxima() {
        return recompensaMaxima;
    }

    public void setRecompensaMaxima(BigDecimal recompensaMaxima) {
        this.recompensaMaxima = recompensaMaxima;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
    }

}
