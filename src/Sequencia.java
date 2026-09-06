package loyaltyplatform_modelo.src;

public class Sequencia implements Ativavel {

    // Definindo atributos de Sequencia
    private int idUsuario;
    private int diasConsecutivos;
    private String statusSequencia;
    private String dataInicioSequencia;
    private String ultimoAcesso;
    private String dataDesativacao;

    // Construtor padrão
    public Sequencia() {
    }

    //Construtor parametrizado

    public Sequencia(int idUsuario, int diasConsecutivos, String statusSequencia, String dataInicioSequencia, String ultimoAcesso, String dataDesativacao) {
        this.idUsuario = idUsuario;
        this.diasConsecutivos = diasConsecutivos;
        this.statusSequencia = statusSequencia;
        this.dataInicioSequencia = dataInicioSequencia;
        this.ultimoAcesso = ultimoAcesso;
        this.dataDesativacao = dataDesativacao;
    }


    public int getIdUsuario() {
        return idUsuario;
    }
    public int getDiasConsecutivos() {
        return diasConsecutivos;
    }
    public String getStatusSequencia() {
        return statusSequencia;
    }
    public String getDataInicioSequencia() {
        return dataInicioSequencia;
    }
    public String getUltimoAcesso() {
        return ultimoAcesso;
    }
    public String getDataDesativacao() {
        return dataDesativacao;
    }

    // Métodos Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setDiasConsecutivos(int diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }
    public void setStatusSequencia(String statusSequencia) {
        this.statusSequencia = statusSequencia;
    }
    public void setDataInicioSequencia(String dataInicioSequencia) {
        this.dataInicioSequencia = dataInicioSequencia;
    }
    public void setUltimoAcesso(String ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }
    public void setDataDesativacao(String dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }

    @Override
    public String toString() {
        return "Dados da Sequência{" +
                "Id do Usuário= " + getIdUsuario() +
                ", Dias Consecutivos= " + getDiasConsecutivos() +
                ", Status da Sequência= " + getStatusSequencia() +
                ", Data de Início= " + getDataInicioSequencia() +
                ", Último Acesso= " + getUltimoAcesso() +
                ", Data de Desativação= " + getDataDesativacao() +
                "}";

    }

    // Método de negócio — registra um novo acesso e incrementa os dias consecutivos
    public void registrarAcesso(String dataAcesso) {
        this.diasConsecutivos++;
        this.ultimoAcesso = dataAcesso;
        System.out.println("Acesso registrado! Streak atual: " + getDiasConsecutivos() + " dias consecutivos.");
    }

    @Override
    public void ativar() {
        this.statusSequencia = "ativa";
        System.out.println("Streak acessa com sucesso");
    }

    @Override
    public void desativar() {
        this.statusSequencia = "Inativo";
        System.out.println("Streak encerrada.");
    }
}