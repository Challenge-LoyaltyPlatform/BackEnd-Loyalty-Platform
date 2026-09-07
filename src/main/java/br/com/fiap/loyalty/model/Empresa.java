package br.com.fiap.loyalty.model;

public class Empresa extends Cadastro implements Ativavel {

    //Definindo atributos de Empresa
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private StatusEmpresa statusEmpresa;

    //Construtor vazio
    public Empresa() {
    }

    //Construtor com todos os parâmetros
    public Empresa(int id, String email, String dataCadastro,
                   String nomeEmpresa, String cnpjEmpresa, StatusEmpresa statusEmpresa) {
        super(id, email, dataCadastro);
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.statusEmpresa = statusEmpresa;
    }

    //Métodos Getters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    public String getCnpjEmpresa() {
        return cnpjEmpresa;
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
    public void setStatusEmpresa(StatusEmpresa statusEmpresa) {
        this.statusEmpresa = statusEmpresa;
    }

    @Override
    public String getTipo() {
        return "Empresa";
    }

    //Métodos de negócio
    @Override
    public void ativar() {
        if(statusEmpresa == StatusEmpresa.INATIVA){
            this.statusEmpresa = StatusEmpresa.ATIVA;
            System.out.println("Empresa Ativada com sucesso!");
        } else{
            System.err.println("A empresa já está ativa");
        }

    }

    @Override
    public void desativar() {
        if(statusEmpresa == StatusEmpresa.ATIVA) {
            this.statusEmpresa = StatusEmpresa.INATIVA;
        } else {
            System.err.println("A empresa já está inativa");
        }
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
                ", E-mail= " + getEmail() +
                ", Data de Cadastro= " + getDataCadastro() +
                ", Status= " + getStatusEmpresa() +
                "}";
    }
}