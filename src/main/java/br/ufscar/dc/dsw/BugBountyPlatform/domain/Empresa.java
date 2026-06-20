package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Table(name = "Empresa")
@PrimaryKeyJoinColumn(name = "id")
public class Empresa extends Usuario {

    @NotBlank(message = "{empresa.validation.nome.notBlank}")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "{empresa.validation.cnpj.notBlank}")
    //@CNPJ(message = "CNPJ inválido.")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 500)
    private String descricao;

    @NotBlank(message = "{empresa.validation.setor.notBlank}")
    @Column(nullable = false, length = 50)
    private String setor;

    @OneToMany(mappedBy = "empresa")
    private List<Programa> programas;

    public Empresa() {
    }

    public Empresa(String nome, String cnpj, String descricao, String setor) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.setor = setor;
    }

    public Empresa(String email, String senha, String role, String nome, String cnpj, String descricao, String setor) {
        super(email, senha, role);
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }
}