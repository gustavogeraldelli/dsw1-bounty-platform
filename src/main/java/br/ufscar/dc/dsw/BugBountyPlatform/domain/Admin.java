package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Usuario {

    @Column(nullable = false, length = 100)
    private String nome;

    public Admin() {
    }

    public Admin(String email, String senha, String role, String nome) {
        super(email, senha, role);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}