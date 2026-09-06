package loyaltyplatform_modelo.src;

public class Usuario extends Cadastro {

    //Definindo os Atributos do Usuário
    private int idNivel;
    private String nomeUsuario;
    private String senhaUsuario;
    private int pontosTotMissoes;

    //Construtor padrão
    public Usuario(){

    }

    @Override
    public String getTipo() {
        return "Usuário";
    }

    //Construtor com todos os parâmetros
    public Usuario(int idUsuario, int idNivel, String nomeUsuario, String emailUsuario, String senhaUsuario, String dataCadastro, int pontosTotMissoes){
        super(idUsuario, emailUsuario, dataCadastro);
        this.idNivel = idNivel;
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.pontosTotMissoes = pontosTotMissoes;
    }

    //Métodos Getters

    public int getIdNivel(){
        return idNivel;
    }
    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public String getSenhaUsuario(){
        return senhaUsuario;
    }

    public int getPontosTotMissoes(){
        return pontosTotMissoes;
    }

    //Métodos Setters

    public void setIdNivel(int idNivel){
        this.idNivel = idNivel;
    }
    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }


    public void setSenhaUsuario(String senhaUsuario){
        this.senhaUsuario = senhaUsuario;
    }

    public void setPontosTotMissoes(int pontosTotMissoes){
        this.pontosTotMissoes = pontosTotMissoes;
    }

    //metodo de negócio (usuário acumulando os pontos)
    public void acumularPontos(int pontos){
        if(pontos > 0 ){
            this.pontosTotMissoes += pontos;
            System.out.println(getNomeUsuario() + "acumulou " + pontos + " pontos! Total: "+ pontosTotMissoes);
        } else {
            System.err.println("Pontos inválidos. Insira um valor maior que zero(0).");
        }
    }

    @Override
    public String toString(){
        return "Dados do Usuário{" +
                "Id do Usuário= " + getId() +
                ", Id do Nível do Usuário= "+getIdNivel() +
                ", Nome do Usuário= "+getNomeUsuario() +
                ", Email do Usuário= "+ getEmail()+
                ", Senha do Usuário= "+getSenhaUsuario()+
                ", Data de criação do Cadastro de "+getNomeUsuario()+"= "+ getDataCadastro() +
                ", Pontos Acumulados do Usuário= "+getPontosTotMissoes()+
                "}";
    }
}
