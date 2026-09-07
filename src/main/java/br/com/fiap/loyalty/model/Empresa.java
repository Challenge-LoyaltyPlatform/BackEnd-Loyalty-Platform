package br.com.fiap.loyalty.model;

import java.time.LocalDate;

public class Empresa extends Cadastro implements Ativavel {

    //Definindo atributos de Empresa
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private String telefoneEmpresa;
    private StatusEmpresa statusEmpresa;

    //Construtor vazio
    public Empresa() {
    }

    //Construtor com todos os parâmetros
    public Empresa(int id, String email, LocalDate dataCadastro, String nomeEmpresa,
                   String cnpjEmpresa, String telefoneEmpresa, StatusEmpresa statusEmpresa) {
        super(id, email, dataCadastro);
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.telefoneEmpresa = telefoneEmpresa;
        this.statusEmpresa = statusEmpresa;
    }

    //Métodos Getters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public StatusEmpresa getStatusEmpresa() {
        return statusEmpresa;
    }

    //Métodos Setters
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public void setStatusEmpresa(StatusEmpresa statusEmpresa) {
        this.statusEmpresa = statusEmpresa;
    }

    @Override
    public String getTipo() {
        return "Empresa";
    }

    //Métodos de negócio

    // RN21 — somente o Administrador da Loyalty ativa e inativa empresas
    @Override
    public void ativar() {
        if (this.statusEmpresa == StatusEmpresa.ATIVA) {
            throw new IllegalStateException("Empresa já está ativa: " + getNomeEmpresa());
        }
        this.statusEmpresa = StatusEmpresa.ATIVA;
    }

    @Override
    public void desativar() {
        if (this.statusEmpresa == StatusEmpresa.INATIVA) {
            throw new IllegalStateException("Empresa já está inativa: " + getNomeEmpresa());
        }
        this.statusEmpresa = StatusEmpresa.INATIVA;
    }

    public boolean estaAtiva() {
        return this.statusEmpresa == StatusEmpresa.ATIVA;
    }

    @Override
    public String toString() {
        return "Dados da Empresa{" +
                "Id= " + getId() +
                ", Nome= " + getNomeEmpresa() +
                ", CNPJ= " + getCnpjEmpresa() +
                ", Telefone= " + getTelefoneEmpresa() +
                ", E-mail= " + getEmail() +
                ", Data de Cadastro= " + getDataCadastro() +
                ", Status= " + getStatusEmpresa() +
                "}";
    }
}
