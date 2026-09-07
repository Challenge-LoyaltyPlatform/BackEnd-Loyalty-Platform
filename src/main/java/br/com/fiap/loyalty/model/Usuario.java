package br.com.fiap.loyalty.model;

public class Usuario extends Cadastro {

    //Definindo os Atributos do Usuário
    private String nomeUsuario;
    private String senhaUsuario;

    //Construtor padrão
    public Usuario() {
    }

    //Construtor com todos os parâmetros
    public Usuario(int idUsuario, String nomeUsuario, String emailUsuario,
                   String senhaUsuario, String dataCadastro) {
        super(idUsuario, emailUsuario, dataCadastro);
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
    }

    //Métodos Getters
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    @Override
    public String getTipo() {
        return "Usuário";
    }

    //Métodos Setters
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    @Override
    public String toString() {
        return "Dados do Usuário{" +
                "Id do Usuário= " + getId() +
                ", Nome do Usuário= " + getNomeUsuario() +
                ", Email do Usuário= " + getEmail() +
                ", Data de Cadastro= " + getDataCadastro() +
                "}";
    }
}