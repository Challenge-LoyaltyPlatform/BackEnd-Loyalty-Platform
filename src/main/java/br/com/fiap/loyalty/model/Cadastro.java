package br.com.fiap.loyalty.model;

import java.time.LocalDate;

public abstract class Cadastro {

    //Definindo atributos de Cadastro (abstrata)
    private int id;
    private String email;
    private LocalDate dataCadastro;

    //Construtor padrão
    public Cadastro() {
    }

    //Construtor parametrizado
    public Cadastro(int id, String email, LocalDate dataCadastro) {
        this.id = id;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    //Métodos Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    //Métodos Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    //Metodo abstrato
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Cadastro{" +
                "Id= " + getId() +
                ", Email= " + getEmail() +
                ", Data de Cadastro= " + getDataCadastro() +
                "}";
    }
}
