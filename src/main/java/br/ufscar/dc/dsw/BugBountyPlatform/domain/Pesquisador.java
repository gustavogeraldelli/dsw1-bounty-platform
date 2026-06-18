package br.ufscar.dc.dsw.BugBountyPlatform.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Pesquisador")
@PrimaryKeyJoinColumn(name = "id")
public class Pesquisador extends Usuario {

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 16)
    private String telefone;

    @Column(nullable = false, length = 1)
    private String sexo;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pesquisador")
    private List<Relatorio> relatorios;

    public Pesquisador() {
    }

    public Pesquisador(String cpf, String nome, String telefone, String sexo, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    public Pesquisador(String email, String senha, String role, String nome, String cpf, String telefone, String sexo, LocalDate dataNascimento) {
        super(email, senha, role);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
    }
}