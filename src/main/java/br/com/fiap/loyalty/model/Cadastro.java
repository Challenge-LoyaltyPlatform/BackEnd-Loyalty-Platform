package br.com.fiap.loyalty.model;

public abstract class Cadastro {
    //Definindo atributos de Cadastro (abstrata)
    private int id;
    private String email;
    private String dataCadastro;

    //Construtor padrão
    public Cadastro() {}

    //Construtor parametrizado
    public Cadastro(int id, String email, String dataCadastro) {
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
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    //Metodo abstrado
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
