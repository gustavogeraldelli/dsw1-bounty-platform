package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Empresa")
public class Empresa extends AbstractUser {

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj; // Formatado: XX.XXX.XXX/XXXX-XX

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String setor;

    public Empresa() {
    }

    public Empresa(String nome, String cnpj, String descricao, String setor) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.setor = setor;
    }

    public Empresa(String email, String senna, String role, String nome, String cnpj, String descricao, String setor) {
        super(email, senna, role);
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

}
